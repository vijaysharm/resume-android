package ca.vijaysharma.resume.events;

import android.content.Intent;
import android.net.Uri;

public class IntentEvent {
    private final Intent intent;

    public static IntentEvent emailEvent(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        Intent chooser = Intent.createChooser(intent, "Send Email");

        return new IntentEvent(chooser);
    }

    public static IntentEvent urlEvent(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        Intent chooser = Intent.createChooser(i, "Open page " + url);

        return new IntentEvent(chooser);
    }

    public IntentEvent(Intent event) {
        this.intent = event;
    }

    public Intent getIntent() {
        return intent;
    }
}
