package com.dimwits.vaperoid.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.utils.listeners.ProgressListener;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkHelper;

import java.io.IOException;

/**
 * Created by farid on 2/28/17.
 */

public class RegisterActivity extends AppCompatActivity implements ResponseListener, ProgressListener {
    public static final int PICK_IMAGE = 1;
    private ProgressBar progressBar;
    private Button registerButton;
    private Button uploadButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = (ProgressBar) findViewById(R.id.register_upload_progress);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setEnabled(false);
        uploadButton = (Button) findViewById(R.id.register_upload_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            NetworkHelper.getInstance().uploadFile(this, this, getFilePath(uri), "/api/file");
            Toast.makeText(this, getFilePath(uri), Toast.LENGTH_SHORT).show();
        }
    }

    private String getFilePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null)
            return null;
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(projection[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    @Override
    public void refreshProgress(int percents) {
        progressBar.setProgress(percents);
    }

    @Override
    public void onResponseFinished(String response, boolean isSuccessful) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        registerButton.setEnabled(true);
    }
}
