package ca.vijaysharma.resume.utils;

import android.content.Intent;
import android.net.Uri;

public class Intents {
    public static Intent createEmailIntent(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);

        return intent;
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
