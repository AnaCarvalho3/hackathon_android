package com.example.rickandmortyapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppManager {

    enum Type {
        CHARACTER,
        EPISODE
    }

    ImageView CreateImageView(int size, Context context, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayout.addView(imageView, layoutParams);
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;

        viewGroup.addView(linearLayout);
        return imageView;
    }

    TextView CreateTextView(Context context, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(30.0f);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayout.addView(textView, layoutParams);
        viewGroup.addView(linearLayout);
        return textView;
    }
}
