package com.example.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class world_time extends Fragment {

    private TextView timeZoneViewHanoi;
    private EditText tenThanhPho;
    private Button btnThem;
    private LinearLayout timeZonesContainer;
//    https://ipgeolocation.io/documentation/timezone-api.html

    private String urlHaNoi = "https://api.ipgeolocation.io/timezone?apiKey=13ea9b351a8e4dc9bf091e988fcdb2d8&location=Ha_Noi";
    private RequestQueue requestQueue;

    public world_time() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_world_time, container, false);

        timeZoneViewHanoi = view.findViewById(R.id.timeZoneViewHanoi);
        tenThanhPho = view.findViewById(R.id.tenThanhPho);
        btnThem = view.findViewById(R.id.btnThem);
        timeZonesContainer = view.findViewById(R.id.timeZonesContainer);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTenThanhPho = tenThanhPho.getText().toString().trim();
                if (!mTenThanhPho.isEmpty()) {
                    getTime(mTenThanhPho);
                }
            }
        });


        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlHaNoi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String timeHaNoi = response.getString("time_24");
                            String dateHaNoi = response.getString("date_time_txt");

//                            int commaIndex = dateHaNoi.indexOf(",");
//                            String dateHaNoi1 = dateHaNoi.substring(commaIndex + 2, commaIndex + 15);

                            String dateHaNoi1 = dateHaNoi.substring(0,dateHaNoi.lastIndexOf(" "));

                            timeZoneViewHanoi.setText("Giờ Hà Nội: "+ timeHaNoi + "\n" + dateHaNoi1 );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        timeZoneViewHanoi.setText("Lỗi");
                    }
                });

        requestQueue.add(request);

        return view;


    }
    private void getTime (String mTenThanhPho) {
        String url = "https://api.ipgeolocation.io/timezone?apiKey=13ea9b351a8e4dc9bf091e988fcdb2d8&location=" + mTenThanhPho;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String date = response.getString("date");
                            String time = response.getString("time_24");
//                            String location = response.getString("location");
//                            String timezone = response.getString("timezone");
                            TextView textView = new TextView(getActivity());
                            textView.setText( mTenThanhPho  + ": " + time + "\nNgày " + date );
                            textView.setTextSize(22);
                            timeZonesContainer.addView(textView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
    }
}
