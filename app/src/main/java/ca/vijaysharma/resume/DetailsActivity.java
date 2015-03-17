package ca.vijaysharma.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
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

    private final SpringSystem springSystem = SpringSystem.create();
    private Spring heroSpring;
    private Spring action1Spring;
    private Spring action2Spring;
    private SimpleSpringListener heroSpringListener;
    private SimpleSpringListener action1SpringListener;
    private SimpleSpringListener action2SpringListener;

    @InjectView(R.id.container) ObservableScrollView scrollView;
    @InjectView(R.id.background) View background;
    @InjectView(R.id.hero_image) BezelImageView hero;
    @InjectView(R.id.description_container) LinearLayout descriptionContainer;
    @InjectView(R.id.body) LinearLayout body;
    @InjectView(R.id.description_1) TextView title1;
    @InjectView(R.id.description_2) TextView title2;
    @InjectView(R.id.description_3) TextView title3;
    @InjectView(R.id.action_1) ImageButton action1;
    @InjectView(R.id.action_2) ImageButton action2;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);
        heroSpring = springSystem.createSpring();
        action1Spring = springSystem.createSpring();
        action2Spring = springSystem.createSpring();
        heroSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(40, 3));
        action1Spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(40, 3));
        action2Spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(40, 3));
        heroSpringListener = new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0);
                hero.setScaleX(mappedValue);
                hero.setScaleY(mappedValue);
            }
        };
        action1SpringListener = new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                // TODO: the hit area still exists
                float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0);
                action1.setScaleX(mappedValue);
                action1.setScaleY(mappedValue);
            }
        };
        action2SpringListener = new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                // TODO: the hit area still exists
                float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0);
                action2.setScaleX(mappedValue);
                action2.setScaleY(mappedValue);
            }
        };

        ArrayList<DetailParcel> details = getIntent().getParcelableArrayListExtra(PARCELABLE_DATA_KEY);
        final DetailParcel detail = details.get(0);

        int toolbarHeight = Metrics.toolbarHeight(this);
        int marginFromEdge = (int)getResources().getDimension(R.dimen.margin_from_edge);
        int statusBarHeight = Metrics.statusBarHeight(this);
        final Point windowSize = Metrics.size(this);
        applyInsets(scrollView, statusBarHeight);

        toolbar.setBackgroundColor(getResources().getColor(detail.primaryColor()));
        setActionBar(toolbar);
        getActionBar().setTitle(detail.detail1());

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
        hero.setScaleX(0);
        hero.setScaleY(0);
        Picasso.with(this)
            .load(detail.hero())
            .placeholder(R.color.background_color)
            .centerCrop()
            .resize(heroImageDiameter, heroImageDiameter)
            .into(hero, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    hero.animate()
                            .setStartDelay(100)
                            .setDuration(700)
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setInterpolator(new OvershootInterpolator(3.0f))
                            .start();
                }
            });

        frameLayoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        frameLayoutParams.setMargins(0, toolbarHeight, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;
        descriptionContainer.setLayoutParams(frameLayoutParams);

        title1.setText(detail.detail1());
        title1.setTextColor(getResources().getColor(detail.secondaryColor()));
        title1.setTranslationX(windowSize.x);
        title1.animate()
            .setStartDelay(200)
            .setDuration(500)
            .translationX(0)
            .start();

        title2.setText(TextUtils.isEmpty(detail.detail2()) ? "" : detail.detail2());
        title3.setVisibility(TextUtils.isEmpty(detail.detail2()) ? View.INVISIBLE : View.VISIBLE);
        title2.setTextColor(getResources().getColor(detail.secondaryColor()));
        title2.setTranslationX(windowSize.x);
        title2.animate()
            .setStartDelay(300)
            .setDuration(500)
            .translationX(0)
            .start();

        title3.setText(TextUtils.isEmpty(detail.detail3()) ? "" : detail.detail3());
        title3.setVisibility(TextUtils.isEmpty(detail.detail3()) ? View.INVISIBLE : View.VISIBLE);
        title3.setTextColor(getResources().getColor(detail.tertiaryColor()));
        title3.setTranslationX(windowSize.x);
        title3.animate()
            .setStartDelay(250)
            .setDuration(500)
            .translationX(0)
            .start();

        int backgroundHeight = (int)getResources().getDimension(R.dimen.background_view_height);
        int actionItemDiameter = (int)getResources().getDimension(R.dimen.action_item_diameter);
        int actionItemRadius = actionItemDiameter / 2;
        int actionButtonTopMargin = backgroundHeight - actionItemRadius;

        int spaceBetweenActionItems = (int)getResources().getDimension(R.dimen.space_between_action_items);
        int action2Position = marginFromEdge + actionItemDiameter + spaceBetweenActionItems;
        frameLayoutParams = new FrameLayout.LayoutParams(actionItemDiameter, actionItemDiameter);
        frameLayoutParams.setMargins(0, actionButtonTopMargin, action2Position, 0);
        frameLayoutParams.gravity = Gravity.END;

        action1.setScaleX(0);
        action1.setScaleY(0);
        action1.setBackground(Drawables.rippleDrawable(this, detail.primaryColor()));
        action1.setLayoutParams(frameLayoutParams);
        Picasso.with(this)
            .load(detail.action1().action())
            .placeholder(R.color.background_color)
            .into(action1, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    action1.animate()
                            .setStartDelay(200)
                            .setDuration(700)
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setInterpolator(new OvershootInterpolator(3.0f))
                            .start();
                    action1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(detail.action1().intent());
                        }
                    });
                }
            });

        frameLayoutParams = new FrameLayout.LayoutParams(actionItemDiameter, actionItemDiameter);
        frameLayoutParams.setMargins(0, actionButtonTopMargin, marginFromEdge, 0);
        frameLayoutParams.gravity = Gravity.END;

        action2.setScaleX(0);
        action2.setScaleY(0);
        action2.setBackground(Drawables.rippleDrawable(this, detail.primaryColor()));
        action2.setLayoutParams(frameLayoutParams);
        Picasso.with(this)
            .load(detail.action2().action())
            .placeholder(R.color.background_color)
                .into(action2, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        action2.animate()
                                .setStartDelay(300)
                                .setDuration(700)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setInterpolator(new OvershootInterpolator(3.0f))
                            .start();
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

    @Override
    protected void onResume() {
        super.onResume();
        heroSpring.addListener(heroSpringListener);
        action1Spring.addListener(action1SpringListener);
        action2Spring.addListener(action2SpringListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        heroSpring.removeListener(heroSpringListener);
        action1Spring.removeListener(action1SpringListener);
        action2Spring.removeListener(action2SpringListener);
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
            name.setTranslationY(-10.0f);
            name.setAlpha(0);
            name.animate()
                    .setStartDelay(400)
                .setDuration(500)
                    .translationY(0)
                .alpha(1)
                .start();

            TextView position = (TextView) view.findViewById(R.id.position);
            position.setText(section.position());
            position.setTranslationY(-10.0f);
            position.setAlpha(0);
            position.animate()
                .setStartDelay(400)
                .setDuration(500)
                .translationY(0)
                .alpha(1)
                .start();

            final BezelImageView avatar = (BezelImageView) view.findViewById(R.id.avatar);
            avatar.setScaleX(0);
            avatar.setScaleY(0);
            Picasso.with(this)
                .load(section.avatar())
                .placeholder(R.color.background_color)
                .into(avatar, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        avatar.animate()
                            .setStartDelay(100)
                            .setDuration(700)
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setInterpolator(new OvershootInterpolator(1.5f))
                            .start();
                    }
                });

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
            content.setTranslationY(-10.0f);
            content.setAlpha(0);
            content.animate()
                .setStartDelay(400)
                .setDuration(500)
                .translationY(0)
                .alpha(1)
                .start();

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
        heroSpring.setEndValue(view.getScrollY() > 10 ? 1 : 0);
        action1Spring.setEndValue(view.getScrollY() > 15 ? 1 : 0);
        action2Spring.setEndValue(view.getScrollY() > 20 ? 1 : 0);

        int maxHeight = Metrics.toolbarHeight(this);
        int backgroundHeight = (int)getResources().getDimension(R.dimen.background_view_height);
        int difference = backgroundHeight - maxHeight;
        toolbar.setVisibility(view.getScrollY() > difference ? View.VISIBLE : View.INVISIBLE);
    }
}
