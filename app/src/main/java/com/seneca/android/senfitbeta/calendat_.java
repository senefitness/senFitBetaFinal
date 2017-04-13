package com.seneca.android.senfitbeta;

import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class calendat_ extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView dateDisplay;
    private String name;
    private int id;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendat_);

        db = new DbHelper(this);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        
        dateDisplay =(TextView)findViewById(R.id.date_display);
        dateDisplay.setText("Select Preferred Date: ");
        id = getIntent().getExtras().getInt("calExerId");
        name = getIntent().getExtras().getString("calExerName");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               // dateDisplay.setText("Date: " + (month+1) + " / " + dayOfMonth + " / " + year);

                if(db.insertCAL(id,month,dayOfMonth,year,name)){
                    Toast.makeText(getApplicationContext(),"Added SuccessFully", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(),"Failed To Add", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
