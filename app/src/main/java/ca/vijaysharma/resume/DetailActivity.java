package ca.vijaysharma.resume;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.vijaysharma.resume.utils.BezelImageView;

// TODO: Need a toolbar and the body should be a View Pager
public class DetailActivity extends Activity {
    @InjectView(R.id.hero_image) BezelImageView hero;
    @InjectView(R.id.action_1) BezelImageView action1;
    @InjectView(R.id.action_2) BezelImageView action2;
    @InjectView(R.id.body) LinearLayout body;
    @InjectView(R.id.description_1) TextView description1;
    @InjectView(R.id.description_2) TextView description2;
    @InjectView(R.id.description_3) TextView description3;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);
    }
}
