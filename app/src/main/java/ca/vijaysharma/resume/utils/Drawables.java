package ca.vijaysharma.resume.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorRes;

import ca.vijaysharma.resume.R;

public class Drawables {

    public static Drawable borderDrawable(Context context, @ColorRes int primaryColor) {
        int borderThickness = (int)context.getResources().getDimension(R.dimen.circle_item_border_width);
        int color = context.getResources().getColor(primaryColor);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setStroke(borderThickness, color);
        shape.setColor(null);

        return shape;
    }

    public static Drawable rippleDrawable(Context context, @ColorRes int primaryColor) {
        int borderThickness = (int)context.getResources().getDimension(R.dimen.circle_item_border_width);
        int color = context.getResources().getColor(primaryColor);
        int background = context.getResources().getColor(R.color.background_color);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setStroke(borderThickness, color);
        shape.setColor(background);

        RippleDrawable ripple = new RippleDrawable(ColorStateList.valueOf(color), shape, null);
        ripple.setPaddingMode(RippleDrawable.PADDING_MODE_STACK);

        return ripple;
    }
}
