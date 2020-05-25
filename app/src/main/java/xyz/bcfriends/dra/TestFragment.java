package xyz.bcfriends.dra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;


public class TestFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar, container, false);

        Calendar calendar = Calendar.getInstance();

        CalendarView calendarView = v.findViewById(R.id.calendarView);

        List<EventDay> events = new ArrayList<>();

        try {
            DataBaseHelper dbh = new DataBaseHelper(getActivity());

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

            calendarView.setOnDayClickListener(eventDay -> {
                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();

                bottomSheetDialog.show(requireActivity().getSupportFragmentManager(), "bottomSheet");
            });

        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        return v;
    }
}
