package ca.vijaysharma.resume.models;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import org.joda.time.DateTime;

public class Experience {
    private final String logoUrl;
    private final int primaryColor;
    private final int secondaryColor;
    private final int tertiaryColor;
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
        String logo,
        int primaryColor,
        int secondaryColor,
        int tertiaryColor,
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
        this.logoUrl = logo;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.tertiaryColor = tertiaryColor;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public @ColorRes int getPrimaryColor() {
        return primaryColor;
    }

    public @ColorRes int getSecondaryColor() {
        return secondaryColor;
    }

    public @ColorRes int getTertiaryColor() {
        return tertiaryColor;
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
