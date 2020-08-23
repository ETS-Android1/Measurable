package com.example.vinee.measurable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TipsActivity extends AppCompatActivity {
    AlphaAnimation alphaAnimation;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        //Connect Views
        final TextView tip1 = (TextView) findViewById(R.id.tip1);
        final TextView tip2 = (TextView) findViewById(R.id.tip2);
        final TextView tip3 = (TextView) findViewById(R.id.tip3);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);

        //Initialize the counter to 0
        counter = 0;
        //Created by Vineet Sridhar on 3/19/2018.

        tip1.setVisibility(View.VISIBLE);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (counter){
                    case 0:
                        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                        alphaAnimation.setDuration(1000);
                        tip1.setAnimation(alphaAnimation);
                        tip1.setVisibility(View.GONE);

                        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(1000);
                        tip2.setAnimation(alphaAnimation);
                        tip2.setVisibility(View.VISIBLE);
                        counter ++;
                        break;
                    case 1:
                        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                        alphaAnimation.setDuration(1000);
                        tip2.setAnimation(alphaAnimation);
                        tip2.setVisibility(View.GONE);

                        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(1000);
                        tip3.setAnimation(alphaAnimation);
                        tip3.setVisibility(View.VISIBLE);
                        counter ++;
                        break;
                    case 2:
                        backToCamera();
                        break;
                }

            }
        });
    }


    private void backToCamera(){
        Intent i = new Intent(TipsActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            backToCamera();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
