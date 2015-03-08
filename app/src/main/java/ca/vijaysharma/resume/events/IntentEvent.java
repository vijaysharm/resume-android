package ca.vijaysharma.resume.events;

import android.content.Intent;

import ca.vijaysharma.resume.utils.Intents;

public class IntentEvent {
    private final Intent intent;

    public static IntentEvent emailEvent(String email) {
        Intent intent = Intents.createEmailIntent(email);
        Intent chooser = Intent.createChooser(intent, "Send Email");

        return new IntentEvent(chooser);
    }

    public static IntentEvent urlEvent(String url) {
        Intent intent = Intents.createUrlIntent(url);
        Intent chooser = Intent.createChooser(intent, "Open page " + url);

        return new IntentEvent(chooser);
    }

    public IntentEvent(Intent event) {
        this.intent = event;
    }

    public Intent getIntent() {
        return intent;
    }
}
