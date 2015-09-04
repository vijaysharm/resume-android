package ca.vijaysharma.resume.models;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import org.joda.time.DateTime;

public class Education {
    public final int logo;
    public final int primaryColor;
    public final int secondaryColor;
    public final int tertiaryColor;
    public final String name;
    public final String address;
    public final String position;
    public final String site;
    public final DateTime start;
    public final DateTime end;
    public final String[] responsibilities;

    public Education(
        @DrawableRes int logo,
        @ColorRes int primaryColor,
        @ColorRes int secondaryColor,
        @ColorRes int tertiaryColor,
        String name,
        String address,
        String position,
        String site,
        DateTime start,
        DateTime end,
        String[] responsibilities
    ) {
        this.logo = logo;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.tertiaryColor = tertiaryColor;
        this.name = name;
        this.address = address;
        this.position = position;
        this.site = site;
        this.start = start;
        this.end = end;
        this.responsibilities = responsibilities;
    }
}
