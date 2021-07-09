package com.example.qno118arid2982;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    String units[] = {"Days", "Months", "Years"};
    Spinner unitSpinner;
    String colors[] = {"Red", "Green", "Blue"};
    Spinner colorSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        unitSpinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        colorSpinner= (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                colors);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter2);

        checkSharePref();

    }

    public void saveSettings(View v){
        int unit = unitSpinner.getSelectedItemPosition() + 1;
        int color = colorSpinner.getSelectedItemPosition() + 1;

        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("unit", unit);
        editor.putInt("color", color);
        editor.apply();

        Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void checkSharePref(){
        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int unit = sharedPref.getInt("unit", 1) - 1;
        int color = sharedPref.getInt("color", 1) - 1;
        unitSpinner.setSelection(unit);
        colorSpinner.setSelection(color);
    }

}
