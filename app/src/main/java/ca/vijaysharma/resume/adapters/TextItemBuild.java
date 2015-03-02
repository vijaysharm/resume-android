package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.utils.Metrics;
import ca.vijaysharma.resume.utils.Typefaces;

class TextItemBuild {
    private final Context context;

    private String text;
    private Drawable backgroundDrawableResourceId;
    private int connectorColor;
    private boolean addConnection;

    public TextItemBuild(Context context) {
        this.context = context;
        this. addConnection = false;
    }

    public TextItemBuild setBackgroundDrawable(Drawable backgroundDrawableResourceId) {
        this.backgroundDrawableResourceId = backgroundDrawableResourceId;
        return this;
    }

    public TextItemBuild setText(String text) {
        this.text = text;
        return this;
    }

    public TextItemBuild setConnectorColor(int connectorColor) {
        this.connectorColor = connectorColor;
        return this;
    }

    public TextItemBuild setAddConnection(boolean addConnection) {
        this.addConnection = addConnection;
        return this;
    }

    public View build() {
        FrameLayout container = new FrameLayout(context);
        LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

        TextView text = new TextView(context);
        int textWidth = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        int textHeight = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        text.setLayoutParams(new LayoutParams(textWidth, textHeight, Gravity.CENTER));
        text.setGravity(Gravity.CENTER);
        text.setText(this.text);
        text.setTextColor(this.context.getResources().getColor(R.color.white));
        text.setBackground(this.backgroundDrawableResourceId);
        float textSize = this.context.getResources().getDimension(R.dimen.circle_item_text_size) / this.context.getResources().getDisplayMetrics().density;
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        text.setTypeface(Typefaces.get(this.context.getString(R.string.thin)));

        container.addView(text);
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
