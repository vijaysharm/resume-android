package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.utils.Action1;
import ca.vijaysharma.resume.utils.Metrics;
import ca.vijaysharma.resume.utils.RoundedTransformation;

class ImageButtonBuilder<T> {
    private final Context context;

    private Drawable backgroundDrawable;
    private int connectorColor;
    private boolean addConnection;
    private Action1<T> listener;
    private T object;
    private @DrawableRes int image;

    public ImageButtonBuilder(Context context, T object) {
        this.context = context;
        this.addConnection = false;
        this.object = object;
    }

    public ImageButtonBuilder<T> setBackgroundDrawable(Drawable backgroundDrawableResourceId) {
        this.backgroundDrawable = backgroundDrawableResourceId;
        return this;
    }

    public ImageButtonBuilder<T> setConnectorColor(int connectorColor) {
        this.connectorColor = connectorColor;
        return this;
    }

    public ImageButtonBuilder<T> setAddConnection(boolean addConnection) {
        this.addConnection = addConnection;
        return this;
    }

    public ImageButtonBuilder<T> setListener(Action1<T> listener) {
        this.listener = listener;
        return this;
    }

    public ImageButtonBuilder<T> setImage(@DrawableRes int image) {
        this.image = image;
        return this;
    }

    public View build() {
        FrameLayout container = new FrameLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        int margin = computeContainerMargin();
        params.setMargins(margin, 0, margin, 0);
        container.setLayoutParams(params);

        View connection = new View(context);
        int connectionHeight = (int) context.getResources().getDimension(R.dimen.connector_height);
        int connectionWidth = computeConnectionWidth();
        int connectionMargin = computeConnectionMargin();
        params = new LayoutParams(connectionWidth, connectionHeight, Gravity.CENTER_VERTICAL);
        params.setMargins(connectionMargin, 0 , 0, 0);
        connection.setLayoutParams(params);
        connection.setBackgroundColor(connectorColor);

        ImageButton button = new ImageButton(context);
        int textWidth = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        int textHeight = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        button.setLayoutParams(new LayoutParams(textWidth, textHeight, Gravity.CENTER));
        button.setBackground(this.backgroundDrawable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null ) listener.call(object);
            }
        });

        int avatarSize = (int)this.context.getResources().getDimension(R.dimen.circle_image_diameter);
        Picasso.with(context)
            .load(this.image)
            .placeholder(R.color.background_color)
            .centerCrop()
            .resize(avatarSize, avatarSize)
            .transform(new RoundedTransformation())
            .into(button);

        container.addView(button);
        if (addConnection) container.addView(connection);

        return container;
    }

    private int computeConnectionWidth() {
        int screenWidthPx = Metrics.size(context).x;
        int halfScreenPx = screenWidthPx / 2;
        int circleRadiusPx = (int)context.getResources().getDimension(R.dimen.circle_item_diameter);
        int px = halfScreenPx - circleRadiusPx;

        return px;
    }

    private int computeConnectionMargin() {
        int circleRadiusPx = (int)context.getResources().getDimension(R.dimen.circle_item_diameter);
        int halfCircleRadiusPx = circleRadiusPx / 2;
        int px = halfCircleRadiusPx;

        return px;
    }

    private int computeContainerMargin() {
        int screenWidthPx = Metrics.size(context).x;
        int pageMarginPx = screenWidthPx / 4;

        return pageMarginPx;
    }
}
