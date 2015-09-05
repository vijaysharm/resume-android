package ca.vijaysharma.resume.parcelable;

import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import ca.vijaysharma.resume.models.Project;

@AutoParcel
public abstract class ProjectSectionItem implements Parcelable {
    public static ArrayList<ProjectSectionItem> items(List<Project> projects) {
        ArrayList<ProjectSectionItem> items = new ArrayList<>(projects.size());
        for (Project project : projects) {
            ArrayList<Integer> locals = new ArrayList<>(project.locals.length);
            for (int local: project.locals) {
                locals.add(local);
            }

            ArrayList<Uri> remotes = new ArrayList<>(project.remote.length);
            for (String remote : project.remote) {
                remotes.add(Uri.parse(remote));
            }

            items.add(ProjectSectionItem.create(
                project.name,
                TextUtils.isEmpty(project.url) ? Uri.EMPTY : Uri.parse(project.url),
                project.description,
                locals,
                remotes
            ));
        }

        return items;
    }
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
