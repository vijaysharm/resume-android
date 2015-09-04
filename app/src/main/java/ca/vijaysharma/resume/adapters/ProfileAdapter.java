package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.IntentEvent;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.models.Profile;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.parcelable.TextSection;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Intents;
import ca.vijaysharma.resume.utils.Lists;
import de.greenrobot.event.EventBus;

public class ProfileAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;
    private final Profile profile;

    public ProfileAdapter(
        Context context,
        EventBus bus,
        Storage storage,
        Profile profile
    ) {
        this.context = context;
        this.bus = bus;
        this.profile = profile;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null;
        if (position == 0) {
            final Section objective = TextSection.create("Objective", Lists.newArrayList(profile.getObjective()));
            final Section biography = TextSection.create("Bio", Lists.newArrayList(profile.getBiography()));
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.white))
                .setBackgroundDrawable(Drawables.doubleBorderDrawable(this.context, R.color.white))
                .setAddConnection(false)
                .setImage(profile.getAvatarId())
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DetailParcel parcel = DetailParcel.builder()
                            .detail1(profile.getName())
                            .detail2(profile.getTitle())
                            .detail3(profile.getLocation())
                            .hero(profile.getAvatarId())
                            .back(R.drawable.ic_arrow_back_black_24dp)
                            .primaryColor(R.color.white)
                            .secondaryColor(R.color.black)
                            .tertiaryColor(R.color.dark_grey)
                            .background(R.color.white)
                            .action1(DetailAction.builder()
                                .action(R.drawable.ic_public_white_24dp)
                                .intent(Intents.createUrlIntent(profile.getWebsite()))
                                .build())
                            .action2(DetailAction.builder()
                                .action(R.drawable.email_48)
                                .intent(Intents.createEmailIntent(profile.getEmail()))
                                .build())
                            .sections(Lists.newArrayList(
                                objective, biography
                            ))
                            .build();

                        bus.post(new ShowDetailsEvent(parcel, view));
                    }
                })
                .build();
        } else if (position == 1) {
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.white))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, R.color.white))
                .setAddConnection(true)
                .setImage(R.drawable.globe_256)
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bus.post(IntentEvent.urlEvent(profile.getWebsite()));
                    }
                })
                .build();
        } else if (position == 2) {
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.white))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, R.color.white))
                .setAddConnection(true)
                .setImage(R.drawable.email_256)
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bus.post(IntentEvent.emailEvent(profile.getEmail()));
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