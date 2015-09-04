package ca.vijaysharma.resume.parcelable;

import android.net.Uri;
import android.os.Parcelable;

import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class ProjectSectionItem implements Parcelable {
    public static ProjectSectionItem create(
        String name,
        Uri link,
        String description,
        List<Integer> locals,
        List<Uri> remotes
    ) {
        return new AutoParcel_ProjectSectionItem(name, description, link, locals, remotes);
    }

    public abstract String name();
    public abstract String description();
    public abstract Uri link();
    public abstract List<Integer> locals();
    public abstract List<Uri> remotes();
}
