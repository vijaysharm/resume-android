package ca.vijaysharma.resume;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.Map;

public class Storage {
    private final SharedPreferences preferences;
    private final Gson gson;

    public Storage(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        gson = new Gson();
    }

    public void save(String data) {
        preferences.edit().putString("RESUME_DATA", data).apply();
    }

    public String read() {
        return preferences.getString("RESUME_DATA", null);
    }

    public Map<String, Object> load() {
        String data = read();
        if (data == null) return Collections.emptyMap();

        return gson.fromJson(data, Map.class);
    }
}
