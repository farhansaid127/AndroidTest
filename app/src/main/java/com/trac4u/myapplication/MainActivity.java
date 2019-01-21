package com.trac4u.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText idetSearchBox;
    Button idbtnSearch;
    Context context;
    ProgressBar idProgressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idetSearchBox = findViewById(R.id.idetSearchBox);
        idbtnSearch = findViewById(R.id.idbtnSearch);
        idProgressBar = findViewById(R.id.idProgressBar);
        context = this;
        idbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textSearch = idetSearchBox.getText().toString();
                if (textSearch.isEmpty()) {
                    Toast.makeText(context, getString(R.string.empty_searchbox), Toast.LENGTH_SHORT).show();
                } else {
                    getUser(textSearch);
                }
            }
        });
    }

    private void getUser(String textSearch) {
        idProgressBar.setVisibility(View.VISIBLE);
        idbtnSearch.setEnabled(false);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, "https://api.github.com/users/" + textSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        idbtnSearch.setEnabled(true);
                        idProgressBar.setVisibility(View.INVISIBLE);
                        Log.d("TAG", response);

                        try {
                            JSONObject userObj = new JSONObject(response);
                            String userName = userObj.getString("login");
                            String email = userObj.getString("email");
                            String avatarUrl = userObj.getString("avatar_url");
                            Intent intent = new Intent(context, UserDetails.class);
                            if (email.equalsIgnoreCase("null")) {
                                email = "No Email";
                            }
                            if (userName.equalsIgnoreCase("null")) {
                                userName = "No Username";
                            }
                            if (avatarUrl.equalsIgnoreCase("null")) {
                                avatarUrl = "No avatar url exist";
                            }
                            intent.putExtra("userName", userName);
                            intent.putExtra("email", email);
                            intent.putExtra("avatarUrl", avatarUrl);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, getString(R.string.userNotFound), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                idProgressBar.setVisibility(View.INVISIBLE);
                idbtnSearch.setEnabled(true);
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "Oops. Network error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Oops. Server error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Oops.  Auth Failure error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Oops.  Parse error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Oops.  Connection error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, getString(R.string.Something_went_wrong), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "token ec6aade995b02d01d4e4b0717c3b033f8ebc09ce");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


}
