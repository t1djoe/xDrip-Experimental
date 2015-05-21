package com.eveningoutpost.dexdrip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class LicenseAgreementActivity extends Activity {
    private boolean IUnderstand;
    private CheckBox agreeCheckBox;
    private Button saveButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        IUnderstand = prefs.getBoolean("I_understand", false);
        setContentView(R.layout.activity_license_agreement);
        agreeCheckBox = (CheckBox)findViewById(R.id.agreeCheckBox);
        agreeCheckBox.setChecked(IUnderstand);
        saveButton = (Button)findViewById(R.id.saveButton);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                prefs.edit().putBoolean("I_understand", agreeCheckBox.isChecked()).apply();

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }

        });
    }
}
