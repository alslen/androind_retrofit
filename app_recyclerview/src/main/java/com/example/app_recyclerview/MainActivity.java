package com.example.app_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        TextView name = findViewById(R.id.name);
        TextView phone = findViewById(R.id.phone);

        List<Person> personList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //Adapter
        PersonAdapter adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);

        // 데이터를 강제적으로 주입시킴
        for(int i=0; i<20; i++){
            adapter.addItem(new Person("홍길동"+i, "010-1111-2222"));
        }
        adapter.setListener(new PersonListener() {  // PersonListener()은 인터페이스 이기 때문에 반드시 메서드를 구현해줘야함.
            @Override
            public void onItemClick(int position) {
                Person person = adapter.getItem(position);
                name.setText(person.getName());
                phone.setText(person.getPhone());
               // Toast.makeText(getApplicationContext(), person.getName()+"선택됨", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnLongItemClickListener(new PersonAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int position) {
                Person person = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), person.getPhone()+"선택됨", Toast.LENGTH_SHORT).show();
                adapter.removeItem(position);
            }
        });

    }
}