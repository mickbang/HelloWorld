package com.lq.helloworld;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lq.helloworld.databinding.ActivityThridBinding;

public class ThirdActivity extends AppCompatActivity {
    String url = "https://www.baidu.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityThridBinding binding = ActivityThridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RequestQueue queue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        binding.tvResult.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.tvResult.setText("That didn't work!");
            }
        });

        binding.btnRequest.setOnClickListener(v -> {
            binding.tvResult.setText("");
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        });
    }
}
