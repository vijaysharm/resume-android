package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;

import java.util.ArrayList;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class ReferenceSection implements Parcelable, Section {
    public static ReferenceSection create(String name, ArrayList<ReferenceItemSection> items) {
        return new AutoParcel_ReferenceSection(name, items);
    }

    public abstract String name();
    public abstract ArrayList<ReferenceItemSection> items();
}
