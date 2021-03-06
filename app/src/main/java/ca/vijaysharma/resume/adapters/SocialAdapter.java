package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.IntentEvent;
import ca.vijaysharma.resume.models.Social;
import ca.vijaysharma.resume.utils.Drawables;
import de.greenrobot.event.EventBus;

public class SocialAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;
    private final List<Social> socials;

    public SocialAdapter(Context context, EventBus bus, List<Social> social) {
        this.context = context;
        this.bus = bus;
        this.socials = social;
    }

    @Override
    public int getCount() {
        return 1 + socials.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null;
        if (position == 0) {
            view = new TextButtonBuilder<>(this.context, null)
                .setText("Social")
                .setConnectorColor(ContextCompat.getColor(context, R.color.yellow))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.yellow_circle_button))
                .setAddConnection(false)
                .build();
            collection.addView(view);
        } else {
            final Social social = socials.get(position - 1);
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.yellow))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, ContextCompat.getColor(context, R.color.yellow)))
                .setAddConnection(true)
                .setImage(social.getIcon())
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bus.post(IntentEvent.urlEvent(social.getUrl()));
                    }
                })
                .build();
            collection.addView(view);
        }

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