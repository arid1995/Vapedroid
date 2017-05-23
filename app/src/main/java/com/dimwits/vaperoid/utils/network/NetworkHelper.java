package com.dimwits.vaperoid.utils.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.dimwits.vaperoid.utils.listeners.ProgressListener;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.listeners.UploadedListener;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by farid on 3/22/17.
 */

public class NetworkHelper {
    public static String GET = "GET";
    public static String POST = "POST";
    private final int NUM_THREADS = 3;
    private final Handler resultHandler;
    private final Handler progressHandler;
    private final Handler uploadedHandler;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final SparseArray<ResponseListener> tasks = new SparseArray<>();
    private final SparseArray<UploadedListener> uploadTasks = new SparseArray<>();
    private final SparseArray<ProgressListener> progressTasks = new SparseArray<>();

    private int taskId = 0;
    private final ExecutorService fixedThreadPool;
    private static NetworkHelper instance;

    private NetworkHelper() {
        fixedThreadPool = Executors.newFixedThreadPool(NUM_THREADS);
        resultHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                resultReady(message.arg1, (String) message.obj, message.arg2 == 0);
            }
        };
        progressHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                progressChanged(message.arg1, message.arg2);
            }
        };
        uploadedHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                fileUploaded(message.arg1, (String) message.obj, message.arg2 == 0);
            }
        };
    }

    public static synchronized NetworkHelper getInstance() {
        if (instance == null) {
            instance = new NetworkHelper();
        }
        return instance;
    }

    public int send(ResponseListener listener,
                    final String method, final String body, final String url) {
        taskId++;
        tasks.put(taskId, listener);

        fixedThreadPool.execute(new MemRunnable(taskId) {
            @Override
            public void run() {
                Message message = resultHandler.obtainMessage();
                message.arg1 = this.taskId;
                message.arg2 = 0;
                try {
                    message.obj = sendRequest(method, body, url);
                    message.sendToTarget();
                } catch (IOException ex) {
                    message.arg2 = 1;
                    message.sendToTarget();
                }
            }
        });

        return taskId;
    }

    public int uploadFile(UploadedListener uploadedListener, ProgressListener progressListener,
                          final String filePath, final String url) {
        taskId++;
        uploadTasks.put(taskId, uploadedListener);
        progressTasks.put(taskId, progressListener);

        final File file = new File(filePath);
        RequestBody requestBody = buildRequestBody(file, taskId);

        final Request request = new Request.Builder()
                .url(NetworkConstants.IP_ADDRESS + url)
                .post(requestBody)
                .build();

        fixedThreadPool.execute(new MemRunnable(taskId) {
            @Override
            public void run() {
                Message message = uploadedHandler.obtainMessage();
                message.arg1 = this.taskId;
                message.arg2 = 0;
                try {
                    message.obj = OkHttpConnector.getConnector().newCall(request)
                            .execute().body().string();
                    message.sendToTarget();
                } catch (IOException e) {
                    message.arg2 = 1;
                    message.sendToTarget();
                }
            }
        });
        return taskId;
    }

    public void unsubscribe(int taskId) {
        tasks.remove(taskId);
        uploadTasks.remove(taskId);
        progressTasks.remove(taskId);
    }

    private String sendRequest(String method, String body, String url) throws IOException {
        RequestBody requestBody = null;
        if (!method.equals(GET)) {
            requestBody = RequestBody.create(JSON, body);
        }

        OkHttpClient client = OkHttpConnector.getConnector();

        Request request = new Request.Builder()
                .url(url)
                .method(method, requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private RequestBody buildRequestBody(final File file, final int taskId) {
        return new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart(
                        "file", file.getName(),
                        new CountingFileRequestBody(file, "image/*", new CountingFileRequestBody.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                Message message = progressHandler.obtainMessage();
                                message.arg1 = taskId;
                                message.arg2 = (int) num;
                                message.sendToTarget();
                            }
                        })
                )
                .build();
    }

    private void resultReady(int taskId, String response, boolean isSuccessful) {
        ResponseListener responseListener = tasks.get(taskId);
        if (responseListener == null) {
            return;
        }
        tasks.remove(taskId);
        responseListener.onResponseFinished(response, isSuccessful);
    }

    private void fileUploaded(int taskId, String response, boolean isSuccessful) {
        UploadedListener uploadedListener = uploadTasks.get(taskId);
        if (uploadedListener == null) {
            return;
        }
        uploadTasks.remove(taskId);
        progressTasks.remove(taskId);
        uploadedListener.fileUploaded(response, isSuccessful);
    }

    private void progressChanged(int taskId, int percents) {
        ProgressListener progressListener = progressTasks.get(taskId);
        if (progressListener == null) {
            return;
        }
        progressListener.refreshProgress(percents);
    }

    private abstract class MemRunnable implements Runnable {
        int taskId;

        MemRunnable(int taskId) {
            this.taskId = taskId;
        }
    }
}
