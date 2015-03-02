package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import ca.vijaysharma.resume.R;
import ca.vijaysharma.resume.utils.Action1;
import ca.vijaysharma.resume.utils.Metrics;
import ca.vijaysharma.resume.utils.Typefaces;

class TextButtonBuilder<T> {
    private final Context context;

    private String text;
    private Drawable backgroundDrawableResourceId;
    private int connectorColor;
    private boolean addConnection;
    private Action1<T> listener;
    private T object;

    public TextButtonBuilder(Context context, T object) {
        this.context = context;
        this.addConnection = false;
        this.object = object;
    }

    public TextButtonBuilder setBackgroundDrawable(Drawable backgroundDrawableResourceId) {
        this.backgroundDrawableResourceId = backgroundDrawableResourceId;
        return this;
    }

    public TextButtonBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextButtonBuilder setConnectorColor(int connectorColor) {
        this.connectorColor = connectorColor;
        return this;
    }

    public TextButtonBuilder setAddConnection(boolean addConnection) {
        this.addConnection = addConnection;
        return this;
    }

    public TextButtonBuilder setListener(Action1<T> listener) {
        this.listener = listener;
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

        Button button = new Button(context);
        int textWidth = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        int textHeight = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        button.setLayoutParams(new LayoutParams(textWidth, textHeight, Gravity.CENTER));
        button.setGravity(Gravity.CENTER);
        button.setText(this.text);
        button.setTextColor(this.context.getResources().getColor(R.color.white));
        button.setBackground(this.backgroundDrawableResourceId);
        float textSize = this.context.getResources().getDimension(R.dimen.circle_item_text_size) / this.context.getResources().getDisplayMetrics().density;
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        button.setTypeface(Typefaces.get(this.context.getString(R.string.thin)));
        button.setTransformationMethod(null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null ) listener.call(object);
            }
        });

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
