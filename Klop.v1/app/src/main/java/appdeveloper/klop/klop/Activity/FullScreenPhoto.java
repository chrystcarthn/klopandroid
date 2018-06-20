package appdeveloper.klop.klop.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import appdeveloper.klop.klop.R;
import appdeveloper.klop.klop.Other.TouchImageView;

public class FullScreenPhoto extends AppCompatActivity {

    Toolbar toolbar;
    String name, url;
    TouchImageView touchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        initView();
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        name = bundle.getString("name");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        touchImageView = (TouchImageView) findViewById(R.id.img_full);

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(touchImageView);
    }
}
