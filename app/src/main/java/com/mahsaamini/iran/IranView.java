package com.mahsaamini.iran;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class IranView extends View {

    private static final int BACKGROUND_COLOR = Color.BLACK;
    private static final int STROKE_COLOR = Color.WHITE;
    private static final float STROKE_SIZE = 4f; //dp
    private static final long DURATION = 3000;

    private final Paint paint = new Paint();

    private final Path subPath = new Path();
    private final PathMeasure measure;
    private final int size;

    private final Bitmap bmp;

    public IranView(Context context) {
        this(context, null);
    }

    public IranView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IranView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        paint.setColor(STROKE_COLOR);
        paint.setStrokeWidth(context.getResources().getDisplayMetrics().density * STROKE_SIZE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(paint.getStrokeWidth() / 2f));

        float min = size = Math.min(h, w);
        float max = Math.max(h, w);
        boolean isHorizontal = w > h;
        float offset = (max - min) / 2f;

        Path path = createPath();
        Matrix matrix = new Matrix();
        matrix.postScale(min, min);
        matrix.postTranslate(isHorizontal ? offset : 0f, isHorizontal ? 0f : offset);
        path.transform(matrix);

        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(BACKGROUND_COLOR);

        Paint paint2 = new Paint();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPath(path, paint2);

        measure = new PathMeasure(path, false);

        if (isInEditMode())
            subPath.set(path);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bmp, 0, 0, paint);
        canvas.drawPath(subPath, paint);
    }

    public void start(Listener listener) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(DURATION);
        animator.addUpdateListener(a -> {
            measure.getSegment(0f, a.getAnimatedFraction() * measure.getLength(), subPath, true);
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onReady();
            }
        });
        animator.start();
    }

    public int getSize() {
        return size;
    }

    public interface Listener {
        void onReady();
    }

    private static Path createPath() {
        float[][] positions = {
                {0.500f, 0.230f}, {0.501f, 0.230f}, {0.501f, 0.240f}, {0.508f, 0.260f},
                {0.492f, 0.260f}, {0.484f, 0.255f}, {0.474f, 0.258f}, {0.468f, 0.258f},
                {0.460f, 0.264f}, {0.450f, 0.266f}, {0.450f, 0.266f}, {0.440f, 0.268f},
                {0.420f, 0.271f}, {0.400f, 0.271f}, {0.380f, 0.268f}, {0.370f, 0.266f},
                {0.365f, 0.260f}, {0.360f, 0.255f}, {0.350f, 0.250f}, {0.345f, 0.248f},
                {0.340f, 0.244f}, {0.330f, 0.235f}, {0.330f, 0.228f}, {0.320f, 0.222f},
                {0.310f, 0.220f}, {0.294f, 0.219f}, {0.278f, 0.210f}, {0.274f, 0.200f},
                {0.272f, 0.190f}, {0.270f, 0.180f}, {0.268f, 0.165f}, {0.258f, 0.162f},
                {0.248f, 0.156f}, {0.242f, 0.150f}, {0.232f, 0.140f}, {0.229f, 0.140f},
                {0.229f, 0.130f}, {0.242f, 0.124f}, {0.234f, 0.110f}, {0.243f, 0.100f},
                {0.226f, 0.084f}, {0.186f, 0.110f}, {0.178f, 0.112f}, {0.174f, 0.118f},
                {0.170f, 0.124f}, {0.130f, 0.127f}, {0.110f, 0.110f}, {0.100f, 0.105f},
                {0.095f, 0.090f}, {0.090f, 0.080f}, {0.084f, 0.074f}, {0.075f, 0.070f},
                {0.065f, 0.090f}, {0.050f, 0.088f}, {0.055f, 0.098f}, {0.052f, 0.110f},
                {0.058f, 0.120f}, {0.056f, 0.142f}, {0.060f, 0.150f}, {0.055f, 0.164f},
                {0.050f, 0.170f}, {0.064f, 0.184f}, {0.064f, 0.200f}, {0.068f, 0.210f},
                {0.072f, 0.224f}, {0.074f, 0.234f}, {0.073f, 0.244f}, {0.080f, 0.246f},
                {0.080f, 0.260f}, {0.090f, 0.268f}, {0.093f, 0.290f}, {0.104f, 0.292f},
                {0.114f, 0.302f}, {0.140f, 0.308f}, {0.140f, 0.312f}, {0.130f, 0.312f},
                {0.124f, 0.320f}, {0.130f, 0.340f}, {0.120f, 0.349f}, {0.110f, 0.360f},
                {0.110f, 0.375f}, {0.100f, 0.373f}, {0.092f, 0.383f}, {0.103f, 0.404f},
                {0.090f, 0.410f}, {0.120f, 0.450f}, {0.125f, 0.460f}, {0.120f, 0.468f},
                {0.144f, 0.480f}, {0.166f, 0.508f}, {0.180f, 0.508f}, {0.186f, 0.528f},
                {0.194f, 0.548f}, {0.194f, 0.590f}, {0.208f, 0.592f}, {0.210f, 0.610f},
                {0.210f, 0.630f}, {0.218f, 0.635f}, {0.222f, 0.650f}, {0.242f, 0.651f},
                {0.248f, 0.641f}, {0.248f, 0.635f}, {0.278f, 0.642f}, {0.280f, 0.650f},
                {0.300f, 0.645f}, {0.308f, 0.646f}, {0.330f, 0.690f}, {0.334f, 0.700f},
                {0.344f, 0.700f}, {0.348f, 0.710f}, {0.346f, 0.718f}, {0.360f, 0.728f},
                {0.370f, 0.768f}, {0.380f, 0.778f}, {0.404f, 0.779f}, {0.415f, 0.790f},
                {0.430f, 0.794f}, {0.438f, 0.804f}, {0.448f, 0.820f}, {0.460f, 0.824f},
                {0.480f, 0.844f}, {0.520f, 0.843f}, {0.540f, 0.863f}, {0.570f, 0.850f},
                {0.582f, 0.840f}, {0.600f, 0.826f}, {0.630f, 0.820f}, {0.645f, 0.824f},
                {0.660f, 0.854f}, {0.660f, 0.864f}, {0.664f, 0.870f}, {0.664f, 0.880f},
                {0.678f, 0.900f}, {0.700f, 0.900f}, {0.710f, 0.910f}, {0.730f, 0.905f},
                {0.755f, 0.908f}, {0.760f, 0.915f}, {0.840f, 0.914f}, {0.845f, 0.920f},
                {0.880f, 0.930f}, {0.895f, 0.925f}, {0.895f, 0.900f}, {0.905f, 0.890f},
                {0.901f, 0.880f}, {0.902f, 0.870f}, {0.922f, 0.854f}, {0.927f, 0.845f},
                {0.970f, 0.838f}, {0.970f, 0.818f}, {0.972f, 0.802f}, {0.945f, 0.804f},
                {0.950f, 0.794f}, {0.948f, 0.774f}, {0.946f, 0.764f}, {0.946f, 0.750f},
                {0.930f, 0.750f}, {0.906f, 0.735f}, {0.876f, 0.705f}, {0.870f, 0.690f},
                {0.845f, 0.650f}, {0.865f, 0.630f}, {0.870f, 0.620f}, {0.880f, 0.610f},
                {0.888f, 0.596f}, {0.879f, 0.576f}, {0.859f, 0.570f}, {0.842f, 0.566f},
                {0.836f, 0.536f}, {0.834f, 0.506f}, {0.828f, 0.486f}, {0.824f, 0.480f},
                {0.820f, 0.476f}, {0.835f, 0.446f}, {0.820f, 0.444f}, {0.821f, 0.404f},
                {0.828f, 0.402f}, {0.822f, 0.390f}, {0.835f, 0.380f}, {0.838f, 0.350f},
                {0.840f, 0.330f}, {0.842f, 0.320f}, {0.838f, 0.305f}, {0.841f, 0.294f},
                {0.836f, 0.280f}, {0.840f, 0.264f}, {0.818f, 0.264f}, {0.804f, 0.264f},
                {0.787f, 0.244f}, {0.777f, 0.236f}, {0.757f, 0.230f}, {0.754f, 0.214f},
                {0.732f, 0.210f}, {0.722f, 0.206f}, {0.702f, 0.216f}, {0.690f, 0.200f},
                {0.660f, 0.190f}, {0.650f, 0.178f}, {0.614f, 0.174f}, {0.610f, 0.180f},
                {0.590f, 0.182f}, {0.566f, 0.182f}, {0.550f, 0.200f}, {0.546f, 0.204f},
                {0.544f, 0.216f}, {0.536f, 0.218f}, {0.520f, 0.220f}, {0.500f, 0.230f},
        };

        Path path = new Path();
        path.moveTo(positions[0][0], positions[0][1]);
        for (float[] p : positions)
            path.lineTo(p[0], p[1]);

        return path;
    }
}
