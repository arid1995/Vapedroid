package com.dimwits.vaperoid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.fragments.UnauthenticatedFragment;
import com.dimwits.vaperoid.requests.RegistrationRequest;
import com.dimwits.vaperoid.requests.entities.ErrorEntity;
import com.dimwits.vaperoid.requests.entities.SessionEntity;
import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;
import com.dimwits.vaperoid.requests.exceptions.WrongCredentialsException;
import com.dimwits.vaperoid.utils.listeners.ProgressListener;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.listeners.UploadedListener;
import com.dimwits.vaperoid.utils.network.NetworkHelper;
import com.google.gson.Gson;

/**
 * Created by farid on 2/28/17.
 */

public class RegisterActivity extends AppCompatActivity implements ResponseListener,
        ProgressListener, UploadedListener {
    public static final int PICK_IMAGE = 1;
    private ProgressBar progressBar;
    private Button registerButton;
    private Button uploadButton;
    private String fileName;
    private EditText loginField;
    private EditText passwordField;
    private EditText repeatPasswordField;
    private EditText emailField;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText aboutField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instantiateViewVars();

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
                RegistrationRequest registrationRequest = new RegistrationRequest();
                String login = loginField.getText().toString();
                String password = passwordField.getText().toString();
                String passwordRepeated = repeatPasswordField.getText().toString();
                String email = emailField.getText().toString();
                String firstName = firstNameField.getText().toString();
                String lastName = lastNameField.getText().toString();
                String about = aboutField.getText().toString();

                if (password.equals(passwordRepeated)) {
                    try {
                        registrationRequest.register(RegisterActivity.this, login, password, email,
                                firstName, lastName, fileName, about);
                    } catch (ViolatedConstraintsException e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
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
        if (isSuccessful) {
            try {
                ErrorEntity errorEntity = new ErrorEntity(response);
                if (errorEntity.getMessage() != null) {
                    Toast.makeText(this, errorEntity.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                writeSessionInPrefs(response);
            } catch (WrongCredentialsException e) {
                Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        Toast.makeText(this, "Something is wrong with the connection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fileUploaded(String fileName, boolean isSuccessful) {
        registerButton.setEnabled(true);
        this.fileName = fileName;
    }

    private void instantiateViewVars() {
        progressBar = (ProgressBar) findViewById(R.id.register_upload_progress);
        progressBar.setMax(100);
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setEnabled(false);
        uploadButton = (Button) findViewById(R.id.register_upload_button);
        loginField = (EditText) findViewById(R.id.register_login);
        passwordField = (EditText) findViewById(R.id.register_password);
        repeatPasswordField = (EditText) findViewById(R.id.register_password_repeat);
        emailField = (EditText) findViewById(R.id.register_email);
        firstNameField = (EditText) findViewById(R.id.register_first_name);
        lastNameField = (EditText) findViewById(R.id.register_last_name);
        aboutField = (EditText) findViewById(R.id.register_about);
    }

    private void writeSessionInPrefs(String sessionJsonString) throws WrongCredentialsException {
        Gson gson = new Gson();
        SessionEntity session = gson.fromJson(sessionJsonString, SessionEntity.class);
        String sessionId = session.getSessionId();
        if (sessionId == null) {
            throw new WrongCredentialsException("Wrong credentials");
        }
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.shared_pref_file),
                Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UnauthenticatedFragment.SESSION_ID_KEY, sessionId);
        editor.apply();
    }
}
