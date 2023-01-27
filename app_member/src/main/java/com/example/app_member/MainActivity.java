package com.example.app_member;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    MemberAdapter adapter;
    List<Member> memberList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleview1);
        Button btnInsert = findViewById(R.id.btnInsert);

        // 전체보기
        MemberService memberService = Retrofit2Client.getInstance().getMemberService();
        Call<List<Member>> call = memberService.findAll();

        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                memberList = response.body();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new MemberAdapter(memberList);
                recyclerView.setAdapter(adapter);

                Log.d("data", "onResponse: 응답 받은 데이터 : "+memberList);
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {

            }
        });

        // 추가하기
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

    }
    private void addMember(){
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog, null);

        final EditText etName = dialogView.findViewById(R.id.etname);
        final EditText etAge = dialogView.findViewById(R.id.etage);
        final EditText etPhone = dialogView.findViewById(R.id.etphone);
        final EditText etAddr = dialogView.findViewById(R.id.etaddr);
        final EditText etEmail = dialogView.findViewById(R.id.etemail);

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("멤버 등록");
        dlg.setIcon(R.drawable.ic_menu_allfriends);
        dlg.setView(dialogView);
        dlg.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Member memberDto = new Member();
                memberDto.setName(etName.getText().toString());
                memberDto.setAge(Long.parseLong(etAge.getText().toString()));
                memberDto.setPhone(etPhone.getText().toString());
                memberDto.setAddr(etAddr.getText().toString());
                memberDto.setEmail(etEmail.getText().toString());

                Log.d("insert", "onClick: 등록 클릭시 값 확인"+memberDto);

                MemberService memberService = Retrofit2Client.getInstance().getMemberService();
               Call<Member> call= memberService.save(memberDto);

               call.enqueue(new Callback<Member>() {
                   @Override
                   public void onResponse(Call<Member> call, Response<Member> response) {
                       adapter.addItem(response.body());
                   }

                   @Override
                   public void onFailure(Call<Member> call, Throwable t) {

                   }
               });


            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();

    }
}