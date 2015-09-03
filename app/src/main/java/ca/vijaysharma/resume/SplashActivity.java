package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Map;

import ca.vijaysharma.resume.network.ResumeService;
import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        final Storage storage = new Storage(this);
        String endpoint = getString(R.string.base_url);
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(endpoint)
            .build();
        ResumeService service = restAdapter.create(ResumeService.class);
        service
            .resume()
            .map(new Func1<Map<String, Object>, String>() {
                @Override
                public String call(Map<String, Object> data) {
                    Gson gson = new Gson();
                    return gson.toJson(data);
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    // TODO: If there's no data, then send the user to a friendly
                    // TODO: activity that says they need internet to get resume
                    startActivity(ResumeActivity.start(SplashActivity.this));
                    finishAfterTransition();
                }

                @Override
                public void onNext(String data) {
                    storage.save(data);
                    startActivity(ResumeActivity.start(SplashActivity.this));
                    finishAfterTransition();
                }
            });
    }
}
