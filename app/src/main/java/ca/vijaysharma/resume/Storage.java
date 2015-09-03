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

    public Map<String, Object> read() {
        String data = preferences.getString("RESUME_DATA", null);
        if (data == null)
            return Collections.emptyMap();

        return gson.fromJson(data, Map.class);
    }
}
