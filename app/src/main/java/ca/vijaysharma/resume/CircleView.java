package ca.vijaysharma.resume;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private Paint paint;
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(android.R.color.white));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
    }
}
