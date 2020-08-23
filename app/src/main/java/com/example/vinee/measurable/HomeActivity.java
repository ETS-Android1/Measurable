package com.example.vinee.measurable;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    //Create instance variables
    private FloatingActionButton button;
    private RelativeLayout layout;
    private RelativeLayout menuLayout;
    boolean isOpen = false;
    private EditText heightView;
    private TextView start;
    public static TextView holder;
    public static double height;
    ListView listView;
    ListAdapter adapter;
    public static ArrayList<Result> resultList;
    PermissionManager permissionManager;
    static double forehead;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Request camera permission
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Find views from XML
        button = (FloatingActionButton) findViewById(R.id.menu);
        layout = (RelativeLayout) findViewById(R.id.layout);
        menuLayout = (RelativeLayout) findViewById(R.id.menu2);
        start = (TextView) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMenu();
            }
        });
        heightView = (EditText) findViewById(R.id.height);
        holder = (TextView) findViewById(R.id.placeholder);
        listView = (ListView) findViewById(R.id.listView);

        //Get average forehead size from db
        Paper.init(getBaseContext());

        forehead = Paper.book().read("forehead", 4.0);

        //Instantiate PermissionManager
        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        //Create paper db, so values are saved when app is closed
        Paper.init(getBaseContext());

        //Get values and put it on the homescreen
        resultList = Paper.book().read("measure", new ArrayList<Result>());
        adapter = new ListAdapter(this, R.layout.history_layout2, resultList);
        listView.setAdapter(adapter);

        //When user clicks start
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code only runs of textbox has a value
                if(!heightView.getText().toString().equals("")) {
                    openIntro(view);
                    height = Double.parseDouble(heightView.getText().toString());
                }
            }
        });

        //Create and set fonts
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-Light.ttf");
        Typeface custom2 = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-SemiBold.ttf");
        start.setTypeface(custom2);
        heightView.setTypeface(custom);
        holder.setTypeface(custom);

        //Created by Vineet Sridhar on 3/19/2018.
    }
    //Go to the next activity
    private void openIntro(View view){
        Intent intent = new Intent(this, ChoiceActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    private void viewMenu() {
        if (!isOpen) {

            //Open the menu
            //Get animation parameters
            int x = layout.getRight();
            int y = layout.getBottom();
            int startRadius = 0;
            int endRadius = (int) Math.hypot(layout.getWidth(), layout.getHeight());

            //Create animation and change values of the Floating Action Button
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
            button.setImageResource(R.drawable.ic_close_black_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(menuLayout, x, y, startRadius, endRadius);
            menuLayout.setVisibility(View.VISIBLE);
            anim.start();
            //Set isOpen as true so that I know if the menu is open or not.
            isOpen = true;
        } else {
            //If menu is already open, and I need to get back to homescreen
            //Animation Parameters
            int x = layout.getRight();
            int y = layout.getBottom();
            int startRadius = (int) Math.hypot(layout.getWidth(), layout.getHeight());
            int endRadius = 0;

            //Create animation and change values of the Floating Action Button
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            button.setImageResource(R.drawable.ic_add_white_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(menuLayout, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //listView.setVisibility(View.VISIBLE);
                    menuLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            //Start animation, and change isOpen to false if user clicks button again
            anim.start();
            isOpen = false;
        }
    }
}
