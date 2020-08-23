package com.example.vinee.measurable;

import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Create initial variables
    private Sensor mySensor;
    private SensorManager SM;
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private Double formatted;
    Double ang;
    boolean firstNum;
    TextView angle;
    public static Double[] heightCalcs;
    public static Double angleNum;
    ImageView rotate;
    AlphaAnimation alphaAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove the title and action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Refer to images from the home screen
        ImageButton close = (ImageButton) findViewById(R.id.imgClose);
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/WorkSans-Light.ttf");
        rotate = (ImageView) findViewById(R.id.rotate);
        ImageButton info = (ImageButton) findViewById(R.id.info);



        firstNum = false;
        close = (ImageButton) findViewById(R.id.imgClose);
        //Get accelerometer values
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_GRAVITY);
        SM.registerListener(this, mySensor, 1000000);

        //New array with 2 values if calculating height
        heightCalcs = new Double[2];
        try {
            //Start the camera
            mCamera = getCameraInstance();
            Camera.Parameters params = mCamera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(params);
        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            //Create a SurfaceView to show camera data
            mCameraView = new CameraView(this, mCamera);
            FrameLayout camera_view = (FrameLayout) findViewById(R.id.camera_view);
            //Add the SurfaceView to the layout
            camera_view.addView(mCameraView);
        }
        angle = (TextView) findViewById(R.id.angle);
        angle.setTypeface(custom);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code to go back home
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code to view tips
                Intent i = new Intent(MainActivity.this, TipsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });

        //When user clicks shutter
        ImageButton shutter = (ImageButton) findViewById(R.id.shutter);
        shutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user chose height and this is the first time they hit shutter
                if (ChoiceActivity.choice && !firstNum) {
                    heightCalcs[0] = 90 - Math.toDegrees(ang);
                    firstNum = true;
                }
                //If user chose height and this is the second time they hit shutter
                else if (ChoiceActivity.choice && firstNum) {
                    heightCalcs[1]= Math.toDegrees(ang);
                    goToResults();
                }
                //If user chose distance
                else {
                    angleNum = 90 - Math.toDegrees(ang);
                    goToResults();
                }
            }
        });
    }
    public void goToResults(){
        Intent i = new Intent(MainActivity.this, Results.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            // Attempt to get a Camera instance
            c = Camera.open();
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        // Returns null if camera is unavailable
        return c;
    }

    @Override
    //Get sensor data
    public void onSensorChanged(SensorEvent event) {
        //Set gravity value
        double g = 9.81;
        //Algorithm to calculate angle from acceleration values
        formatted = event.values[2] / g;
        ang = Math.asin(formatted);
        //Peek distance values
        /**
         * This block was meant for development purposes
        Set TextView to instantaneous distance
        if(!ChoiceActivity.choice) {
            if (Math.toDegrees(ang) > 0) {
                angle.setVisibility(View.VISIBLE);
                angle.setText(getDistance(ang));
            }
            else
                angle.setText("Tilt phone down");
        }
    **/
        //Image to tell user to put their phone in landscape mode
        if(event.values[1] > 4){
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            rotate.startAnimation(alphaAnimation);
            rotate.setVisibility(View.VISIBLE);
        }
        else if(event.values[1] < 4){
            rotate.setVisibility(View.GONE);
        }

    }

    //Necessary function implemented
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Method meant for development

    public String getDistance(Double angle) {
        DecimalFormat df = new DecimalFormat("##.###");
        //Array
        int[] heightValues = new int[2];
        //Algorithm to get distance
        double finale = (HomeActivity.height - HomeActivity.forehead) * Math.tan(Double.parseDouble(df.format(Math.toRadians((90-Math.toDegrees(angle))))));
        heightValues[0] = (int) finale / 12;
        heightValues[1] = ((int) finale) % 12;

        df = new DecimalFormat("##.#");
        //Return formatted String
        return heightValues[0] + "\'" + heightValues[1] + "\"      " + df.format(Math.toDegrees(angle));
    }
    **/


}
