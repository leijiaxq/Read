package com.maymeng.read.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.maymeng.read.R;


public class ProgressXQ extends Dialog {


    static ProgressXQ instance;
    TextView tvMessage;
    ImageView ivProgressSpinner;
    AnimationDrawable adProgressSpinner;
    Context context;


    public static ProgressXQ getInstance(Context context) {
//        if (instance == null) {
//            synchronized (ProgressXQ.class) {
//                if (instance == null) {
//                    instance = new ProgressXQ(context);
//                }
//            }
//        }
        instance = new ProgressXQ(context);
        return instance;
    }

    private ProgressXQ(Context context) {
        super(context, R.style.DialogTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        this.context = context;
        View view = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        tvMessage = (TextView) view.findViewById(R.id.textview_message);
        ivProgressSpinner = (ImageView) view
                .findViewById(R.id.imageview_progress_spinner);

//        setSpinnerType();
        this.setContentView(view);
    }

    public void setSpinnerType() {


        ivProgressSpinner.setVisibility(View.VISIBLE);

//        ivProgressSpinner.setImageResource(R.drawable.round_spinner_fade);
//        adProgressSpinner = (AnimationDrawable) ivProgressSpinner.getDrawable();

        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.image_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        ivProgressSpinner.startAnimation(operatingAnim);

    }

    public void setMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ivProgressSpinner.clearAnimation();
    }
}
