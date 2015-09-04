package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

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
import de.greenrobot.event.EventBus;

public class EducationAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;
    private final Storage storage;
    private final List<Education> education;

    public EducationAdapter(
        Context context,
        EventBus bus,
        Storage storage,
        List<Education> education
    ) {
        this.context = context;
        this.bus = bus;
        this.storage = storage;
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
                .setConnectorColor(this.context.getResources().getColor(R.color.blue))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.blue_circle_button))
                .setAddConnection(false)
                .build();
        } else {
            final Education school = education.get(position - 1);
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.blue))
                .setBackgroundDrawable(Drawables.doubleBorderDrawable(this.context, R.color.blue))
                .setImage(school.logo)
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
            .hero(education.logo)
            .back(R.drawable.ic_arrow_back_white_24dp)
            .primaryColor(education.primaryColor)
            .secondaryColor(education.secondaryColor)
            .tertiaryColor(education.tertiaryColor)
            .background(R.color.background_color)
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
        final Years years = Years.yearsBetween(experience.start, experience.end);
        if (years.getYears() != 0) {
            return years.getYears() == 1 ? years.getYears() + " year" : years.getYears() + " years";
        }

        final Months months = Months.monthsBetween(experience.start, experience.end);
        if (months.getMonths() != 0) {
            return months.getMonths() == 1 ? months.getMonths() + " month" : months.getMonths() + " months";
        }

        final Days days = Days.daysBetween(experience.start, experience.end);
        return days.getDays() + " days";
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
