//Template from http://www.gadgetsaint.com/android/create-pedometer-step-counter-android/
//Changes made by Chris Choi and Haine Cho

package com.example.pocketpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.SensorEvent;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.drawable.Drawable;


public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView textView;
    TextView TvSteps;
    Button BtnStart;
    Button BtnStop;
    private ImageView myAnimation;
    private ImageView myAnimation2;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        myAnimation = (ImageView)findViewById(R.id.baymax1);
        myAnimation2 = (ImageView)findViewById(R.id.baymax2);
        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);

        BtnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                numSteps = 0;
                myAnimation.setVisibility(ImageView.VISIBLE);
                //myAnimation2.setVisibility(View.INVISIBLE);
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });
        BtnStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sensorManager.unregisterListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        System.out.println(R.drawable.uni);
        if ((numSteps%2) == 0) {
            myAnimation.setVisibility(ImageView.INVISIBLE);
            myAnimation2.setVisibility(View.VISIBLE);
        } else {
            myAnimation.setVisibility(ImageView.VISIBLE);
            myAnimation2.setVisibility(View.INVISIBLE);
        }
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

}
