package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
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


public class DetailsActivity extends Activity {
    private static final String PARCELABLE_DATA_KEY = "details";

    @InjectView(R.id.hero_image) BezelImageView hero;
    @InjectView(R.id.action_1) BezelImageView action1;
    @InjectView(R.id.action_2) BezelImageView action2;
    @InjectView(R.id.body) LinearLayout body;
    @InjectView(R.id.description_1) TextView description1;
    @InjectView(R.id.description_2) TextView description2;
    @InjectView(R.id.description_3) TextView description3;
    @InjectView(R.id.container) ViewGroup container;

    public static Intent start(Context context, DetailParcel...parcels) {
        Intent intent = new Intent(context, DetailsActivity.class);

        ArrayList<DetailParcel> data = new ArrayList<>();
        Collections.addAll(data, parcels);
        intent.putParcelableArrayListExtra(PARCELABLE_DATA_KEY, data);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);

        applyInsets(container, statusBarHeight(this));

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

    private static int statusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static int toolbarHeight(Context context) {
        final TypedArray styledAttributes =
            context.getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.actionBarSize
            });
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
    private static void applyInsets(ViewGroup container, final int toolbarHeight) {
        container.setFitsSystemWindows(true);
        container.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                view.setPadding(0, toolbarHeight, 0, insets.getSystemWindowInsetBottom());
                return insets.consumeSystemWindowInsets();
            }
        });
    }
}
