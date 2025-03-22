package com.example.ftp_downloader.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftp_downloader.R;
import com.example.ftp_downloader.ui.data.prefs.ServerCredentials;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HomeActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView progressText;
    private ServerCredentials creds;
    private static String LOCAL_PATH;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        creds = new ServerCredentials(this);

        LOCAL_PATH = getExternalFilesDir(null) + "/FTP_APK/" + creds.getFileName();
        progressBar = findViewById(R.id.progressbar);
        progressText = findViewById(R.id.progresstext);

        requestPermissions();
        Listeners();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!getPackageManager().canRequestPackageInstalls()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                        .setData(Uri.parse(String.format("package:%s", getPackageName())));
                startActivityForResult(intent, 1234);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 1234) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (getPackageManager().canRequestPackageInstalls()) {
                    // Permission granted, proceed with the installation
                    installApk();
                } else {
                    Toast.makeText(this, "Permission denied to install APK", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void Listeners() {
        findViewById(R.id.settings).setOnClickListener(v -> startActivity(new Intent(this, ActivityFTPServerSettings.class)));
        findViewById(R.id.Download).setOnClickListener(v -> new DownloadApkTask().execute());
        findViewById(R.id.exit).setOnClickListener(v -> exit());
    }

    private void exit() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Exit", (dialogInterface, i) -> HomeActivity.this.finish())
                .setNegativeButton("No", null)
                .show();
    }

    private class DownloadApkTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            progressText.setText("0%");
            Toast.makeText(HomeActivity.this, "Starting Download...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(creds.getServerIP());
                ftpClient.login(creds.getUserId(), creds.getPassword());
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                FTPFile[] files = ftpClient.listFiles(creds.getNormalizedPath() + creds.getFileName());
                if (files == null || files.length == 0) {
                    error("File not found on the server");
                    Log.e("FTP_ERROR", "File not found on the server");
                    return false;
                }

                long fileSize = files[0].getSize();
                File localFile = new File(LOCAL_PATH);
                localFile.getParentFile().mkdirs();

                try (InputStream inputStream = ftpClient.retrieveFileStream(creds.getNormalizedPath() + creds.getFileName());
                     OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile))) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalBytesRead = 0;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                        publishProgress((int) (totalBytesRead * 100 / fileSize));
                    }

                    outputStream.flush();
                    return ftpClient.completePendingCommand();
                }
            } catch (IOException e) {
                error("Download error: " + e.getMessage());
                Log.e("FTP_ERROR", "Download error: " + e.getMessage());
                return false;
            } finally {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    error("Disconnection error: " + e.getMessage());
                    Log.e("FTP_ERROR", "Disconnection error: " + e.getMessage());
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0]);
            progressText.setText(progress[0] + "%");
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Toast.makeText(HomeActivity.this, success ? "Download Complete" : "Download Failed", Toast.LENGTH_SHORT).show();
            if (success) installApk();
        }
    }

    private void installApk() {
        File file = new File(LOCAL_PATH);
        if (file.exists()) {
            Uri apkUri = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                    FileProvider.getUriForFile(this, getPackageName() + ".provider", file) :
                    Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else {
            error("Installation file not found.");
            Toast.makeText(this, "Installation file not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void error(String msg) {
        runOnUiThread(() -> {
            new MaterialAlertDialogBuilder(HomeActivity.this)
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show();
        });
    }

}
