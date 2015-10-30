/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Mauricio on 28/10/15.
 */
public class SummaryActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private TextView summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        imageView = (ImageView)findViewById(R.id.image_post);
        title = (TextView)findViewById(R.id.texto_titulo);
        summary = (TextView)findViewById(R.id.summary_item);

        final Animation animationTranslate = AnimationUtils.loadAnimation(this, R.anim.translate_down_in);
        final Animation animationTitle = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        summary.postDelayed(new Runnable() {
            @Override
            public void run() {
                title.startAnimation(animationTitle);
                title.setVisibility(View.VISIBLE);
                summary.setVisibility(View.VISIBLE);
                summary.startAnimation(animationTranslate);
            }
        }, getResources().getInteger(android.R.integer.config_mediumAnimTime));

        Bundle bundle = getIntent().getExtras();

        title.setText(bundle.getString("title"));
        Picasso.with(this)
                .load(bundle.getString("image"))
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        summary.setText(bundle.getString("summary"));
    }
}
