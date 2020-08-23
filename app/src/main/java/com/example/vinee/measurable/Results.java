package com.example.vinee.measurable;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.paperdb.Paper;

import static com.example.vinee.measurable.HomeActivity.forehead;

public class Results extends AppCompatActivity {
    int ft;
    int in;
    Double ang1, ang2, total;
    DecimalFormat df;
    TextView fin2, saveView;
    EditText nameView;
    ArrayList<Result> resultList;
    TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        df = new DecimalFormat("##.##");

        //Custom Fonts
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-Light.ttf");
        Typeface custom2 = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-SemiBold.ttf");


        //Instantiate views
        nameView = (EditText) findViewById(R.id.name);
        saveView = (TextView) findViewById(R.id.save);
        button = (TextView) findViewById(R.id.settings);

        //Get results from ArrayList in the home activity
        resultList = HomeActivity.resultList;

        //Set fonts
        nameView.setTypeface(custom);
        saveView.setTypeface(custom2);

        TextView fin = (TextView) findViewById(R.id.finale);
        fin2 = (TextView) findViewById(R.id.finale2);

        //Set custom font
        fin.setTypeface(custom);
        fin2.setTypeface(custom);
        ang1 = 0.0;
        ang2 = 0.0;

        //If user chose distance
        if (!ChoiceActivity.choice) {
            fin2.setText("Metric: " + df.format(getDistance() * 0.0254) + "m");
        }
        //If user chose height and both angle values are within the same 90 degree range
        else if (ChoiceActivity.choice && MainActivity.heightCalcs[1] > 0){
            fin2.setText("Metric: " + df.format(getSmallerHeight() * 0.0254) + "m");
        }
        //If user chose height and each angle value is on a different 90 degree range
        else {
            fin2.setText("Metric: " + df.format(getHeight() * 0.0254) + "m");
        }
        fin.setText("Imperial: " + ft + "\'" + in + "\"");

        //If user hits the 'x'
        ImageButton close = (ImageButton) findViewById(R.id.imgClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIntro();
            }
        });

        //If user hits save
        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize paper db
                Paper.init(getBaseContext());
                //If user didn't enter a name for the measurement
                if(nameView.getText().toString().equals("") || nameView.getText().toString().equals(" "))
                    resultList.add(0,new Result("Unknown", total, ChoiceActivity.choice));
                //If they did
                else
                    resultList.add(0,new Result(nameView.getText().toString(), total, ChoiceActivity.choice));

                //Write new value in db
                Paper.book().write("measure", resultList);
                HomeActivity.holder.setVisibility(View.GONE);

                //Go back home
                Intent i = new Intent(Results.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Results.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }

    public Double getDistance() {
        //Algorithm to get distance
        double finale = (HomeActivity.height - forehead) * Math.tan(Math.toRadians(MainActivity.angleNum));
        ft = (int) finale / 12;
        in = ((int) finale) % 12;
        total = finale;
        return Double.parseDouble(df.format(finale));
    }

    public Double getSmallerHeight(){
        //Algorithm to get height
        double distance = getDistanceHeight();
        //double height = (HomeActivity.height-forehead)/Math.tan(Math.toRadians(90 - MainActivity.heightCalcs[1]));
        //double height = (Math.tan(Math.toRadians(90 - MainActivity.heightCalcs[1])/distance));
        double height = (distance/Math.tan(Math.toRadians(90-MainActivity.heightCalcs[1])));
        double totalHeight = HomeActivity.height - height - forehead;

        ang1 = MainActivity.heightCalcs[0];
        ang2 = MainActivity.heightCalcs[1];

        //Toast.makeText(getBaseContext(), "Angles: "  + ang1 + ang2, Toast.LENGTH_LONG).show();

        total = totalHeight;
        ft = (int) totalHeight / 12;
        in = ((int) totalHeight) % 12;
        return Double.parseDouble(df.format(totalHeight));
    }

    public Double getHeight() {
        //Algorithm to get height if on separate planes
        double distance = getDistanceHeight();
        //double height = distance * Math.tan(Math.toRadians(Math.abs(MainActivity.heightCalcs[1])));
        double height = distance / Math.tan(Math.toRadians(90-Math.abs(MainActivity.heightCalcs[1])));
        double totalHeight = height + HomeActivity.height - forehead;

        total = totalHeight;

        ft = (int) totalHeight / 12;
        in = ((int) totalHeight) % 12;

        ang1 = MainActivity.heightCalcs[0];
        ang2 = MainActivity.heightCalcs[1];

        return Double.parseDouble(df.format(totalHeight));
    }
    //Method to return distance for height calculations
    public double getDistanceHeight(){
        return (HomeActivity.height - forehead) * Math.tan(Math.toRadians(MainActivity.heightCalcs[0]));
    }

    private void openIntro() {
        Intent intent = new Intent(Results.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            openIntro();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
