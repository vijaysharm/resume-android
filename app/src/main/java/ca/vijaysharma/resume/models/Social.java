package ca.vijaysharma.resume.models;

import android.support.annotation.DrawableRes;

public class Social {
    private final String name;
    private final String url;
    private final int icon;

    public Social(String name, String url, @DrawableRes int icon) {
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getIcon() {
        return icon;
    }
}
