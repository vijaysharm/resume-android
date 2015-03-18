package ca.vijaysharma.resume.events;

import android.view.View;

import ca.vijaysharma.resume.parcelable.DetailParcel;

public class ShowDetailsEvent {
    private final DetailParcel parcel;
    private final View view;

    public ShowDetailsEvent(DetailParcel parcel, View view) {
        this.parcel = parcel;
        this.view = view;
    }

    public DetailParcel getParcel() {
        return parcel;
    }

    public View getView() {
        return view;
    }
}
