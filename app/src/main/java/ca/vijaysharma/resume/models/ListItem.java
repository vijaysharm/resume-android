package ca.vijaysharma.resume.models;

import android.support.annotation.DrawableRes;

public class ListItem {
    public final @DrawableRes int logo;
    public final int index;

    public ListItem(int logo, int index) {
        this.logo = logo;
        this.index = index;
    }
}
