package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.vijaysharma.resume.parcelable.DetailParcel;
import ca.vijaysharma.resume.parcelable.ReferenceItemSection;
import ca.vijaysharma.resume.parcelable.ReferenceSection;
import ca.vijaysharma.resume.parcelable.Section;
import ca.vijaysharma.resume.parcelable.TextSection;
import ca.vijaysharma.resume.utils.BezelImageView;
import ca.vijaysharma.resume.utils.Drawables;
import ca.vijaysharma.resume.utils.Metrics;
import ca.vijaysharma.resume.utils.ObservableScrollView;


public class DetailsActivity extends Activity {
    private static final String PARCELABLE_DATA_KEY = "details";

    public static Intent start(Context context, DetailParcel...parcels) {
        Intent intent = new Intent(context, DetailsActivity.class);

        ArrayList<DetailParcel> data = new ArrayList<>();
        Collections.addAll(data, parcels);
        intent.putParcelableArrayListExtra(PARCELABLE_DATA_KEY, data);

        return intent;
    }

    @InjectView(R.id.container) ObservableScrollView scrollView;
    @InjectView(R.id.background) View background;
    @InjectView(R.id.hero_image) BezelImageView hero;
    @InjectView(R.id.description_container) LinearLayout descriptionContainer;
    @InjectView(R.id.button_container) LinearLayout buttonContainer;
    @InjectView(R.id.body) LinearLayout body;
    @InjectView(R.id.description_1) TextView title1;
    @InjectView(R.id.description_2) TextView title2;
    @InjectView(R.id.description_3) TextView title3;
    @InjectView(R.id.action_1) ImageButton action1;
    @InjectView(R.id.action_2) ImageButton action2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);

        ArrayList<DetailParcel> details = getIntent().getParcelableArrayListExtra(PARCELABLE_DATA_KEY);
        final DetailParcel detail = details.get(0);

        int toolbarHeight = Metrics.toolbarHeight(this);
        int marginFromEdge = (int)getResources().getDimension(R.dimen.margin_from_edge);
        int statusBarHeight = Metrics.statusBarHeight(this);

        applyInsets(scrollView, statusBarHeight);

        scrollView.addCallbacks(new ObservableScrollView.Callbacks() {
            @Override
            public void onScrollChanged(ObservableScrollView view, int deltaX, int deltaY) {
                handleScroll(view, deltaX, deltaY);
            }
        });

        background.setBackgroundColor(getResources().getColor(detail.primaryColor()));

        int heroDiameter = (int)getResources().getDimension(R.dimen.circle_item_diameter);
        int heroImageDiameter = (int)getResources().getDimension(R.dimen.circle_image_diameter);
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(heroDiameter, heroDiameter);
        frameLayoutParams.setMargins(marginFromEdge, toolbarHeight, 0, 0);
        hero.setLayoutParams(frameLayoutParams);
        hero.setBorderDrawable(Drawables.borderDrawable(this, detail.primaryColor()));
        hero.setClickable(false);
        hero.setFocusable(false);
        Picasso.with(this)
            .load(detail.hero())
            .placeholder(R.color.background_color)
            .centerCrop()
            .resize(heroImageDiameter, heroImageDiameter)
            .into(hero);

        frameLayoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        frameLayoutParams.setMargins(0, toolbarHeight, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;
        descriptionContainer.setLayoutParams(frameLayoutParams);

        title1.setText(detail.detail1());
        title1.setTextColor(getResources().getColor(detail.secondaryColor()));

        title2.setText(detail.detail2());
        title2.setTextColor(getResources().getColor(detail.secondaryColor()));

        title3.setText(detail.detail3());
        title3.setTextColor(getResources().getColor(detail.tertiaryColor()));

        int backgroundHeight = (int)getResources().getDimension(R.dimen.background_view_height);
        int actionItemDiameter = (int)getResources().getDimension(R.dimen.action_item_diameter);
        int actionItemRadius = actionItemDiameter / 2;
        int actionButtonTopMargin = backgroundHeight - actionItemRadius;
        frameLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        frameLayoutParams.setMargins(0, actionButtonTopMargin, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;
        buttonContainer.setLayoutParams(frameLayoutParams);

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

        int bodyMarginTop = toolbarHeight + heroDiameter; // + statusBarHeight
        frameLayoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        frameLayoutParams.setMargins(marginFromEdge, bodyMarginTop, marginFromEdge, 0);
        body.setLayoutParams(frameLayoutParams);

        final ArrayList<Section> sections = detail.sections();
        for (int index = 0; index < sections.size(); index++) {
            Section section = sections.get(index);
            if (section instanceof TextSection) {
                TextSection textSection = (TextSection) section;
                addTextSection(detail.primaryColor(), textSection, body);
            } else if (section instanceof ReferenceSection) {
                ReferenceSection referenceSection = (ReferenceSection)section;
                addReferenceSection(detail.primaryColor(), referenceSection, body);
            }
        }

//        DesignSpec designSpec = DesignSpec.fromResource(scrollView, R.raw.details_spec);
//        designSpec.setKeylinesColor(getResources().getColor(R.color.yellow));
//        designSpec.setSpacingsColor(getResources().getColor(R.color.yellow));
//        designSpec.setBaselineGridColor(getResources().getColor(R.color.yellow));
//        scrollView.getOverlay().add(designSpec);

    }

    private void sectionTitle(int primaryColor, String titleString, LinearLayout linearLayout) {
        final LayoutInflater inflater = LayoutInflater.from(this);
        final TextView title = (TextView) inflater.inflate(R.layout.text_detail_section_header, linearLayout, false);
        title.setText(titleString);
        title.setTextColor(getResources().getColor(primaryColor));
        linearLayout.addView(title);
    }

    private void addReferenceSection(
        @ColorRes int primaryColor,
        ReferenceSection detail,
        LinearLayout linearLayout
    ) {
        sectionTitle(primaryColor, detail.name(), linearLayout);

        int itemSpacer = (int) getResources().getDimension(R.dimen.space_between_body_items);
        int firstItemSpacer = (int)getResources().getDimension(R.dimen.space_between_body_first_item);

        final LayoutInflater inflater = LayoutInflater.from(this);

        final ArrayList<ReferenceItemSection> items = detail.items();
        for (int index = 0; index < items.size(); index++) {
            ReferenceItemSection section = items.get(index);
            View view = inflater.inflate(R.layout.reference_detail_section_body, linearLayout, false);

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(section.name());

            TextView position = (TextView) view.findViewById(R.id.position);
            position.setText(section.position());

            BezelImageView avatar = (BezelImageView) view.findViewById(R.id.avatar);
            Picasso.with(this)
                .load(section.avatar())
                .placeholder(R.color.background_color)
                .into(avatar);

            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayoutParams.setMargins(0, index == 0 ? firstItemSpacer : itemSpacer, 0, 0);
            LinearLayout container = (LinearLayout) view.findViewById(R.id.reference_container);
            container.setLayoutParams(linearLayoutParams);

            linearLayout.addView(container);
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

        LayoutInflater inflater = LayoutInflater.from(this);

        final ArrayList<String> items = detail.items();
        for (int index = 0; index < items.size(); index++) {
            linearLayoutParams.setMargins(leftMargin, index == 0 ? firstItemSpacer : itemSpacer, rightMargin, 0);
            String item = items.get(index);
            TextView content = (TextView) inflater.inflate(R.layout.text_detail_section_body, linearLayout, false);
            content.setLayoutParams(linearLayoutParams);
            content.setText(item);
            content.setTextColor(getResources().getColor(R.color.white));
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
