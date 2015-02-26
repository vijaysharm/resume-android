package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        experience.setAdapter(new ProfileAdapter(this));
        social.setAdapter(new ProfileAdapter(this));
        skills.setAdapter(new ProfileAdapter(this));
    }

    private static class ProfileAdapter extends PagerAdapter {
        private final Context context;

        public ProfileAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
//            ImageView view = new ImageView(this.context);
//            view.setImageResource(R.drawable.avatar);
//
//            return view;
            TextView view = new TextView(this.context);
            view.setText("View "  + position);
            view.setTextColor(this.context.getResources().getColor(android.R.color.white));
            view.setBackgroundColor(this.context.getResources().getColor(android.R.color.holo_red_dark));
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
