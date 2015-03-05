package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.utils.BezelImageView;
import ca.vijaysharma.resume.utils.RoundedTransformation;

// TODO: Need a toolbar and the body should be a View Pager
public class DetailActivity extends Activity {
    private static final String PARCELABLE_DATA_KEY = "details";

    @InjectView(R.id.hero_image) BezelImageView hero;
    @InjectView(R.id.action_1) BezelImageView action1;
    @InjectView(R.id.action_2) BezelImageView action2;
    @InjectView(R.id.body) LinearLayout body;
    @InjectView(R.id.description_1) TextView description1;
    @InjectView(R.id.description_2) TextView description2;
    @InjectView(R.id.description_3) TextView description3;

    public static Intent start(Context context, DetailParcel...parcels) {
        Intent intent = new Intent(context, DetailActivity.class);

        ArrayList<DetailParcel> data = new ArrayList<>();
        Collections.addAll(data, parcels);
        intent.putParcelableArrayListExtra(PARCELABLE_DATA_KEY, data);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);

        ArrayList<DetailParcel> details = getIntent().getParcelableArrayListExtra(PARCELABLE_DATA_KEY);
        DetailParcel detail = details.get(0);

        description1.setText(detail.detail1());
        description2.setText(detail.detail2());
        description3.setText(detail.detail3());

        int heroSize = (int)getResources().getDimension(R.dimen.circle_image_diameter);
        Picasso.with(this)
            .load(detail.hero())
            .placeholder(R.color.background_color)
            .centerCrop()
            .resize(heroSize, heroSize)
            .transform(new RoundedTransformation())
            .into(hero);
    }
}
