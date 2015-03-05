package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class DetailParcel implements Parcelable {
    public static Builder builder() {
        return new AutoParcel_DetailParcel.Builder();
    }

    public abstract String detail1();
    public abstract String detail2();
    public abstract String detail3();
    public abstract @DrawableRes int hero();

    @AutoParcel.Builder
    public interface Builder {
        Builder detail1(String detail);
        Builder detail2(String detail);
        Builder detail3(String detail);
        Builder hero(@DrawableRes int id);

        DetailParcel build();
    }
}
