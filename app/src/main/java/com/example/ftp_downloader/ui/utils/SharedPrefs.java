package com.example.ftp_downloader.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Shared preferences helper class
 *
 * @author Karthick.L
 * @version 1
 * @since 20-03-2025
 */
public class SharedPrefs {
    private final SharedPreferences sharedPref;

    public SharedPrefs(Context ctx) {
        sharedPref = ctx.getSharedPreferences("FTP_DOWNLOADER", Context.MODE_PRIVATE);
    }

    /**
     * Insert modify a String value based on its key.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.  Passing {@code null}
     *              for this argument will remove the string.
     */
    public void putString(String key, String value) {
        sharedPref.edit().putString(key, value).apply();

    }

    /**
     * Insert modify a int value based on its key.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.  Passing {@code null}
     *              for this argument will remove the string.
     */
    public void putInt(String key, int value) {
        sharedPref.edit().putInt(key, value).apply();
    }

    /**
     * Insert modify a int value based on its key.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.  Passing {@code null}
     *              for this argument will remove the string.
     */
    public void putLong(String key, long value) {
        sharedPref.edit().putLong(key, value).apply();
    }

    /**
     * Insert modify a boolean value based on its key.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.  Passing {@code null}
     *              for this argument will remove the string.
     */
    public void putBoolean(String key, boolean value) {
        sharedPref.edit().putBoolean(key, value).apply();
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or null if not exist.
     */
    public String getString(String key) {
        try {
            return sharedPref.getString(key, null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public String getString(String key, String defaultValue) {
        try {
            return sharedPref.getString(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or false if not exists.
     */
    public boolean getBoolean(String key) {
        try {
            return sharedPref.getBoolean(key, false);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return sharedPref.getBoolean(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or -1 if not exists.
     */
    public int getInt(String key) {
        try {
            return sharedPref.getInt(key, -1);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or default value.
     */
    public int getInt(String key, int defaultValue) {
        try {
            return sharedPref.getInt(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or -1 if not exists.
     */
    public long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or default value.
     */
    public long getLong(String key, long defaultValue) {
        try {
            return sharedPref.getLong(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }


}
