package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.vijaysharma.resume.BuildConfig;
import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.Storage;
import ca.vijaysharma.resume.events.IntentEvent;
import ca.vijaysharma.resume.events.ShowDetailsEvent;
import ca.vijaysharma.resume.models.Profile;
import ca.vijaysharma.resume.models.Project;
import ca.vijaysharma.resume.parcelable.DetailAction;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.ProjectSection;
import ca.vijaysharma.resume.parcelable.ProjectSectionItem;
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
        Profile profile
    ) {
        this.context = context;
        this.bus = bus;
        this.profile = profile;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null;
        if (position == 0) {
            final Section objective = TextSection.create("Objective", Lists.newArrayList(profile.getObjective()));
            final Section biography = TextSection.create("Bio", Lists.newArrayList(profile.getBiography()));
            final Section awards = TextSection.create("Awards", profile.getAwards());

            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.white))
                .setBackgroundDrawable(Drawables.doubleBorderDrawable(this.context, ContextCompat.getColor(context, R.color.white)))
                .setAddConnection(false)
                .setImage(profile.getAvatarUrl())
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DetailParcel parcel = DetailParcel.builder()
                            .detail1(profile.getName())
                            .detail2(profile.getTitle())
                            .detail3(profile.getLocation())
                            .hero(profile.getAvatarUrl())
                            .back(R.drawable.ic_arrow_back_black_24dp)
                            .primaryColor(ContextCompat.getColor(context, R.color.white))
                            .secondaryColor(ContextCompat.getColor(context, R.color.black))
                            .tertiaryColor(ContextCompat.getColor(context, R.color.dark_grey))
                            .background(ContextCompat.getColor(context, R.color.white))
                            .action1(DetailAction.builder()
                                .action(R.drawable.ic_public_white_24dp)
                                .intent(Intents.createUrlIntent(profile.getWebsite()))
                                .build())
                            .action2(DetailAction.builder()
                                .action(R.drawable.email_48)
                                .intent(Intents.createEmailIntent(profile.getEmail()))
                                .build())
                            .sections(Lists.newArrayList(
                                objective, biography, awards
                            ))
                            .build();

                        bus.post(new ShowDetailsEvent(parcel, view));
                    }
                })
                .build();
        } else if (position == 1) {
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.white))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, ContextCompat.getColor(context, R.color.white)))
                .setAddConnection(true)
                .setImage("https://cdn.rawgit.com/vijaysharm/resume-android/master/images/globe_256.png")
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bus.post(IntentEvent.urlEvent(profile.getWebsite()));
                    }
                })
                .build();
        } else if (position == 2) {
            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.white))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, ContextCompat.getColor(context, R.color.white)))
                .setAddConnection(true)
                .setImage("https://cdn.rawgit.com/vijaysharm/resume-android/master/images/email_256.png")
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bus.post(IntentEvent.emailEvent(profile.getEmail()));
                    }
                })
                .build();
        } else if (position == 3) {
            String version = String.format("%s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
            String build = String.format("%s (%s)", BuildConfig.GIT_SHA, BuildConfig.BUILD_TIME);
            final Section versionSection = TextSection.create("Version", Lists.newArrayList(version));
            final Section buildSection = TextSection.create("Build", Lists.newArrayList(build));
            final Section projects = ProjectSection.create("Libraries", ProjectSectionItem.items(projects()));

            view = new ImageButtonBuilder(this.context)
                .setConnectorColor(ContextCompat.getColor(context, R.color.white))
                .setBackgroundDrawable(Drawables.rippleDrawable(this.context, ContextCompat.getColor(context, R.color.white)))
                .setAddConnection(true)
                .setImage("https://cdn.rawgit.com/vijaysharm/resume-android/master/images/settings_256.png")
                .setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DetailParcel parcel = DetailParcel.builder()
                            .detail1("Settings")
                            .detail2("")
                            .detail3("")
                            .hero("https://cdn.rawgit.com/vijaysharm/resume-android/master/images/settings_256.png")
                            .back(R.drawable.ic_arrow_back_black_24dp)
                            .primaryColor(ContextCompat.getColor(context, R.color.white))
                            .secondaryColor(ContextCompat.getColor(context, R.color.black))
                            .tertiaryColor(ContextCompat.getColor(context, R.color.dark_grey))
                            .background(ContextCompat.getColor(context, R.color.background_color))
                            .action1(DetailAction.builder()
                                .action(R.drawable.ic_public_white_24dp)
                                .intent(Intents.createEmptyIntent())
                                .build())
                            .action2(DetailAction.builder()
                                .action(R.drawable.ic_place_white_24dp)
                                .intent(Intents.createEmptyIntent())
                                .build())
                            .sections(Lists.newArrayList(
                                versionSection, buildSection, projects
                            ))
                            .build();

                        bus.post(new ShowDetailsEvent(parcel, view));
                    }
                })
                .build();
        }

        collection.addView(view);

        return view;
    }

    private List<Project> projects() {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(new Project(
            "Android Suport Library",
            "Licensed under the Apache License, Version 2.0",
            "http://developer.android.com/tools/support-library/index.html",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Butterknife",
            "Licensed under the Apache License, Version 2.0",
            "http://jakewharton.github.io/butterknife/",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Picasso",
            "Licensed under the Apache License, Version 2.0",
            "http://square.github.io/picasso/",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Eventbus",
            "Licensed under the Apache License, Version 2.0",
            "https://github.com/greenrobot/EventBus",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Joda",
            "Licensed under the Apache License, Version 2.0",
            "https://github.com/dlew/joda-time-android",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Rebound",
            "Licensed under the BSD License",
            "http://facebook.github.io/rebound/",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Retrofit",
            "Licensed under the Apache License, Version 2.0",
            "http://square.github.io/retrofit/",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "RxJava",
            "Licensed under the Apache License, Version 2.0",
            "https://github.com/ReactiveX/RxJava",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "RxAndroid",
            "Licensed under the Apache License, Version 2.0",
            "https://github.com/ReactiveX/RxAndroid",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Auto-Parcel",
            "Licensed under the Apache License, Version 2.0",
            "https://github.com/frankiesardo/auto-parcel",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));
        projects.add(new Project(
            "Auto-Value",
            "Licensed under the Apache License, Version 2.0",
            "https://github.com/google/auto/tree/master/value",
            new int[0], new String[0], new String[0], Collections.<String>emptyList()
        ));

        return projects;
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