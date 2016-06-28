package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class ReferenceSectionItem implements Parcelable {
    public static ReferenceSectionItem create(String name, String position, String avatarUrl) {
        return new AutoParcel_ReferenceSectionItem(name, position, avatarUrl);
    }

    public abstract String name();
    public abstract String position();
    public abstract String avatarUrl();
}
