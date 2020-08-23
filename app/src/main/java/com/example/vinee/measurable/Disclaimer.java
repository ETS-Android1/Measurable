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

public class Disclaimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        //Create custom font
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-Light.ttf");

        //Connect views from XML and set custom font
        TextView view = (TextView) findViewById(R.id.text);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        view.setTypeface(custom);


        //If user chooses to close out
        ImageButton close =(ImageButton) findViewById(R.id.imgClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIntro(view);
            }
        });

        //When user clicks to proceed
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Disclaimer.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    //Code to go back home
    private void openIntro(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
    }
}
