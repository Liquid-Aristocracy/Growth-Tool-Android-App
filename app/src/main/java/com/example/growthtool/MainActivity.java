package com.example.growthtool;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    // Get the widgets reference from XML layout
    TextView birthdate, thisdate, ageresult;
    Button resetbirth, resetthis;
    Calendar today = Calendar.getInstance();
    Calendar birthday = Calendar.getInstance();
    Calendar thisday = Calendar.getInstance();

    int yearsAge, monthAge, dayAge;

    @SuppressLint("SetTextI18n")
    private void calcAge() {
        ageresult.setText(R.string.calculating);
        long miliSecondForBirth = birthday.getTimeInMillis();
        long miliSecondForThis = thisday.getTimeInMillis();
        // Calculate the difference in millisecond between two dates
        long diffInMilis = miliSecondForThis - miliSecondForBirth;
        if (diffInMilis < 0) {
            ageresult.setText(R.string.age_alert);
        }
        else {
            int tempday = thisday.get(Calendar.DAY_OF_MONTH);
            int tempmonth = thisday.get(Calendar.MONTH);
            int tempyear = thisday.get(Calendar.YEAR);
            int[] dayinmonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if ((tempyear % 4 == 0 && tempyear % 100 != 0) || tempyear % 400 == 0) {
                dayinmonth[1] = 29;
            }
            if (tempday < birthday.get(Calendar.DAY_OF_MONTH)) {
                tempmonth -= 1;
                if (tempmonth < 0) {
                    tempmonth = 11;
                    tempyear -= 1;
                }
                tempday += dayinmonth[tempmonth];
            }
            if (tempmonth < birthday.get(Calendar.MONTH)) {
                tempyear -= 1;
                tempmonth += 12;
            }
            yearsAge = tempyear - birthday.get(Calendar.YEAR);
            monthAge = tempmonth - birthday.get(Calendar.MONTH);
            dayAge = tempday - birthday.get(Calendar.DAY_OF_MONTH);
            ageresult.setText(getString(R.string.age_hint)
                    + yearsAge + getString(R.string.y)
                    + monthAge + getString(R.string.m)
                    + dayAge + getString(R.string.d));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birthday.set(today.get(Calendar.YEAR) - 8, 0, 1);
        birthday.set(Calendar.MILLISECOND, 0);
        thisday.set(today.get(Calendar.YEAR),
                today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        thisday.set(Calendar.MILLISECOND, 0);

        birthdate = findViewById(R.id.BirthDate);
        birthdate.setText(birthday.get(Calendar.YEAR) + "-"
                + (birthday.get(Calendar.MONTH) + 1) + "-" + birthday.get(Calendar.DAY_OF_MONTH));
        birthdate.setOnClickListener(this);
        resetbirth = findViewById(R.id.resetButton1);
        resetbirth.setOnClickListener(this);

        thisdate = findViewById(R.id.ThisDate);
        thisdate.setText(thisday.get(Calendar.YEAR) + "-"
                + (thisday.get(Calendar.MONTH) + 1) + "-" + thisday.get(Calendar.DAY_OF_MONTH));
        thisdate.setOnClickListener(this);
        resetthis = findViewById(R.id.resetButton2);
        resetthis.setOnClickListener(this);

        ageresult = findViewById(R.id.ageResult);
        ageresult.setText(R.string.calculating);
        ageresult.setOnClickListener(this);
        calcAge();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v == birthdate) {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    R.style.DatePickerTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            birthdate.setText(year + "-" + (month + 1) + "-" + day);
                            birthday.set(year, month, day);
                            calcAge();
                        }
                    },
                    birthday.get(Calendar.YEAR),
                    birthday.get(Calendar.MONTH),
                    birthday.get(Calendar.DAY_OF_MONTH));
            dialog.setTitle(R.string.birth_date_text);
            dialog.show();
        }

        if (v == thisdate) {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    R.style.DatePickerTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            thisdate.setText(year + "-" + (month + 1) + "-" + day);
                            thisday.set(year, month, day);
                            calcAge();
                        }
                    },
                    thisday.get(Calendar.YEAR),
                    thisday.get(Calendar.MONTH),
                    thisday.get(Calendar.DAY_OF_MONTH));
            dialog.setTitle(R.string.this_date_text);
            dialog.show();
        }

        if (v == resetbirth) {
            birthday.set(today.get(Calendar.YEAR) - 8, 0, 1);
            calcAge();
            birthdate.setText(birthday.get(Calendar.YEAR) + "-"
                    + (birthday.get(Calendar.MONTH) + 1) + "-"
                    + birthday.get(Calendar.DAY_OF_MONTH));
        }

        if (v == resetthis) {
            thisday.set(today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
            calcAge();
            thisdate.setText(thisday.get(Calendar.YEAR) + "-"
                    + (thisday.get(Calendar.MONTH) + 1) + "-"
                    + thisday.get(Calendar.DAY_OF_MONTH));
        }

        if (v == ageresult) {
            calcAge();
        }
    }
}

