package ca.vijaysharma.resume;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.vijaysharma.resume.adapters.ExperienceAdapter;
import ca.vijaysharma.resume.adapters.ProfileAdapter;
import ca.vijaysharma.resume.adapters.SkillsAdapter;
import ca.vijaysharma.resume.adapters.SocialAdapter;


public class ResumeActivity extends Activity {
    @InjectView(R.id.container) ViewGroup container;
    @InjectView(R.id.me) ViewPager profile;
    @InjectView(R.id.experience) ViewPager experience;
    @InjectView(R.id.skills) ViewPager skills;
    @InjectView(R.id.social) ViewPager social;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.inject(this);

        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
            new int[] { android.R.attr.actionBarSize });
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.action_bar);
        applyInsets(toolbarHeight);

        preparePager(profile, new ProfileAdapter(this));
        preparePager(experience, new ExperienceAdapter(this));
        preparePager(social, new SocialAdapter(this));
        preparePager(skills, new SkillsAdapter(this));
    }

    private void applyInsets(final int toolbarHeight) {
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
        pager.setAdapter(adapter);
        pager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.row_item_margins));
        pager.setOffscreenPageLimit(2);
    }
}
