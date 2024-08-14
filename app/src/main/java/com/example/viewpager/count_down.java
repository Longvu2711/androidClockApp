package com.example.viewpager;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class count_down extends Fragment {

    private EditText etHours;
    private EditText etMinutes;
    private TextView tvCountdown;
    private EditText etSeconds;

    private CountDownTimer countDownTimer;

    private static final String CHANNEL_ID = "countdown_channel";


    public count_down() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_down, container, false);

        etHours = view.findViewById(R.id.et_hours);
        etMinutes = view.findViewById(R.id.et_minutes);
        etSeconds = view.findViewById(R.id.et_seconds);
        tvCountdown = view.findViewById(R.id.tv_countdown);
        Button btnStart = view.findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountdown();
            }
        });

        return view;
    }

    private void startCountdown() {
        String hoursStr = etHours.getText().toString();
        String minutesStr = etMinutes.getText().toString();
        String secondsStr = etSeconds.getText().toString();
        if (TextUtils.isEmpty(hoursStr)) hoursStr = "0";
        if (TextUtils.isEmpty(minutesStr)) minutesStr = "0";
        if (TextUtils.isEmpty(secondsStr)) secondsStr = "0";
        int hours = Integer.parseInt(hoursStr);
        int minutes = Integer.parseInt(minutesStr);
        int seconds = Integer.parseInt(secondsStr);

        long totalMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) (millisUntilFinished / 3600000);
                int minutes = (int) ((millisUntilFinished % 3600000) / 60000);
                int seconds = (int) ((millisUntilFinished % 60000) / 1000);
                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                tvCountdown.setText(time);
            }
            @Override
            public void onFinish() {
                tvCountdown.setText("00:00:00");
                showAlertDialog();
            }
        };
        countDownTimer.start();
    }
    private void showAlertDialog() {
        new AlertDialog.Builder(requireContext())
                .setMessage("Đếm ngược hoàn tất. Hết giờ")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}