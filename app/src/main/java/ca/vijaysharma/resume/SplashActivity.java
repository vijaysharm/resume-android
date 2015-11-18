package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Map;

import ca.vijaysharma.resume.network.ResumeService;
import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class SplashActivity extends Activity {
    private static int[] RESOURCES = {
        R.drawable.android_256,
        R.drawable.apple_256,
        R.drawable.avatar,
        R.drawable.cloud_256,
        R.drawable.concordia,
        R.drawable.datacap,
        R.drawable.email_48,
        R.drawable.email_256,
        R.drawable.github_256,
        R.drawable.globe_256,
        R.drawable.html5_256,
        R.drawable.intelerad,
        R.drawable.kwilt,
        R.drawable.linkedin_256,
        R.drawable.person_image_empty,
        R.drawable.robarts,
        R.drawable.signiant,
        R.drawable.stackoverflow_256,
        R.drawable.storage_256,
        R.drawable.toptal,
        R.drawable.twitter_256,
        R.drawable.testfairy,
        R.drawable.younility
    };

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
                    preload(SplashActivity.this);
                    startActivity(ResumeActivity.start(SplashActivity.this));
                    finishAfterTransition();
                }

                @Override
                public void onNext(String data) {
                    storage.save(data);
                    preload(SplashActivity.this);
                    startActivity(ResumeActivity.start(SplashActivity.this));
                    finishAfterTransition();
                }
            });
    }

    private static void preload(Context context) {
        for (int resource : RESOURCES) {
            Picasso.with(context).load(resource).fetch();
        }
    }
}
