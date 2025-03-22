package com.example.ftp_downloader.ui.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class ServerCredentials {
    private static final String PREF_NAME = "FTPServerPrefs";
    private static final String SERVER_IP_KEY = "server_ip";
    private static final String USER_ID_KEY = "user_id";
    private static final String PASSWORD_KEY = "password";
    private static final String PATH_KEY = "path";
    private static final String FILE_NAME_KEY = "file_name";

    private SharedPreferences preferences;

    public ServerCredentials(Context ctx) {
        preferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getServerIP() {
        return preferences.getString(SERVER_IP_KEY, "192.168.51.4");
    }

    public String getUserId() {
        return preferences.getString(USER_ID_KEY, "mes");
    }

    public String getPassword() {
        return preferences.getString(PASSWORD_KEY, "mes");
    }

    public String getPath() {
        return preferences.getString(PATH_KEY, "/ANDROID_APK/");
    }

    public String getNormalizedPath() {
        String path = getPath();
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }

    public String getFileName() {
        return preferences.getString(FILE_NAME_KEY, "Seoyon.apk");
    }

    public boolean validateCredentials() {
        return getServerIP() != null && !getServerIP().isEmpty()
                && getUserId() != null && !getUserId().isEmpty()
                && getPassword() != null && !getPassword().isEmpty();
    }

    public void setServerIp(String baseUrl) {
        preferences.edit().putString(SERVER_IP_KEY, baseUrl).apply();
    }

    public void setUserId(String userId) {
        preferences.edit().putString(USER_ID_KEY, userId).apply();
    }

    public void setPassword(String password) {
        preferences.edit().putString(PASSWORD_KEY, password).apply();
    }

    public void setPath(String path) {
        preferences.edit().putString(PATH_KEY, path).apply();
    }

    public void setFileName(String fileName) {
        preferences.edit().putString(FILE_NAME_KEY, fileName).apply();
    }
}
