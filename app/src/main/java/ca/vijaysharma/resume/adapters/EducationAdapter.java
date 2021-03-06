package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.models.Education;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.parcelable.TextSection;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Intents;
import ca.vijaysharma.resume.utils.Lists;
import ca.vijaysharma.resume.utils.Times;
import de.greenrobot.event.EventBus;

public class EducationAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;
    private final List<Education> education;

    public EducationAdapter(
        Context context,
        EventBus bus,
        List<Education> education
    ) {
        this.context = context;
        this.bus = bus;
        this.education = education;
    }

    @Override
    public int getCount() {
        return 1 + education.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null;

        if (position == 0) {
            view = new TextButtonBuilder<>(this.context, null)
                .setText("Education")
                .setConnectorColor(ContextCompat.getColor(context, R.color.blue))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.blue_circle_button))
                .setAddConnection(false)
                .build();
        } else {
            final Education school = education.get(position - 1);
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.blue))
                .setBackgroundDrawable(Drawables.doubleBorderDrawable(this.context, ContextCompat.getColor(context, R.color.blue)))
                .setImage(school.logoUrl)
                .setAddConnection(true)
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show(school, view);
                    }
                })
                .build();
        }

        collection.addView(view);

        return view;
    }

    private void show(Education education, View view) {
        final Section work = TextSection.create("Summary", Lists.newArrayList(education.responsibilities));
        DetailParcel parcel = DetailParcel.builder()
            .detail1(education.name)
            .detail2(education.position)
            .detail3(duration(education))
            .hero(education.logoUrl)
            .back(R.drawable.ic_arrow_back_white_24dp)
            .primaryColor(education.primaryColor)
            .secondaryColor(education.secondaryColor)
            .tertiaryColor(education.tertiaryColor)
            .background(ContextCompat.getColor(context, R.color.background_color))
            .action1(DetailAction.builder()
                .action(R.drawable.ic_public_white_24dp)
                .intent(Intents.createUrlIntent(education.site))
                .build())
            .action2(DetailAction.builder()
                .action(R.drawable.ic_place_white_24dp)
                .intent(Intents.createLocationIntent(context, education.address))
                .build())
            .sections(Lists.newArrayList(
                work
            ))
            .build();

        bus.post(new ShowDetailsEvent(parcel, view));
    }

    private String duration(Education experience) {
        return Times.duration(experience.start, experience.end);
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
