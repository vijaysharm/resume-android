package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.utils.BezelImageView;
import ca.vijaysharma.resume.utils.Typefaces;


public class DetailsActivity extends Activity {
    private static final String PARCELABLE_DATA_KEY = "details";

    public static Intent start(Context context, DetailParcel...parcels) {
        Intent intent = new Intent(context, DetailsActivity.class);

        ArrayList<DetailParcel> data = new ArrayList<>();
        Collections.addAll(data, parcels);
        intent.putParcelableArrayListExtra(PARCELABLE_DATA_KEY, data);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int toolbarHeight = toolbarHeight(this);
        int marginFromEdge = (int)getResources().getDimension(R.dimen.margin_from_edge);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        scrollView.addView(frameLayout);

        View background = new View(this);
        int backgroundHeight = (int)getResources().getDimension(R.dimen.background_view_height);
        background.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, backgroundHeight));
        background.setBackgroundColor(getResources().getColor(R.color.white));
        frameLayout.addView(background);

        BezelImageView hero = new BezelImageView(this);
        int heroDiameter = (int)getResources().getDimension(R.dimen.circle_image_diameter);
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(heroDiameter, heroDiameter);
        frameLayoutParams.setMargins(marginFromEdge, toolbarHeight, 0, 0);
        hero.setLayoutParams(frameLayoutParams);
        hero.setScaleType(ImageView.ScaleType.CENTER_CROP);
        hero.setMaskDrawable(getResources().getDrawable(R.drawable.circle_mask));
        hero.setClickable(false);
        hero.setFocusable(false);
        hero.setImageResource(R.drawable.avatar);
        frameLayout.addView(hero);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        frameLayoutParams.setMargins(0, toolbarHeight, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;
        linearLayout.setLayoutParams(frameLayoutParams);
        frameLayout.addView(linearLayout);

        TextView title1 = new TextView(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.END;
        title1.setLayoutParams(linearLayoutParams);
        float textSize = getResources().getDimension(R.dimen.title_1_text_size) / getResources().getDisplayMetrics().density;
        title1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        title1.setText("Vijay Sharma");
        title1.setTextColor(getResources().getColor(android.R.color.black));
        linearLayout.addView(title1);

        TextView title2 = new TextView(this);
        title2.setLayoutParams(linearLayoutParams);
        textSize = getResources().getDimension(R.dimen.title_2_text_size) / getResources().getDisplayMetrics().density;
        title2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        title2.setText("Mobile Developer");
        title2.setTextColor(getResources().getColor(android.R.color.black));
        title2.setTypeface(Typefaces.get(getString(R.string.light)), Typeface.ITALIC);
        linearLayout.addView(title2);

        TextView title3 = new TextView(this);
        title3.setLayoutParams(linearLayoutParams);
        textSize = getResources().getDimension(R.dimen.title_3_text_size) / getResources().getDisplayMetrics().density;
        title3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        title3.setText("Ottawa");
        title3.setTextColor(getResources().getColor(android.R.color.darker_gray));
        title3.setTypeface(Typefaces.get(getString(R.string.thin)));
        linearLayout.addView(title3);

        int actionItemDiameter = (int)getResources().getDimension(R.dimen.action_item_diameter);
        int actionItemRadius = actionItemDiameter / 2;
        int actionButtonTopMargin = backgroundHeight - actionItemRadius;
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        frameLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        frameLayoutParams.setMargins(0, actionButtonTopMargin, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;
        linearLayout.setLayoutParams(frameLayoutParams);
        frameLayout.addView(linearLayout);

        int spacing = (int)getResources().getDimension(R.dimen.space_between_action_items);
        ImageButton action1 = new ImageButton(this);
        linearLayoutParams = new LinearLayout.LayoutParams(actionItemDiameter, actionItemDiameter);
        linearLayoutParams.gravity = Gravity.END;
        linearLayoutParams.setMargins(0, 0, spacing, 0);
        action1.setLayoutParams(linearLayoutParams);
        action1.setBackground(getDrawable(R.drawable.white_circle));
        action1.setImageResource(R.drawable.ic_public_white_24dp);
        linearLayout.addView(action1);

        ImageButton action2 = new ImageButton(this);
        linearLayoutParams = new LinearLayout.LayoutParams(actionItemDiameter, actionItemDiameter);
        linearLayoutParams.gravity = Gravity.END;
        linearLayoutParams.setMargins(0, 0, 0, 0);
        action2.setLayoutParams(linearLayoutParams);
        action2.setBackground(getDrawable(R.drawable.white_circle));
        action2.setImageResource(R.drawable.ic_email_white_24dp);
        linearLayout.addView(action2);

        setContentView(scrollView);

        applyInsets(scrollView, statusBarHeight(this));
    }

    private static int statusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static int toolbarHeight(Context context) {
        final TypedArray styledAttributes =
                context.getTheme().obtainStyledAttributes(new int[] {
                    android.R.attr.actionBarSize
                });
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    private static void applyInsets(ViewGroup container, final int toolbarHeight) {
        container.setFitsSystemWindows(true);
        container.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                view.setPadding(0, toolbarHeight, 0, insets.getSystemWindowInsetBottom());
                return insets.consumeSystemWindowInsets();
            }
        });
    }
}
