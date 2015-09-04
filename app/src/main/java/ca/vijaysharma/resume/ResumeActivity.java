package ca.vijaysharma.resume;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Toolbar;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.vijaysharma.resume.adapters.EducationAdapter;
import ca.vijaysharma.resume.adapters.ExperienceAdapter;
import ca.vijaysharma.resume.adapters.ProfileAdapter;
import ca.vijaysharma.resume.adapters.SkillsAdapter;
import ca.vijaysharma.resume.adapters.SocialAdapter;
import ca.vijaysharma.resume.events.IntentEvent;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.utils.Metrics;
import de.greenrobot.event.EventBus;

public class ResumeActivity extends AppCompatActivity {
    public static Intent start(Context context) {
        return new Intent(context, ResumeActivity.class);
    }

    @Bind(R.id.container) ViewGroup container;
    @Bind(R.id.me) ViewPager profile;
    @Bind(R.id.experience) ViewPager experience;
    @Bind(R.id.skills) ViewPager skills;
    @Bind(R.id.education) ViewPager education;
    @Bind(R.id.social) ViewPager social;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private EventBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.bind(this);
        bus = EventBus.getDefault();
        Storage storage = new Storage(this);

        setActionBar(toolbar);
        getActionBar().setTitle(null);
        applyInsets(container, toolbarHeight(this));

        Map<String, Object> resume = storage.read();
        preparePager(profile, new ProfileAdapter(this, bus, storage, ResumeData.profile(resume)));
        preparePager(experience, new ExperienceAdapter(this, bus, storage, ResumeData.experiences(resume)));
        preparePager(social, new SocialAdapter(this, bus, storage, ResumeData.social(resume)));
        preparePager(skills, new SkillsAdapter(this, bus, storage, ResumeData.skills(resume)));
        preparePager(education, new EducationAdapter(this, bus, storage, ResumeData.education(resume)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(ShowDetailsEvent event) {
        Intent intent = DetailsActivity.start(this, event.getParcel());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            event.getView(),
            "hero"
        );

        startActivity(intent, options.toBundle());
    }

    @SuppressWarnings("unused")
    public void onEvent(IntentEvent event) {
        startActivity(event.getIntent());
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

    private void preparePager(ViewPager pager, PagerAdapter adapter) {
        int screenWidthPx = Metrics.size(this).x;
        int pageMarginPx = -1 * screenWidthPx / 2;

        pager.setAdapter(adapter);
        pager.setPageMargin(pageMarginPx);
        pager.setOffscreenPageLimit(3);
    }
}
