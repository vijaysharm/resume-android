package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.List;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.ResumeData;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.models.Experience;
import ca.vijaysharma.resume.models.ListItem;
import ca.vijaysharma.resume.models.Projects;
import ca.vijaysharma.resume.models.Reference;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.ReferenceSectionItem;
import ca.vijaysharma.resume.parcelable.ReferenceSection;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.parcelable.TextSection;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Intents;
import ca.vijaysharma.resume.utils.Lists;
import de.greenrobot.event.EventBus;

public class ExperienceAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;
    private final Storage storage;
    private final List<ListItem> experiences;
    private final Projects projects;

    public ExperienceAdapter(
        Context context,
        EventBus bus,
        Storage storage,
        List<ListItem> experiences,
        Projects projects
    ) {
        this.context = context;
        this.bus = bus;
        this.storage = storage;
        this.experiences = experiences;
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return 1 + experiences.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null;

        if (position == 0) {
            view = new TextButtonBuilder<>(this.context, null)
                .setText("Experience")
                .setConnectorColor(this.context.getResources().getColor(R.color.purple))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.purple_circle_button))
                .setAddConnection(false)
                .build();
        } else {
            final ListItem item = experiences.get(position - 1);
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.purple))
                .setBackgroundDrawable(Drawables.doubleBorderDrawable(this.context, R.color.purple))
                .setImage(item.logo)
                .setAddConnection(true)
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show(item.index, view);
                    }
                })
                .build();
        }

        collection.addView(view);

        return view;
    }

    private void show(int index, View view) {
        final Experience experience = ResumeData.experienceDetail(index, storage.read());
        final Section company = TextSection.create("Company", Lists.newArrayList(experience.getSummary()));
        final Section work = TextSection.create("Experience", Lists.newArrayList(experience.getJobs()));

        ArrayList<ReferenceSectionItem> items = new ArrayList<>();
        for (Reference reference : experience.getReferences()) {
            items.add(ReferenceSectionItem.create(
                    reference.getName(),
                    reference.getPosition(),
                    reference.getAvatar())
            );
        }
        final Section references = ReferenceSection.create("References", items);
        DetailParcel parcel = DetailParcel.builder()
            .detail1(experience.getName())
            .detail2(experience.getPosition())
            .detail3(duration(experience))
            .hero(experience.getLogo())
            .back(R.drawable.ic_arrow_back_white_24dp)
            .primaryColor(experience.getPrimaryColor())
            .secondaryColor(experience.getSecondaryColor())
            .tertiaryColor(experience.getTertiaryColor())
            .background(experience.getPrimaryColor())
            .action1(DetailAction.builder()
                .action(R.drawable.ic_public_white_24dp)
                .intent(Intents.createUrlIntent(experience.getWebsite()))
                .build())
            .action2(DetailAction.builder()
                .action(R.drawable.ic_place_white_24dp)
                .intent(Intents.createLocationIntent(context, experience.getLocation()))
                .build())
            .sections(Lists.newArrayList(
                company, work, references
            ))
            .build();

        bus.post(new ShowDetailsEvent(parcel, view));
    }

    private String duration(Experience experience) {
        final Years years = Years.yearsBetween(experience.getStart(), experience.getEnd());
        if (years.getYears() != 0) {
            return years.getYears() == 1 ? years.getYears() + " year" : years.getYears() + " years";
        }

        final Months months = Months.monthsBetween(experience.getStart(), experience.getEnd());
        if (months.getMonths() != 0) {
            return months.getMonths() == 1 ? months.getMonths() + " month" : months.getMonths() + " months";
        }

        final Days days = Days.daysBetween(experience.getStart(), experience.getEnd());
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