package com.example.app_person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etName, etTel;
    private Button btnInsert, btnUpdate, btnDelete;
    LinearLayoutManager linearLayoutManager;
    private PhoneAdapter phoneAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview1);
        etName = findViewById(R.id.etName);
        etTel = findViewById(R.id.etTel);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);


        // 전체보기
        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
        Call<List<Phone>> call = phoneService.findAll();

        call.enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                List<Phone> phoneList = response.body();
                linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                phoneAdapter = new PhoneAdapter(phoneList);
                recyclerView.setAdapter(phoneAdapter);

                phoneAdapter.setListener(new PhoneAdapter.PhoneListener() {
                    @Override
                    public void onItemClick(int position) {
                        Phone phone = phoneAdapter.getItem(position);
                        etName.setText(phone.getName());
                        etTel.setText(phone.getTel());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {

            }
        });

        // 추가버튼을 클릭했을 때
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(etTel.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    addContact();
                }

            }
        });

        // 수정버튼을 클릭했을 때
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone phoneDto = new Phone();
                phoneDto.setName(etName.getText().toString());
                phoneDto.setTel(etTel.getText().toString());

                PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
                Call<Phone> call = phoneService.update(phoneDto.getId(),phoneDto);

                call.enqueue(new Callback<Phone>() {
                    @Override
                    public void onResponse(Call<Phone> call, Response<Phone> response) {
                        Phone phone = response.body();
                        //phoneAdapter.updateItem(phone, position);
                    }

                    @Override
                    public void onFailure(Call<Phone> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void addContact() {
        Phone phoneDto = new Phone();
        phoneDto.setName(etName.getText().toString());
        phoneDto.setTel(etTel.getText().toString());

        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
        Call<Phone> call = phoneService.save(phoneDto);
        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                phoneAdapter.addItem(response.body());
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {

            }
        });
    }
}