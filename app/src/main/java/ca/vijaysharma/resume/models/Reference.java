package ca.vijaysharma.resume.models;

import android.support.annotation.DrawableRes;

public class Reference {
    private final String name;
    private final String position;
    private final @DrawableRes int avatar;

    public Reference(
        String name,
        String position,
        @DrawableRes int avatar
    ) {
        this.name = name;
        this.position = position;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getAvatar() {
        return avatar;
    }
}
