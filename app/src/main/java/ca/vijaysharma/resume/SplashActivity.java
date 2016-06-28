package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import ca.vijaysharma.resume.network.ResumeService;
import retrofit.RestAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class SplashActivity extends Activity {
    private static int[] RESOURCES = {

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        final Storage storage = new Storage(this);
        final String endpoint = getString(R.string.base_url);
        final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(endpoint)
            .build();
        final ResumeService service = restAdapter.create(ResumeService.class);
        final Gson gson = new Gson();

        Observable<String> resume = service
                .resume()
                .map(new Func1<Map<String, Object>, String>() {
                    @Override
                    public String call(Map<String, Object> data) {
                        return gson.toJson(data);
                    }
                })
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        String data = storage.read();
                        if (data != null && !data.isEmpty())
                            return data;

                        return resume();
                    }
                });
//                .onErrorReturn(new Func1<Throwable, String>() {
//                    @Override
//                    public String call(Throwable throwable) {
//                        String data = storage.read();
//                        if (data != null && !data.isEmpty())
//                            return data;
//
//                        return metadata();
//                    }
//                });

        Observable.combineLatest(service.resume(), service.metadata(), new Func2<Map<String,Object>, Map<String,Object>, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(Map<String, Object> resume, Map<String, Object> metadata) {
                Map<String, Object> data = new HashMap<>();
                data.put("resume", resume);
                data.put("metadata", metadata);

                return data;
            }
        }).map(new Func1<Map<String, Object>, String>() {
            @Override
            public String call(Map<String, Object> data) {
                return gson.toJson(data);
            }
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<String>() {
                @Override
                public void call(String data) {
                    storage.save(data);
                    preload(SplashActivity.this);
                    startActivity(ResumeActivity.start(SplashActivity.this));
                    finishAfterTransition();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    // TODO: If there's no data, then send the user to a friendly
                    // TODO: activity that says they need internet to get resume
                    preload(SplashActivity.this);
                    startActivity(ResumeActivity.start(SplashActivity.this));
                    finishAfterTransition();
                }
            });
    }

    private String resume() {
        try {
            final Resources resources = getResources();
            final InputStream is = resources.openRawResource(R.raw.resume);
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");

            final char[] buffer = new char[1024];
            final StringWriter s = new StringWriter();

            int n;
            while ((n = reader.read(buffer, 0, buffer.length)) != -1) {
                s.write(buffer, 0, n);
            }

            return s.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private String metadata() {
        try {
            final Resources resources = getResources();
            final InputStream is = resources.openRawResource(R.raw.resume);
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");

            final char[] buffer = new char[1024];
            final StringWriter s = new StringWriter();

            int n;
            while ((n = reader.read(buffer, 0, buffer.length)) != -1) {
                s.write(buffer, 0, n);
            }

            return s.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static void preload(Context context) {
        for (int resource : RESOURCES) {
            Picasso.with(context).load(resource).fetch();
        }
    }
}
