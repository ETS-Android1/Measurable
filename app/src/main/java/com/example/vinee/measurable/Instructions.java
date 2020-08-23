package com.example.vinee.measurable;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Instructions extends AppCompatActivity {
TextView text;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        //Create font
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-Light.ttf");

        //Refer to XML views and set the font
        text = (TextView) findViewById(R.id.info);
        text.setTypeface(custom);
        layout = (RelativeLayout) findViewById(R.id.layout);
        ImageButton close = (ImageButton) findViewById(R.id.imgClose);

        //If user close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeApp(view);
            }
        });

        //If user clicks to proceed
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIntro(view);
            }
        });

    }

    //Code to go to respective destinations
    private void openIntro(View view){
        Intent intent = new Intent(this, Disclaimer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void closeApp(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
    }
}
