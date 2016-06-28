package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.models.Skill;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.ProjectSection;
import ca.vijaysharma.resume.parcelable.ProjectSectionItem;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.parcelable.TextSection;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Intents;
import ca.vijaysharma.resume.utils.Lists;
import ca.vijaysharma.resume.utils.Times;
import de.greenrobot.event.EventBus;

import static ca.vijaysharma.resume.utils.Strings.join;

public class SkillsAdapter extends PagerAdapter {
    private final Context context;
    private final EventBus bus;
    private final List<Skill> skills;

    public SkillsAdapter(Context context, EventBus bus, List<Skill> skills) {
        this.context = context;
        this.bus = bus;
        this.skills = skills;
    }

    @Override
    public int getCount() {
        return 1 + skills.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        if (position == 0) {
            View view = new TextButtonBuilder<>(this.context, null)
                .setText("Skills")
                .setConnectorColor(ContextCompat.getColor(context, R.color.green))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.green_circle_button))
                .setAddConnection(position != 0)
                .build();

            collection.addView(view);
            return view;
        } else {
            final Skill skill = skills.get(position - 1);
            View view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.green))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, ContextCompat.getColor(context, R.color.green)))
                .setImage(skill.logoUrl)
                .setAddConnection(true)
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DetailParcel parcel = DetailParcel.builder()
                            .detail1(skill.name)
                            .detail2(Times.duration(skill.start, skill.end))
                            .detail3("")
                            .hero(skill.logoUrl)
                            .back(R.drawable.ic_arrow_back_white_24dp)
                            .primaryColor(skill.primary)
                            .secondaryColor(ContextCompat.getColor(context, R.color.white))
                            .tertiaryColor(ContextCompat.getColor(context, R.color.white))
                            .background(ContextCompat.getColor(context, R.color.background_color))
                            .action1(DetailAction.builder()
                                .action(R.drawable.ic_public_white_24dp)
                                .intent(Intents.createEmptyIntent())
                                .build())
                            .action2(DetailAction.builder()
                                .action(R.drawable.ic_place_white_24dp)
                                .intent(Intents.createEmptyIntent())
                                .build())
                            .sections(sections(skill))
                            .build();

                        bus.post(new ShowDetailsEvent(parcel, view));
                    }
                })
                .build();
            collection.addView(view);
            return view;
        }
    }

    @NonNull
    private ArrayList<Section> sections(Skill skill) {
        ArrayList<Section> sections = new ArrayList<>();
        if (! skill.beginner.isEmpty())
            sections.add(TextSection.create("Beginner Knowledge", Lists.newArrayList(join(skill.beginner))));

        if (! skill.intermediate.isEmpty())
            sections.add(TextSection.create("Intermediate Knowledge", Lists.newArrayList(join(skill.intermediate))));

        if (! skill.advanced.isEmpty())
            sections.add(TextSection.create("Advanced Knowledge", Lists.newArrayList(join(skill.advanced))));

        if (skill.projects.isEmpty())
            return sections;

        sections.add(ProjectSection.create("Relevant Projects", ProjectSectionItem.items(skill.projects)));
        return sections;
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