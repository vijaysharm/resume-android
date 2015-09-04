package ca.vijaysharma.resume.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.URLEncoder;

public class Intents {
    public static Intent createEmailIntent(String email) {
        return new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
    }

    public static Intent createLocationIntent(Context context, String address) {
        try {
            String query = URLEncoder.encode(address, "utf-8");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=" + query));

            if (! context.getPackageManager().queryIntentActivities(intent, 0).isEmpty())
                return intent;

            intent.setData(Uri.parse("https://maps.google.com/?q=" + query));
            return intent;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Intent createUrlIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        return intent;
    }

    public static Intent createEmptyIntent() {
        Intent intent = new Intent();
        intent.putExtra("EMPTY_INTENT", true);

        return intent;
    }

    public static boolean isEmpty(Intent intent) {
        return intent.getBooleanExtra("EMPTY_INTENT", false);
    }
}
