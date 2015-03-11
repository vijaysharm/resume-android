package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;

import java.util.ArrayList;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class TextSection implements Parcelable, Section {
    public static TextSection create(String name, ArrayList<String> items) {
        return new AutoParcel_TextSection(name, items);
    }

    public abstract String name();
    public abstract ArrayList<String> items();
}
