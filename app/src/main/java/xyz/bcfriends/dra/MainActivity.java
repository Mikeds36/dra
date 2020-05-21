package xyz.bcfriends.dra;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        Calendar calendar = Calendar.getInstance();

        CalendarView calendarView = findViewById(R.id.calendarView);

        List<EventDay> events = new ArrayList<>();

        try {
            DataBaseHelper dbh = new DataBaseHelper(this);

            LocalDate ld = LocalDate.now();
            ld.with(TemporalAdjusters.firstDayOfMonth());

            Cursor res = dbh.getDepressStatus(ld.with(TemporalAdjusters.firstDayOfMonth()), ld.with(TemporalAdjusters.lastDayOfMonth()));

            while (res.moveToNext()) {

            }

            calendarView.setDate(calendar);
            calendarView.setEvents(events);

        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }
}
