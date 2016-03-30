package com.amtechventures.tucita.utils.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.amtechventures.tucita.R;

public class TuCitaProgressDialog extends ProgressDialog {

    private AnimationDrawable animation;

    public TuCitaProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.progress_dialog);

        ImageView imageView = (ImageView) findViewById(R.id.animation);

        imageView.setBackgroundResource(R.drawable.progress_bar_states);

        animation = (AnimationDrawable) imageView.getBackground();

    }

    @Override
    public void show() {

       super.show();

       animation.start();

    }

    @Override
    public void dismiss() {

       super.dismiss();

       animation.stop();

    }

}