package ca.vijaysharma.resume.events;

import ca.vijaysharma.resume.parcelable.DetailParcel;

public class ShowDetailsEvent {
    private final DetailParcel parcel;

    public ShowDetailsEvent(DetailParcel parcel) {
        this.parcel = parcel;
    }

    public DetailParcel getParcel() {
        return parcel;
    }
}
