package com.dimwits.vaperoid.utils.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final SparseArray<ResponseListener> tasks = new SparseArray<>();
    private int taskId = 0;
    private final ExecutorService fixedThreadPool;
    private static NetworkHelper instance;

    private NetworkHelper() {
        fixedThreadPool = Executors.newFixedThreadPool(NUM_THREADS);
        resultHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                resultReady(message.arg1, (String) message.obj, true);
            }
        };
    }

    public static synchronized NetworkHelper getInstance() {
        if (instance == null) {
            instance = new NetworkHelper();
        }
        return instance;
    }

    private String sendRequest(String method, String body, String url) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        OkHttpClient client = OkHttpConnector.getConnector();

        Request request = new Request.Builder()
                .url(url)
                .method(method, requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public int send(ResponseListener listener,
                    final String method, final String body, final String url) {
        tasks.put(taskId, listener);

        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message message = resultHandler.obtainMessage();
                message.arg1 = taskId;
                try {
                    message.obj = sendRequest(method, body, url);
                    message.sendToTarget();
                } catch (IOException ex) {
                    message.sendToTarget();
                }
            }
        });

        return taskId;
    }

    private void resultReady(int taskId, String response, boolean isSuccessful) {
        ResponseListener responseListener = tasks.get(taskId);
        tasks.remove(taskId);
        responseListener.onResponseFinished(response, isSuccessful);
    }
}
