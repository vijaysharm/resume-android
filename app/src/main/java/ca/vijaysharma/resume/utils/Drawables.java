package ca.vijaysharma.resume.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import ca.vijaysharma.resume.R;

public class Drawables {

    public static Drawable doubleBorderDrawable(Context context, int primaryColor) {
        return doubleBorderDrawable(context, primaryColor, ContextCompat.getColor(context, R.color.background_color));
    }

    public static Drawable doubleBorderDrawable(Context context, int primaryColor, int background) {
        int borderThickness = (int)context.getResources().getDimension(R.dimen.circle_item_border_width);
        int color = primaryColor;
        int innerColor = background;

        GradientDrawable outer = new GradientDrawable();
        outer.setShape(GradientDrawable.OVAL);
        outer.setStroke(borderThickness, color);
        outer.setColor(null);

        GradientDrawable inner = new GradientDrawable();
        inner.setShape(GradientDrawable.OVAL);
        inner.setColor(ColorStateList.valueOf(innerColor));

        InsetDrawable inset = new InsetDrawable(inner, borderThickness);

        return new LayerDrawable(new Drawable[]{outer, inset});
    }

    public static Drawable rippleDrawable(Context context, int primaryColor) {
        int borderThickness = (int)context.getResources().getDimension(R.dimen.circle_item_border_width);
        int color = primaryColor;
        int background = ContextCompat.getColor(context, R.color.background_color);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setStroke(borderThickness, color);
        shape.setColor(background);

        RippleDrawable ripple = new RippleDrawable(ColorStateList.valueOf(color), shape, null);
        ripple.setPaddingMode(RippleDrawable.PADDING_MODE_STACK);

        return ripple;
    }
}
