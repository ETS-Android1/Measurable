package com.example.vinee.measurable;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChoiceActivity extends AppCompatActivity {
    //Instance Variables
    TextView heightView, distView, Instructions;
    LinearLayout distBox, heightBox;
    Animation anim1;
    Slide slide;
    ViewGroup layout;
    AlphaAnimation alphaAnimation;
    Slide slideDown;
    //Static so I can determine what the user chooses in other Activities
    public static boolean choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        //Custom Font
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-Light.ttf");

        //Find Views from XML
        heightView = (TextView) findViewById(R.id.height);
        distView = (TextView) findViewById(R.id.distance);
        Instructions = (TextView) findViewById(R.id.heightInstructions);
        heightBox = (LinearLayout) findViewById(R.id.heightBox);
        distBox = (LinearLayout) findViewById(R.id.distBox);

        //Instructions exist, but have no visibility
        Instructions.setVisibility(View.GONE);
        Instructions.setTypeface(custom);

        //Create animation resources
        anim1= AnimationUtils.loadAnimation(this, R.anim.slide_out_view);
        slide = new Slide();
        slideDown = new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        slideDown.setSlideEdge(Gravity.BOTTOM);

        layout = (ViewGroup) findViewById(R.id.layout);

        //When user clicks on the height option
        heightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Slide the distance box out
                TransitionManager.beginDelayedTransition(layout, slide);
                distBox.setVisibility(View.GONE);
                heightView.animate().translationY(-800);

//      //Fade instructions in
//                alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//                alphaAnimation.setDuration(1000);
//                Instructions.setText(getResources().getString(R.string.instructionsHeight));
//                Instructions.startAnimation(alphaAnimation);
//                Instructions.setVisibility(View.VISIBLE);

                //Set choice to true (User chose height)
                choice = true;

                //Move to Camera
                Intent i = new Intent(ChoiceActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //When user chooses distance
        distBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Slide height box out
                TransitionManager.beginDelayedTransition(layout, slide);
                heightBox.setVisibility(View.GONE);
                distView.animate().translationY(-800);

//                //Fade instructions in
//                alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//                alphaAnimation.setDuration(1000);
//                Instructions.setText(getResources().getString(R.string.instructionsDistance));
//                Instructions.startAnimation(alphaAnimation);
//                Instructions.setVisibility(View.VISIBLE);


                //User chose distance
                choice = false;

                //Move to Camera
                Intent i = new Intent(ChoiceActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        //When user chooses to proceed
        Instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to the instructions view
                Intent i = new Intent(ChoiceActivity.this, Disclaimer.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        //Set custom font onto text
        heightView.setTypeface(custom);
        distView.setTypeface(custom);

    }
}
