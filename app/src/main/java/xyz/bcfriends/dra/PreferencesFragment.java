package xyz.bcfriends.dra;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

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
        int hourOfDay, minute;
        super.onPreferenceTreeClick(preference);
        Calendar cal = Calendar.getInstance();
        final AlarmScheduler alarmScheduler = new AlarmScheduler(requireActivity(), prefs);

        switch (preference.getKey()) {
            case "alarm_daily":
                if (prefs.getBoolean("alarm_daily", false)) {
                    alarmScheduler.scheduleNotification();
                }
                else {
                    alarmScheduler.cancelNotification();
                }
                break;
            case "alarm_time":
                hourOfDay = prefs.getInt("hourOfDay", cal.get(Calendar.HOUR_OF_DAY));
                minute = prefs.getInt("minute", cal.get(Calendar.MINUTE));

                TimePickerDialog dialog = new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("hourOfDay", hourOfDay);
                        editor.putInt("minute", minute);
                        editor.apply();
                        alarmScheduler.scheduleNotification();
                    }
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
}
