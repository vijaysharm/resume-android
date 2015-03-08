package ca.vijaysharma.resume.parcelable;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class DetailAction implements Parcelable {
    public static Builder builder() {
        return new AutoParcel_DetailAction.Builder();
    }

    public abstract @DrawableRes int action();
    public abstract Intent intent();

    @AutoParcel.Builder
    public interface Builder {
        Builder intent(Intent intent);
        Builder action(@DrawableRes int action);

        DetailAction build();
    }
}
