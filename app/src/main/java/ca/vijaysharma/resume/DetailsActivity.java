package ca.vijaysharma.resume;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.ReferenceItemSection;
import ca.vijaysharma.resume.parcelable.ReferenceSection;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.parcelable.TextSection;
import ca.vijaysharma.resume.utils.BezelImageView;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Metrics;
import ca.vijaysharma.resume.utils.ObservableScrollView;
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

    private BezelImageView hero;
    private TextView title1;
    private TextView title2;
    private TextView title3;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<DetailParcel> details = getIntent().getParcelableArrayListExtra(PARCELABLE_DATA_KEY);
        final DetailParcel detail = details.get(0);

        int toolbarHeight = Metrics.toolbarHeight(this);
        int marginFromEdge = (int)getResources().getDimension(R.dimen.margin_from_edge);
        int statusBarHeight = Metrics.statusBarHeight(this);

        ObservableScrollView scrollView = new ObservableScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        scrollView.addCallbacks(new ObservableScrollView.Callbacks() {
            @Override
            public void onScrollChanged(ObservableScrollView view, int deltaX, int deltaY) {
                handleScroll(view, deltaX, deltaY);
            }
        });
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ));
        scrollView.addView(frameLayout);

        toolbar = getActionBar();
        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(detail.primaryColor())));

        View background = new View(this);
        int backgroundHeight = (int)getResources().getDimension(R.dimen.background_view_height);
        background.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            backgroundHeight
        ));
        background.setBackgroundColor(getResources().getColor(detail.primaryColor()));
        frameLayout.addView(background);


        hero = new BezelImageView(this);
        int heroDiameter = (int)getResources().getDimension(R.dimen.circle_item_diameter);
        int heroImageDiameter = (int)getResources().getDimension(R.dimen.circle_image_diameter);
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(heroDiameter, heroDiameter);
        frameLayoutParams.setMargins(marginFromEdge, toolbarHeight, 0, 0);
        hero.setLayoutParams(frameLayoutParams);
        hero.setScaleType(ImageView.ScaleType.CENTER_CROP);
        hero.setMaskDrawable(getResources().getDrawable(R.drawable.circle_mask));
        hero.setBorderDrawable(Drawables.borderDrawable(this, detail.primaryColor()));
        hero.setClickable(false);
        hero.setFocusable(false);
        frameLayout.addView(hero);
        Picasso.with(this)
            .load(detail.hero())
            .placeholder(R.color.background_color)
            .centerCrop()
            .resize(heroImageDiameter, heroImageDiameter)
            .into(hero);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        frameLayoutParams.setMargins(0, toolbarHeight, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;
        linearLayout.setLayoutParams(frameLayoutParams);
        frameLayout.addView(linearLayout);

        title1 = new TextView(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.END;
        title1.setLayoutParams(linearLayoutParams);
        float textSize = getResources().getDimension(R.dimen.title_1_text_size) / getResources().getDisplayMetrics().density;
        title1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        title1.setText(detail.detail1());
        title1.setTextColor(getResources().getColor(detail.secondaryColor()));
        linearLayout.addView(title1);

        if (!TextUtils.isEmpty(detail.detail2())) {
            title2 = new TextView(this);
            title2.setLayoutParams(linearLayoutParams);
            textSize = getResources().getDimension(R.dimen.title_2_text_size) / getResources().getDisplayMetrics().density;
            title2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            title2.setText(detail.detail2());
            title2.setTextColor(getResources().getColor(detail.secondaryColor()));
            title2.setTypeface(Typefaces.get(getString(R.string.light)), Typeface.ITALIC);
            linearLayout.addView(title2);
        }

        if (!TextUtils.isEmpty(detail.detail3())) {
            title3 = new TextView(this);
            title3.setLayoutParams(linearLayoutParams);
            textSize = getResources().getDimension(R.dimen.title_3_text_size) / getResources().getDisplayMetrics().density;
            title3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            title3.setText(detail.detail3());
            title3.setTextColor(getResources().getColor(detail.tertiaryColor()));
            title3.setTypeface(Typefaces.get(getString(R.string.thin)));
            linearLayout.addView(title3);
        }

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
        final ImageButton action1 = new ImageButton(this);
        linearLayoutParams = new LinearLayout.LayoutParams(actionItemDiameter, actionItemDiameter);
        linearLayoutParams.gravity = Gravity.END;
        linearLayoutParams.setMargins(0, 0, spacing, 0);
        action1.setLayoutParams(linearLayoutParams);
        action1.setBackground(Drawables.rippleDrawable(this, detail.primaryColor()));
        Picasso.with(this)
            .load(detail.action1().action())
            .placeholder(R.color.background_color)
            .into(action1, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    action1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(detail.action1().intent());
                        }
                    });
                }
            });
        linearLayout.addView(action1);

        final ImageButton action2 = new ImageButton(this);
        linearLayoutParams = new LinearLayout.LayoutParams(actionItemDiameter, actionItemDiameter);
        linearLayoutParams.gravity = Gravity.END;
        linearLayoutParams.setMargins(0, 0, 0, 0);
        action2.setLayoutParams(linearLayoutParams);
        action2.setBackground(Drawables.rippleDrawable(this, detail.primaryColor()));
        Picasso.with(this)
            .load(detail.action2().action())
            .placeholder(R.color.background_color)
            .into(action2, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    action2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(detail.action2().intent());
                        }
                    });
                }
            });
        linearLayout.addView(action2);

        int bodyMarginTop = toolbarHeight + heroDiameter; // + statusBarHeight
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        frameLayoutParams.setMargins(marginFromEdge, bodyMarginTop, marginFromEdge, 0);
        linearLayout.setLayoutParams(frameLayoutParams);
        frameLayout.addView(linearLayout);

        final ArrayList<Section> sections = detail.sections();
        for (int index = 0; index < sections.size(); index++) {
            Section section = sections.get(index);
            if (section instanceof TextSection) {
                TextSection textSection = (TextSection) section;
                addTextSection(detail.primaryColor(), textSection, linearLayout);
            } else if (section instanceof ReferenceSection) {
                ReferenceSection referenceSection = (ReferenceSection)section;
                addReferenceSection(detail.primaryColor(), referenceSection, linearLayout);
            }
        }

//        DesignSpec designSpec = DesignSpec.fromResource(scrollView, R.raw.details_spec);
//        designSpec.setKeylinesColor(getResources().getColor(R.color.yellow));
//        designSpec.setSpacingsColor(getResources().getColor(R.color.yellow));
//        designSpec.setBaselineGridColor(getResources().getColor(R.color.yellow));
//        scrollView.getOverlay().add(designSpec);
        setContentView(scrollView);

        applyInsets(scrollView, statusBarHeight);
    }

    private void sectionTitle(int primaryColor, String titleString, LinearLayout linearLayout) {
        int titleLeftMargin = (int)getResources().getDimension(R.dimen.body_section_margin);
        int edgeMargin = (int)getResources().getDimension(R.dimen.margin_from_edge);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );

        int spacer = (int)getResources().getDimension(R.dimen.space_between_sections);
        linearLayoutParams.setMargins(titleLeftMargin, spacer, edgeMargin, 0);

        TextView title = new TextView(this);
        title.setLayoutParams(linearLayoutParams);
        float textSize = getResources().getDimension(R.dimen.body_text_section_title) / getResources().getDisplayMetrics().density;
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        title.setText(titleString);
        title.setAllCaps(true);
        title.setTextColor(getResources().getColor(primaryColor));
        title.setTypeface(Typefaces.get(getString(R.string.sans_serif)), Typeface.BOLD);
        linearLayout.addView(title);
    }

    private void addReferenceSection(
        @ColorRes int primaryColor,
        ReferenceSection detail,
        LinearLayout linearLayout
    ) {
        sectionTitle(primaryColor, detail.name(), linearLayout);

        int edgeMargin = (int) getResources().getDimension(R.dimen.margin_from_edge);
        int itemSpacer = (int) getResources().getDimension(R.dimen.space_between_body_items);
        int firstItemSpacer = (int)getResources().getDimension(R.dimen.space_between_body_first_item);

        final ArrayList<ReferenceItemSection> items = detail.items();
        for (int index = 0; index < items.size(); index++) {
            ReferenceItemSection section = items.get(index);
            TextView name = new TextView(this);
            float textSize = getResources().getDimension(R.dimen.reference_name_text_size) / getResources().getDisplayMetrics().density;
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            name.setText(section.name());
            name.setTextColor(getResources().getColor(R.color.white));
            name.setTypeface(Typefaces.get(getString(R.string.thin)));

            TextView position = new TextView(this);
            textSize = getResources().getDimension(R.dimen.reference_position_text_size) / getResources().getDisplayMetrics().density;
            position.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            position.setText(section.position());
            position.setTextColor(getResources().getColor(R.color.white));
            position.setTypeface(Typefaces.get(getString(R.string.thin)));

            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayoutParams.setMargins(edgeMargin, 0, 0, 0);
            LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(linearLayoutParams);
            l.setOrientation(LinearLayout.VERTICAL);
            l.addView(name);
            l.addView(position);

            BezelImageView avatar = new BezelImageView(this);
            int heroDiameter = (int) getResources().getDimension(R.dimen.reference_avatar_diameter);
            linearLayoutParams = new LinearLayout.LayoutParams(
                heroDiameter,
                heroDiameter
            );
            avatar.setLayoutParams(linearLayoutParams);
            avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
            avatar.setMaskDrawable(getResources().getDrawable(R.drawable.circle_mask));
            avatar.setClickable(false);
            avatar.setFocusable(false);
            Picasso.with(this)
                    .load(section.avatar())
                    .placeholder(R.color.background_color)
                    .into(avatar);

            linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayoutParams.setMargins(0, index == 0 ? firstItemSpacer : itemSpacer, 0, 0);
            LinearLayout r = new LinearLayout(this);
            r.setLayoutParams(linearLayoutParams);
            r.setOrientation(LinearLayout.HORIZONTAL);
            r.addView(avatar);
            r.addView(l);

            linearLayout.addView(r);
        }
    }

    private void addTextSection(
        @ColorRes int primaryColor,
        TextSection detail,
        LinearLayout linearLayout
    ) {
        sectionTitle(primaryColor, detail.name(), linearLayout);

        int leftMargin = (int)getResources().getDimension(R.dimen.body_section_margin);
        int rightMargin = (int)getResources().getDimension(R.dimen.margin_from_edge);
        int itemSpacer = (int)getResources().getDimension(R.dimen.space_between_body_items);
        int firstItemSpacer = (int)getResources().getDimension(R.dimen.space_between_body_first_item);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );

        final ArrayList<String> items = detail.items();
        for (int index = 0; index < items.size(); index++) {
            linearLayoutParams.setMargins(leftMargin, index == 0 ? firstItemSpacer : itemSpacer, rightMargin, 0);
            String item = items.get(index);
            TextView content = new TextView(this);
            content.setLayoutParams(linearLayoutParams);
            float textSize = getResources().getDimension(R.dimen.body_text_section_content) / getResources().getDisplayMetrics().density;
            content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            content.setText(item);
            content.setTextColor(getResources().getColor(R.color.white));
            content.setTypeface(Typefaces.get(getString(R.string.thin)));
            linearLayout.addView(content);
        }
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

    private void handleScroll(ObservableScrollView view, int deltaX, int deltaY) {
        Log.d("DetailsActivity", "Scroll: " + view.getScrollY());

        int maxHeight = Metrics.toolbarHeight(this);
        float percentScrolled = 1 - ((float)view.getScrollY() / maxHeight);
        percentScrolled = Math.max(0, percentScrolled);
        Log.d("DetailsActivity", "Scale: " + percentScrolled);

        hero.setScaleX(percentScrolled);
        hero.setScaleY(percentScrolled);
    }
}
