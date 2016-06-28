package ca.vijaysharma.resume.models;

import android.support.annotation.DrawableRes;

import java.util.ArrayList;

public class Profile {
    private final String name;
    private final String avatarUrl;
    private final String location;
    private final String email;
    private final String title;
    private final String website;
    private final ArrayList<String> awards;
    private final String biography;
    private final String objective;

    public Profile(
        String name,
        String avatarUrl,
        String email,
        String website,
        String location,
        String title,
        String biography,
        String objective,
        ArrayList<String> awards
    ) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.location = location;
        this.biography = biography;
        this.objective = objective;
        this.title = title;
        this.website = website;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getBiography() {
        return biography;
    }

    public String getObjective() {
        return objective;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public ArrayList<String> getAwards() {
        return awards;
    }
}
