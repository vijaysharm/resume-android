package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.models.Skill;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.utils.Action1;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Intents;
import de.greenrobot.event.EventBus;

public class SkillsAdapter extends PagerAdapter implements Action1<Object> {
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
                .setConnectorColor(this.context.getResources().getColor(R.color.green))
                .setBackgroundDrawable(this.context.getDrawable(R.drawable.green_circle_button))
                .setAddConnection(position != 0)
                .setListener(this)
                .build();

            collection.addView(view);
            return view;
        } else {
            final Skill skill = skills.get(position - 1);
            View view = new ImageButtonBuilder(this.context)
                .setConnectorColor(this.context.getResources().getColor(R.color.green))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, R.color.green))
                .setImage(skill.getLogo())
                .setAddConnection(true)
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DetailParcel parcel = DetailParcel.builder()
                            .detail1(skill.getName())
                            .detail2("")
                            .detail3("")
                            .hero(skill.getLogo())
                            .back(R.drawable.ic_arrow_back_white_24dp)
                            .primaryColor(skill.getPrimaryColor())
                            .secondaryColor(R.color.white)
                            .tertiaryColor(R.color.white)
                            .action1(DetailAction.builder()
                                .action(R.drawable.ic_public_white_24dp)
                                .intent(Intents.createEmptyIntent())
                                .build())
                            .action2(DetailAction.builder()
                                .action(R.drawable.ic_place_white_24dp)
                                .intent(Intents.createEmptyIntent())
                                .build())
                            .sections(new ArrayList<Section>())
//                            .sections(Lists.newArrayList(
//                                company, work, references
//                            ))
                            .build();

                        bus.post(new ShowDetailsEvent(parcel, view));
                    }
                })
                .build();
            collection.addView(view);
            return view;
        }
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
        Log.i("SkillsAdapter", "Object: " + item);
    }
}