package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class ReferenceItemSection implements Parcelable {
    public static ReferenceItemSection create(String name, String position, @DrawableRes int avatar) {
        return new AutoParcel_ReferenceItemSection(name, position, avatar);
    }

    public abstract String name();
    public abstract String position();
    public abstract @DrawableRes int avatar();
}
