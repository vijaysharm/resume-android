package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.IntentEvent;
import ca.vijaysharma.resume.models.Social;
import ca.vijaysharma.resume.utils.Action1;
import ca.vijaysharma.resume.utils.Drawables;
import de.greenrobot.event.EventBus;

public class SocialAdapter extends PagerAdapter implements Action1<Object> {
    private final Context context;
    private final EventBus bus;
    private final List<Social> socials;

    public SocialAdapter(Context context, EventBus bus, Storage storage, List<Social> social) {
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
                .setConnectorColor(this.context.getResources().getColor(R.color.yellow))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.yellow_circle_button))
                .setAddConnection(position != 0)
                .setListener(this)
                .build();
            collection.addView(view);
        } else {
            final Social social = socials.get(position - 1);
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.yellow))
                .setBorderDrawable(Drawables.borderDrawable(this.context, R.color.yellow))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, R.color.yellow))
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

    @Override
    public void call(Object item) {
        Log.i("SocialAdapter", "Object: " + item);
    }
}