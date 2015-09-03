package ca.vijaysharma.resume;

import com.squareup.leakcanary.LeakCanary;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
