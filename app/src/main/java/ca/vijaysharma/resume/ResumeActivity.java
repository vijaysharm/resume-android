package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ResumeActivity extends Activity {
    @InjectView(R.id.me) ViewPager profile;
    @InjectView(R.id.experience) ViewPager experience;
    @InjectView(R.id.skills) ViewPager skills;
    @InjectView(R.id.social) ViewPager social;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.inject(this);

        profile.setAdapter(new ProfileAdapter(this));
        preparePager(profile);

        experience.setAdapter(new ProfileAdapter(this));
        preparePager(experience);

        social.setAdapter(new ProfileAdapter(this));
        preparePager(social);

        skills.setAdapter(new ProfileAdapter(this));
        preparePager(skills);
    }

    private void preparePager(ViewPager pager) {
        pager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.row_item_margins));
        pager.setOffscreenPageLimit(2);
    }

    private static class ProfileAdapter extends PagerAdapter {
        private final Context context;
        private final LayoutInflater inflater;

        public ProfileAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View view = inflater.inflate(R.layout.avatar_item, collection, false);
            collection.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
