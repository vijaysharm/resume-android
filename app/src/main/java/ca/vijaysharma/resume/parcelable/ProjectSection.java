package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;

import java.util.ArrayList;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class ProjectSection implements Parcelable, Section {
    public static ProjectSection create(String name, ArrayList<ProjectSectionItem> projects) {
        return new AutoParcel_ProjectSection(name, projects);
    }

    public abstract String name();
    public abstract ArrayList<ProjectSectionItem> items();
}
