package com.angelmsger.cslaboratory.shared;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmsger.cslaboratory.R;
import com.bumptech.glide.Glide;

public class ScrollingActivity extends AppCompatActivity {
    public static final String IMAGE_URI = "ImageURI";
    public static final String ARTICLE_TITLE = "Title";
    public static final String ARTICLE_CONTENT = "Content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "暂未添加的功能", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String imageURI = getIntent().getStringExtra(IMAGE_URI);
        String title = getIntent().getStringExtra(ARTICLE_TITLE);
        String content = getIntent().getStringExtra(ARTICLE_CONTENT);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView tvContent = (TextView) findViewById(R.id.content);

        if (imageURI != null && content != null) {
            Glide.with(this).load(imageURI).into(imageView);
            setTitle(title);
            tvContent.setText(content);
        }
    }
}
