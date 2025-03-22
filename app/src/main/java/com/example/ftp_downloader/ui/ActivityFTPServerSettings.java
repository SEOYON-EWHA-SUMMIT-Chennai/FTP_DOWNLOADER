package com.example.ftp_downloader.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftp_downloader.R;
import com.example.ftp_downloader.ui.data.prefs.ServerCredentials;

public class ActivityFTPServerSettings extends AppCompatActivity {
    ServerCredentials creds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp_server_settings);
        creds = new ServerCredentials(this);
        setTitle("FTP Server settings");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupViews();
        listeners();
    }

    private void setupViews() {
        EditText server_ipEditText = findViewById(R.id.server_ip);
        EditText user_idEditText = findViewById(R.id.user_id);
        EditText passwordEditText = findViewById(R.id.password);
        EditText download_pathEditText = findViewById(R.id.download_path);
        EditText file_nameEditText = findViewById(R.id.file_name);

        server_ipEditText.setText(creds.getServerIP());
        user_idEditText.setText(creds.getUserId());
        passwordEditText.setText(creds.getPassword());
        download_pathEditText.setText(creds.getPath());
        file_nameEditText.setText(creds.getFileName());
    }

    private void listeners() {
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText server_ipEditText = findViewById(R.id.server_ip);
                EditText user_idEditText = findViewById(R.id.user_id);
                EditText passwordEditText = findViewById(R.id.password);
                EditText download_pathEditText = findViewById(R.id.download_path);
                EditText file_nameEditText = findViewById(R.id.file_name);

                String ip = server_ipEditText.getText().toString().trim();
                String user = user_idEditText.getText().toString().trim();
                String pwd = passwordEditText.getText().toString().trim();
                String path = download_pathEditText.getText().toString().trim();
                String file = file_nameEditText.getText().toString().trim();

                if (ip.isEmpty()) {
                    toast("Enter valid server ip");
                    return;
                }
                if (user.isEmpty()) {
                    toast("Enter valid user id");
                    return;
                }
                if (pwd.isEmpty()) {
                    toast("Enter valid password");
                    return;
                }
                if (path.isEmpty()) {
                    toast("Enter valid download path");
                    return;
                }
                if (file.isEmpty()) {
                    toast("Enter valid file name");
                    return;
                }
                creds.setServerIp(ip);
                creds.setUserId(user);
                creds.setPassword(pwd);
                creds.setPath(path);
                creds.setFileName(file);
                toast("Settings saved!");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String message) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        });
    }
}