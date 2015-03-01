package ca.vijaysharma.resume.utils;

import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class Typefaces {
    private static final String TAG = "Typefaces";

    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public static Typeface get(String font) {
        synchronized (cache) {
            if (!cache.containsKey(font)) {
                try {
//                    Typeface t = Typeface.createFromAsset(c.getAssets(),
//                            assetPath);
                    Typeface t = Typeface.create(font, Typeface.NORMAL);
                    cache.put(font, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + font
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(font);
        }
    }
}