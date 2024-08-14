
package com.example.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class alarm extends Fragment {
    Button btnHenGio, btnDungLai;
    TextView txtHienThi;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public alarm() {
    }

    @SuppressLint("ServiceCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        btnHenGio = view.findViewById(R.id.btnHenGio);
        btnDungLai = view.findViewById(R.id.btnDungLai);
        txtHienThi = view.findViewById(R.id.textView);
        timePicker = view.findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);

        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                calendar.set(Calendar.HOUR_OF_DAY, gio);
                calendar.set(Calendar.MINUTE, phut);
                calendar.set(Calendar.SECOND, 0);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1); // Chuyển sang ngày mai
                }

                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);

                if (gio >= 12) {
                    string_gio = String.valueOf(gio > 12 ? gio - 12 : gio);
                    string_phut += " PM";
                } else {
                    string_phut += " AM";
                }

                if (phut < 10) {
                    string_phut = "0" + string_phut;
                }
                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(
                        getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                txtHienThi.setText("Đã Đặt Giờ " + string_gio + ":" + string_phut);
            }
        });

        btnDungLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                txtHienThi.setText("Dừng Lại");
                intent.putExtra("extra", "off");
                getActivity().sendBroadcast(intent);
            }
        });

        return view;
    }
}