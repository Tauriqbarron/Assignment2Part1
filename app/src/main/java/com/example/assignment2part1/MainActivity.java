package com.example.assignment2part1;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.*;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

public class MainActivity extends AppCompatActivity {
// www.wordsapi.com



    TextView searchText;
    TextView searchResult;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = findViewById(R.id.wordSearchTxt);
        searchResult = findViewById(R.id.definitionTxt);
        btnSearch = findViewById(R.id.btnSearch);


        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence wordSearch =searchText.getText();

                String url = Uri.parse("https://wordsapiv1.p.mashape.com/words/"+wordSearch+
                        "/definitions").buildUpon().toString();

                StringRequest stRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        StringBuilder txtResult = new StringBuilder();
                        String resp = response;
                        try {
                            JsonObject res = JS
                            JSONArray defArray = res.getJSONArray("definitions");

                            for(int idx = 0; idx< defArray.length(); idx++){
                                JSONObject curDef = defArray.getJSONObject(idx);
                                JSONObject definition = curDef.getJSONObject("definition");
                                txtResult.append(definition.getString("definition")+"/n");
                            }
                        }catch (Throwable t){
                            Log.e("Error","Could not parse");
                        }

                        //searchResult.setText(resp.toString());
                        System.err.println(response);


                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                    }
                }){
                    @Override
                    public Map<String,String> getHeaders() throws AuthFailureError{
                        Map<String,String> params = new HashMap<>();
                        params.put("X-Mashape-Key","dd1d560155msh2a3a50aa39ffde2p13e582jsne6443e91eb58");
                        params.put("Accept","text/plain");
                        return params;
                    }
                };
                queue.add(stRequest);
            }
        });
    }
    private static JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }
}
