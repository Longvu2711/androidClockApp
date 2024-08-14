package com.example.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;


public class timer extends Fragment {


    private TextView tvTimer;
    private LinearLayout llLaps;
    private Button btnLap, btnStartStop, btnReset;
    private Handler handler;
    private long startTime, timeInMillis, updateTime = 0L;
    private int seconds, minutes, milliseconds;
    private boolean isRunning = false;
    private int lapCount = 1;

    public timer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        tvTimer = view.findViewById(R.id.tv_timer);
        llLaps = view.findViewById(R.id.ll_laps);
        btnLap = view.findViewById(R.id.btn_lap);
        btnStartStop = view.findViewById(R.id.btn_start_stop);
        btnReset = view.findViewById(R.id.btn_reset);

        handler = new Handler();

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLap();
            }
        });

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleStartStop();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        return view;
    }

    private void addLap() {
        String lapTime = tvTimer.getText().toString();
        TextView lapTextView = new TextView(requireContext());
        lapTextView.setText("Vòng " + lapCount + ": " + lapTime);
        lapTextView.setTextSize(18);
        llLaps.addView(lapTextView, 0);
        lapCount++;
    }

    private void toggleStartStop() {
        if (isRunning) {
            handler.removeCallbacks(runnable);
            btnStartStop.setText("Bắt đầu");
        } else {
            startTime = SystemClock.uptimeMillis() - updateTime;
            handler.post(runnable);
            btnStartStop.setText("Dừng");
        }
        isRunning = !isRunning;
    }

    private void resetTimer() {
        handler.removeCallbacks(runnable);
        isRunning = false;
        updateTime = 0L;
        tvTimer.setText("00:00:00");
        btnStartStop.setText("Bắt đầu");
        llLaps.removeAllViews();
        lapCount = 1;
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            updateTime = timeInMillis;

            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int) (updateTime % 1000);

            tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, milliseconds / 10));

            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRunning) {
            handler.removeCallbacks(runnable);
        }
    }
}