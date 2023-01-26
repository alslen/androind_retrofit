package com.example.project_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Button btn;

    private MovieInterface apiInterface;
    List<Movie> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        recyclerView = findViewById(R.id.reyclerView);

        list = new ArrayList<>();
        adapter = new MovieAdapter(list);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {  // 버튼을 누르면 네트워크에 연결
            @Override
            public void onClick(View v) {
            apiInterface = MovieClient.getClient().create(MovieInterface.class);
            Call<List<Movie>> call = apiInterface.doGetMovie();  // retrofit 연결

            call.enqueue(new Callback<List<Movie>>() {
                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                    Log.d("TAG", response.code()+"");
                    Log.d("TAG", response.toString()+"");
                    String displayResponse = "";

                    List<Movie> resource = response.body();  // body()에 우리가 원하는 값들이 들어있음.
                    Log.d("TAG", resource.size()+"");
                    Log.d("adapter.getItemCount()", adapter.getItemCount()+"");

                    for(Movie zip : resource){
                        list.add(zip);
                    }
                    Toast.makeText(getApplicationContext(), adapter.getItemCount()+"", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();  // 바뀐 내용을 recyclerView에 적용시키기 위해 사용
                }

                @Override
                public void onFailure(Call<List<Movie>> call, Throwable t) {

                }
            });

            }
        });

    }
}