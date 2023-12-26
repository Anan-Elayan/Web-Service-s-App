package com.example.apiapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PrayerTimeActivity extends AppCompatActivity {


    // components
    private Spinner spinner;
    private TextView txtFajr;
    private TextView txtDuhr;
    private TextView txtAsr;
    private TextView txtMaghrib;
    private TextView txtIsha;
    private Button btnSearch;
    private RequestQueue queue;
    private static final String PREFS_NAME = "PrayerTimePrefs";
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayre_time);
        // call method
        setupComponents();
        loadSavedValues();
    }


    // when user clicked on back
    @Override
    protected void onStop() {
        super.onStop();
        // save date to shared pref
        saveValuesToPrefs();
    }


    // save to shred pref
    private void saveValuesToPrefs() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("txtFajr", txtFajr.getText().toString());
        editor.putString("txtDuhr", txtDuhr.getText().toString());
        editor.putString("txtAsr", txtAsr.getText().toString());
        editor.putString("txtMaghrib", txtMaghrib.getText().toString());
        editor.putString("txtIsha", txtIsha.getText().toString());
        int selectedItemPosition = spinner.getSelectedItemPosition();
        editor.putInt("city", selectedItemPosition);
        editor.commit();
    }

    // load data from shared pref
    private void loadSavedValues() {
        txtFajr.setText(preferences.getString("txtFajr", ""));
        txtDuhr.setText(preferences.getString("txtDuhr", ""));
        txtAsr.setText(preferences.getString("txtAsr", ""));
        txtMaghrib.setText(preferences.getString("txtMaghrib", ""));
        txtIsha.setText(preferences.getString("txtIsha", ""));
        spinner.setSelection(preferences.getInt("city", 0));
    }


    public void setupComponents() {
        spinner = findViewById(R.id.spinnerCitys);
        txtFajr = findViewById(R.id.txtFajr);
        txtDuhr = findViewById(R.id.txtDuhr);
        txtAsr = findViewById(R.id.txtAsr);
        txtMaghrib = findViewById(R.id.txtMaghrib);
        txtIsha = findViewById(R.id.txtIsha);
        queue = Volley.newRequestQueue(this);
        btnSearch = findViewById(R.id.btnSearch);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

    }


    // call data from web serves
    public void search(View view) {
        String selectedLocation = spinner.getSelectedItem().toString();
        String URL = "https://api.aladhan.com/v1/calendarByAddress/2023/12?address=Alaqsa,%20" + selectedLocation + ",%20Palestine&method=2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("data")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            JSONObject timing = jsonArray.getJSONObject(0);
                            JSONObject jsonObject = timing.getJSONObject("timings");

                            String fajr = jsonObject.getString("Fajr").substring(0, 5);
                            String dhuhr = jsonObject.getString("Dhuhr").substring(0, 5);
                            String asr = jsonObject.getString("Asr").substring(0, 5);
                            String maghrib = jsonObject.getString("Maghrib").substring(0, 5);
                            String isha = jsonObject.getString("Isha").substring(0, 5);

                            txtFajr.setText(fajr);
                            txtDuhr.setText(dhuhr);
                            txtAsr.setText(asr);
                            txtMaghrib.setText(maghrib);
                            txtIsha.setText(isha);
                            Toast.makeText(PrayerTimeActivity.this, "Loaded successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(PrayerTimeActivity.this, "Empty data array in the response.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PrayerTimeActivity.this, "Response does not contain 'data' field.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(PrayerTimeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrayerTimeActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}