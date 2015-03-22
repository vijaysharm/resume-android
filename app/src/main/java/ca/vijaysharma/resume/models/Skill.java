package ca.vijaysharma.resume.models;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

public class Skill {
    private final String name;
    private final @DrawableRes int logo;
    private final @ColorRes int primary;

    public Skill(String name, int logo, int primary) {
        this.name = name;
        this.logo = logo;
        this.primary = primary;
    }

    public String getName() {
        return name;
    }

    public int getLogo() {
        return logo;
    }

    public @ColorRes int getPrimaryColor() {
        return primary;
    }
}
