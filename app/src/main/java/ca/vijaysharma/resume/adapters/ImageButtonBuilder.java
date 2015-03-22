package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.squareup.picasso.Picasso;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.utils.BezelImageView;
import ca.vijaysharma.resume.utils.Metrics;

class ImageButtonBuilder {
    private final Context context;

    private Drawable borderDrawable;
    private Drawable backgroundDrawable;
    private int connectorColor;
    private boolean addConnection;
    private @DrawableRes int image;
    private View.OnClickListener listener;

    public ImageButtonBuilder(Context context) {
        this.context = context;
        this.addConnection = false;
    }

    public ImageButtonBuilder setBorderDrawable(Drawable borderDrawable) {
        this.borderDrawable = borderDrawable;
        return this;
    }

    public ImageButtonBuilder setBackgroundDrawable(Drawable drawable) {
        this.backgroundDrawable = drawable;
        return this;
    }

    public ImageButtonBuilder setConnectorColor(int connectorColor) {
        this.connectorColor = connectorColor;
        return this;
    }

    public ImageButtonBuilder setAddConnection(boolean addConnection) {
        this.addConnection = addConnection;
        return this;
    }

    public ImageButtonBuilder setListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    public ImageButtonBuilder setImage(@DrawableRes int image) {
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

        BezelImageView button = new BezelImageView(context);
        int textWidth = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        int textHeight = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        button.setMaskDrawable(this.context.getResources().getDrawable(R.drawable.circle_mask));
        button.setLayoutParams(new LayoutParams(textWidth, textHeight, Gravity.CENTER));
        button.setBackground(this.backgroundDrawable);
        button.setBorderDrawable(this.borderDrawable);
        button.setOnClickListener(this.listener);
        button.setDesaturateOnPress(true);

        int avatarSize = (int)this.context.getResources().getDimension(R.dimen.circle_image_diameter);
        Picasso.with(context)
            .load(this.image)
            .placeholder(R.color.background_color)
            .centerCrop()
            .resize(avatarSize, avatarSize)
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
