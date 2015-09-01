package com.eveningoutpost.dexdrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eveningoutpost.dexdrip.UtilityModels.CollectionServiceStarter;
import com.eveningoutpost.dexdrip.utils.ActivityWithMenu;

import java.util.Calendar;
import java.util.List;


public class StartNewSensor extends ActivityWithMenu {
    public static String menu_name = "Start Sensor";
    private Button button;
    private DatePicker dp;
    private TimePicker tp;
    private RadioGroup radioGroup;
    private EditText sensor_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Sensor.isActive() == false) {
            setContentView(R.layout.activity_start_new_sensor);
            button = (Button)findViewById(R.id.startNewSensor);
            dp = (DatePicker)findViewById(R.id.datePicker);
            tp = (TimePicker)findViewById(R.id.timePicker);
            sensor_location = (EditText) findViewById(R.id.edit_sensor_location);
            sensor_location.setEnabled(false);
            addListenerOnButton();
        } else {
            Intent intent = new Intent(this, StopSensor.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public String getMenuName() {
        return menu_name;
    }

    public void addListenerOnButton() {

        button = (Button)findViewById(R.id.startNewSensor);

        button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {

              Calendar calendar = Calendar.getInstance();
              calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(),
              tp.getCurrentHour(), tp.getCurrentMinute(), 0);
              long startTime = calendar.getTime().getTime();

              
              int selectedId = radioGroup.getCheckedRadioButtonId();
              String location = new String();
              if(selectedId == R.id.sensor_location_private) {
                  location = "private";
              } else if(selectedId == R.id.sensor_location_hand) {
                  location = "hand";
              } else if (selectedId == R.id.sensor_location_bottom) {
                  location = "bottom";
              } else if (selectedId == R.id.sensor_location_other) {
                  location = sensor_location.getText().toString();
              }
              
              Sensor.create(startTime, location);
              Log.w("NEW SENSOR", "Sensor started at " + startTime);

              Toast.makeText(getApplicationContext(), "NEW SENSOR STARTED", Toast.LENGTH_LONG).show();
              Intent intent = new Intent(getApplicationContext(), Home.class);
              CollectionServiceStarter.newStart(getApplicationContext());
              startActivity(intent);
              finish();
          }

        });
        
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.sensor_location_other) {
                    sensor_location.setEnabled(true);
                    sensor_location.requestFocus();
                } else {
                    sensor_location.setEnabled(false);
                }
            }
            
        });
        
    }
}
