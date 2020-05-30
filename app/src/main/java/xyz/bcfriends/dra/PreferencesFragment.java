package xyz.bcfriends.dra;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import xyz.bcfriends.dra.util.AlarmPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PreferencesFragment extends PreferenceFragmentCompat {
    SharedPreferences prefs;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
//        prefs = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity());
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        super.onPreferenceTreeClick(preference);
        final Intent alarmIntent = new Intent(requireActivity(), DailyAlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(requireActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmPresenter presenter = new AlarmPresenter(requireActivity(), prefs, pendingIntent);

        switch (preference.getKey()) {
            case "alarm_daily":
                if (prefs.getBoolean("alarm_daily", false)) {
                    presenter.scheduleAlarm();
                }
                else {
                    presenter.cancelAlarm();
                }
                break;
            case "alarm_time":
                Calendar cal = presenter.getScheduleTime();
                int hourOfDay, minute;

                hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(requireActivity(), (view, hourOfDay1, minute1) -> {
                    Calendar cal1 = Calendar.getInstance();
                    cal1.set(Calendar.HOUR_OF_DAY, hourOfDay1);
                    cal1.set(Calendar.MINUTE, minute1);
                    presenter.scheduleAlarm(cal1);
                }, hourOfDay, minute, false);

                dialog.show();
                break;
            case "app_info":
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("앱 정보").setMessage("Dra v" + BuildConfig.VERSION_NAME);
                builder.setPositiveButton("OK", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case "debug":
                Intent intent = new Intent(requireActivity(), TestActivity.class);
                startActivity(intent);
                break;
            default:
                return false;
        }

        return true;
    }

    public void showAlarmMessage(Calendar scheduleTime) {
        String nextNotifyDate = new SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분", Locale.getDefault()).format(scheduleTime.getTime());
        Toast.makeText(requireActivity(), nextNotifyDate + "으로 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
