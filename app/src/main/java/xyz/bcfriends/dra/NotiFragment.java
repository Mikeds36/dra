package xyz.bcfriends.dra;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

public class NotiFragment extends Fragment {
    private static final String CHANNEL_ID = "main";

    public NotiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Activity mainActivity = requireActivity();
        View v = inflater.inflate(R.layout.notification_fragment, container, false);

        final NotificationImpl noti = new NotificationImpl(mainActivity);
        noti.createNotificationChannel(CHANNEL_ID, "알림 채널", "테스트용 알림 채널");

        Button btn = v.findViewById(R.id.push_btn);
        final Intent intent = new Intent(mainActivity, MainActivity.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity mainActivity = requireActivity();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(mainActivity, 0, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(mainActivity, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Dra")
                        .setContentText("오늘의 기분은 어떠신가요?")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                noti.notify("test", builder.build());
            }
        });

        return v;
    }
}
