package ca.vijaysharma.resume.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import ca.vijaysharma.resume.R;

class TextItemBuild {
    private final Context context;

    private String text;
    private int backgroundDrawableResourceId;


    public TextItemBuild(Context context) {
        this.context = context;
    }

    public TextItemBuild setBackgroundDrawableResourceId(int backgroundDrawableResourceId) {
        this.backgroundDrawableResourceId = backgroundDrawableResourceId;
        return this;
    }

    public TextItemBuild setText(String text) {
        this.text = text;
        return this;
    }

    public View build() {
        FrameLayout container = new FrameLayout(context);
        LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        int leftMargin = (int) context.getResources().getDimension(R.dimen.row_item_margins_half);
        int rightMargin = (int) context.getResources().getDimension(R.dimen.row_item_margins_half);
        params.setMargins(leftMargin, 0, rightMargin, 0);
        container.setLayoutParams(params);

        TextView text = new TextView(context);
        int textWidth = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        int textHeight = (int) context.getResources().getDimension(R.dimen.circle_item_diameter);
        text.setLayoutParams(new LayoutParams(textWidth, textHeight, Gravity.CENTER));
        text.setGravity(Gravity.CENTER);
        text.setText(this.text);
        text.setTextColor(this.context.getResources().getColor(R.color.white));
        text.setBackgroundResource(this.backgroundDrawableResourceId);

        container.addView(text);

        return container;
    }
}
