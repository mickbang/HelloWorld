package com.lq.helloworld;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lq.helloworld.databinding.ActivityThridBinding;
import com.lq.helloworld.volley.Request;
import com.lq.helloworld.volley.RequestQueue;
import com.lq.helloworld.volley.Volley;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ThirdActivity extends AppCompatActivity {
    String url = "https://www.baidu.com";
    int i;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityThridBinding binding = ActivityThridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        requestQueue = Volley.newRequestQueue();


        binding.btnRequest.setOnClickListener(v -> {
            binding.tvResult.setText("");
            // Add the request to the RequestQueue.

            for (int i1 = 0; i1 < 10; i1++) {
                request("张三"+i1+"------");
            }
        });

        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().build();
        try {
            okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    private void request(String text) {
        Request request = new Request(text + i);
        requestQueue.add(request);
        i++;
    }
}
