package ca.vijaysharma.resume.models;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import org.joda.time.DateTime;

public class Experience {
    private final @DrawableRes int logo;
    private final @ColorRes int primaryColor;
    private final String name;
    private final String position;
    private final DateTime start;
    private final DateTime end;
    private final String website;
    private final String location;
    private final String summary;
    private final String [] jobs;
    private final String [] skills;
    private final Reference[] references;

    public Experience(
        @DrawableRes int logo,
        @ColorRes int primaryColor,
        String name,
        String position,
        DateTime start,
        DateTime end,
        String website,
        String location,
        String summary,
        String[] jobs,
        String[] skills,
        Reference[] references
    ) {
        this.logo = logo;
        this.primaryColor = primaryColor;
        this.name = name;
        this.position = position;
        this.start = start;
        this.end = end;
        this.website = website;
        this.location = location;
        this.summary = summary;
        this.jobs = jobs;
        this.skills = skills;
        this.references = references;
    }

    public @DrawableRes int getLogo() {
        return logo;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getSummary() {
        return summary;
    }

    public String[] getJobs() {
        return jobs;
    }

    public String[] getSkills() {
        return skills;
    }

    public Reference[] getReferences() {
        return references;
    }
}
