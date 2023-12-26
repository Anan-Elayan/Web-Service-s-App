package com.example.apiapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiapp.R;

import org.json.JSONException;
import org.json.JSONObject;


public class ExchangeRatesActivity extends AppCompatActivity {

    // Components
    private TextView txtILS;
    private TextView txtUSD;
    private TextView txtJOD;
    private TextView txtEGP;
    private RequestQueue queue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rates);
        queue = Volley.newRequestQueue(this);
        txtILS = findViewById(R.id.txtILS);
        txtUSD = findViewById(R.id.txtUSD);
        txtJOD = findViewById(R.id.txtJOD);
        txtEGP = findViewById(R.id.txtEGP);
        getData();
    }

    // load data from web serves
    public void getData(){

        String str = "http://api.exchangeratesapi.io/v1/latest?access_key=15851cfdb264fb3ccd316c18b29419c6";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("rates");
                    for (int i = 0; i < jsonObject.length(); i++) {
                        // return data from api as a object
                        txtILS.setText(jsonObject.getString("ILS"));
                        txtJOD.setText(jsonObject.getString("JOD"));
                        txtEGP.setText(jsonObject.getString("EGP"));
                        txtUSD.setText(jsonObject.getString("USD"));
                    }
                    Toast.makeText(ExchangeRatesActivity.this,"Loaded Successfully",Toast.LENGTH_SHORT).show();

                }
                catch (JSONException exception){
                    Toast.makeText(ExchangeRatesActivity.this,"Error" + exception.toString(),Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(exception);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error 2"+error.toString());
                Toast.makeText(ExchangeRatesActivity.this,"Error" + error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) ;
        queue.add(request);
    }
}