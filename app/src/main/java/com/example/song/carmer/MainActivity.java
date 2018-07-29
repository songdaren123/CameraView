package com.example.song.carmer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.song.carmer.ui.widget.PictureButton;

/**
 * songmingzhan
 */

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  initView();


    }
    private void initView(){
        mFrameLayout=findViewById(R.id.frame_layout);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        PictureButton button=new PictureButton(this);
        button.setLayoutParams(layoutParams);
        mFrameLayout.addView(button);

    }
}
