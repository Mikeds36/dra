package xyz.bcfriends.dra;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

            Cursor res = dbh.getData(ld.with(TemporalAdjusters.firstDayOfMonth()), ld.with(TemporalAdjusters.lastDayOfMonth()));

            String date;
            String datetime;
            int depressStatus;
            String note;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            while (res.moveToNext()) {
                date = res.getString(res.getColumnIndex("date"));
                datetime = res.getString(res.getColumnIndex("datetime"));
                depressStatus = res.getInt(res.getColumnIndex("depressStatus"));
                note = res.getString(res.getColumnIndex("note"));

                LocalDate dbLD = LocalDate.parse(date);

                calendar.set(dbLD.getYear(), dbLD.getMonthValue(), dbLD.getDayOfMonth());

                events.add(new EventDay(calendar, R.drawable.notification_icon));
            }

            calendarView.setDate(calendar);
            calendarView.setEvents(events);

        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }
}
