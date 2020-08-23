package com.example.vinee.measurable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;
    double progress, shownValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.value);
        Paper.init(getBaseContext());

        Double value = (Paper.book().read("forehead", 4.0));
        value*=10.0;
        seekBar.setProgress(value.intValue());

        shownValue = seekBar.getProgress()/10.0;

        textView.setText(String.format("%s in.", shownValue));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                shownValue = seekBar.getProgress()/10.0;
                textView.setText(String.format("%s in.", shownValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                shownValue = seekBar.getProgress()/10.0;
                textView.setText(String.format("%s in.", shownValue));
                Paper.init(getBaseContext());
                Paper.book().write("forehead", shownValue);
            }
        });

    }
}
