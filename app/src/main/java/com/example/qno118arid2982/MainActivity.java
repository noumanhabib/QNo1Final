package com.example.qno118arid2982;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    final Calendar c = Calendar.getInstance();
    static int yearMain = 0;
    static int monthMain = 0;
    static  int dayMain = 0;
    int unit = 1;
    int color = 1;
    long userAge = 0;
    Button btn;
    TextView ageText;
    TextView ageValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        btn = (Button) findViewById(R.id.button2);
        ageText = (TextView) findViewById(R.id.textView2);
        ageValue = (TextView) findViewById(R.id.textView3);
        setSupportActionBar(toolbar);
        checkSharePref();
        setUnitColor();
    }

    @Override
    protected void onResume() {
        checkSharePref();
        setUnitColor();
        long days =  TimeUnit.DAYS.convert(userAge, TimeUnit.MILLISECONDS);
        int months = (int) (days / 30);
        int years = months / 12;
        if(unit == 1){
            ageValue.setText(days + "");
        }
        else if(unit == 2){
            ageValue.setText(months + "");
        }
        else if(unit == 3){
            ageValue.setText(years + "");
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePicker(View v){
        DialogFragment newFragment = new DatePickerFragment(this.btn);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void checkSharePref(){
        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        unit = sharedPref.getInt("unit", 1);
        color = sharedPref.getInt("color", 1);
    }
    public void setUnitColor(){
        //Unit: {1 => days, 2=> mont, 3 => year}
        if(unit == 1){
            ageText.setText("Age In Days");
        }
        else if(unit == 2){
            ageText.setText("Age In Months");
        }
        else if(unit == 3){
            ageText.setText("Age In Years");
        }
        if(color == 1){
            ageValue.setTextColor(Color.RED);
        }
        else if(color == 2){
            ageValue.setTextColor(Color.GREEN);
        }
        else if(color == 3){
            ageValue.setTextColor(Color.BLUE);
        }
    }

    public void calculateAge(View v) throws ParseException {
        if(dayMain == 0 && monthMain == 0 && yearMain == 0){
            return;
        }
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String nowStr = day + "/" + month + "/" + year;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date nowDate = sdf.parse(nowStr);

        String selectedStr = dayMain + "/" + monthMain + "/" + yearMain;
        Date selectedDate = sdf.parse(selectedStr);

        long diff = nowDate.getTime() - selectedDate.getTime();
        userAge = diff;
        long days =  TimeUnit.DAYS.convert(userAge, TimeUnit.MILLISECONDS);
        int months = (int) (days / 30);
        int years = months / 12;
        if(unit == 1){
            ageValue.setText(days + "");
        }
        else if(unit == 2){
            ageValue.setText(months + "");
        }
        else if(unit == 3){
            ageValue.setText(years + "");
        }

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        Button datePick;
        public DatePickerFragment(Button v){
            datePick = v;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            datePick.setText(day + "-" + month + "-" + year);
            dayMain = day;
            yearMain = year;
            monthMain = month;
        }
    }
}
