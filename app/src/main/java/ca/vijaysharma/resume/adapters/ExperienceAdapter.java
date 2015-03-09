package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.utils.Action1;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Intents;
import de.greenrobot.event.EventBus;

public class ExperienceAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;

    public ExperienceAdapter(Context context, EventBus bus) {
        this.context = context;
        this.bus = bus;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null;

        if (position == 0) {
            view = new TextButtonBuilder<>(this.context, null)
                .setText("Experience")
                .setConnectorColor(this.context.getResources().getColor(R.color.purple))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.purple_circle_button))
                .setAddConnection(position != 0)
                .setListener(new Action1<Object>() {
                    @Override
                    public void call(Object item) {
                        // Do nothing
                    }
                })
                .build();
        } else if (position == 1) {
            view = new ImageButtonBuilder<>(this.context, new Object())
                .setConnectorColor(this.context.getResources().getColor(R.color.purple))
                .setBackgroundDrawable(Drawables.borderDrawable(this.context, R.color.purple))
                .setImage(R.drawable.younility)
                .setAddConnection(position != 0)
                .setListener(new Action1<Object>() {
                    @Override
                    public void call(Object item) {
                        DetailParcel parcel = DetailParcel.builder()
                            .detail1("Younility")
                            .detail2("Senior Mobile Developer")
                            .detail3("5 months")
                            .hero(R.drawable.younility)
                            .primaryColor(R.color.younility)
                            .action1(DetailAction.builder()
                                .action(R.drawable.ic_public_white_24dp)
                                .intent(Intents.createUrlIntent("http://www.vijaysharma.ca"))
                                .build())
                            .action2(DetailAction.builder()
                                .action(R.drawable.ic_email_white_24dp)
                                .intent(Intents.createEmailIntent("vijay.sharm@gmail.com"))
                                .build())
                            .build();

                        bus.post(new ShowDetailsEvent(parcel));
                    }
                })
                .build();
        } else if (position == 2) {
            view = new ImageButtonBuilder<>(this.context, new Object())
                .setConnectorColor(this.context.getResources().getColor(R.color.purple))
                .setBackgroundDrawable(Drawables.borderDrawable(this.context, R.color.purple))
                .setImage(R.drawable.signiant)
                .setAddConnection(position != 0)
                .setListener(new Action1<Object>() {
                    @Override
                    public void call(Object item) {
                        DetailParcel parcel = DetailParcel.builder()
                                .detail1("Signiant")
                                .detail2("Senior Mobile Developer")
                                .detail3("10 months")
                                .hero(R.drawable.signiant)
                                .primaryColor(R.color.signiant)
                                .action1(DetailAction.builder()
                                    .action(R.drawable.ic_public_white_24dp)
                                    .intent(Intents.createUrlIntent("http://www.vijaysharma.ca"))
                                    .build())
                                .action2(DetailAction.builder()
                                    .action(R.drawable.ic_email_white_24dp)
                                    .intent(Intents.createEmailIntent("vijay.sharm@gmail.com"))
                                    .build())
                                .build();

                        bus.post(new ShowDetailsEvent(parcel));
                    }
                })
                .build();
        }

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