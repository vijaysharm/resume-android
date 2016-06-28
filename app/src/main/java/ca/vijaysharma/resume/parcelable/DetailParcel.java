package ca.vijaysharma.resume.parcelable;

import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class DetailParcel implements Parcelable {
    public static Builder builder() {
        return new AutoParcel_DetailParcel.Builder();
    }

    public abstract @ColorRes int primaryColor();
    public abstract @ColorRes int secondaryColor();
    public abstract @ColorRes int tertiaryColor();
    public abstract @ColorRes int background();
    public abstract @DrawableRes int back();

    public abstract String hero();
    public abstract String detail1();
    public abstract String detail2();
    public abstract String detail3();

    public abstract DetailAction action1();
    public abstract DetailAction action2();

    public abstract ArrayList<Section> sections();

    @AutoParcel.Builder
    public interface Builder {
        Builder hero(String url);
        Builder back(@DrawableRes int id);
        Builder primaryColor(@ColorRes int color);
        Builder secondaryColor(@ColorRes int color);
        Builder tertiaryColor(@ColorRes int color);
        Builder background(@ColorRes int color);

        Builder detail1(String detail);
        Builder detail2(String detail);
        Builder detail3(String detail);

        Builder action1(DetailAction a);
        Builder action2(DetailAction a);
        Builder sections(ArrayList<Section> sections);

        DetailParcel build();
    }
}
