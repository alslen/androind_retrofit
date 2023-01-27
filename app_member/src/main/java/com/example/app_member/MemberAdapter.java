package com.example.app_member;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private List<Member> memberList;

    public MemberAdapter(List<Member> memberList){
        this.memberList = memberList;
    }

    class MemberViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvAge, tvPhone, tvAddr, tvEmail;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddr = itemView.findViewById(R.id.tvAddr);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }

    // list에 값을 추가
    public void addItem(Member member){
        memberList.add(member);
        notifyDataSetChanged();
    }

    // list에 값을 수정
    public void updateItem(Member member, int position){
        Member m = memberList.get(position);
        m.setEmail(member.getEmail());
        m.setName(member.getName());
        m.setAge(member.getAge());
        m.setAddr(member.getAddr());
        m.setPhone(member.getPhone());
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        memberList.remove(position);
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);

        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Member member = memberList.get(position);

        holder.tvName.setText(member.getName());
        holder.tvAge.setText(member.getAge().toString());
        holder.tvPhone.setText(member.getPhone());
        holder.tvAddr.setText(member.getAddr());
        holder.tvEmail.setText(member.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(v.getContext(), R.layout.dialog, null);
                final EditText etName = dialogView.findViewById(R.id.etname);
                final EditText etAge = dialogView.findViewById(R.id.etage);
                final EditText etPhone = dialogView.findViewById(R.id.etphone);
                final EditText etAddr = dialogView.findViewById(R.id.etaddr);
                final EditText etEmail = dialogView.findViewById(R.id.etemail);

                etName.setText(member.getName());
                etAge.setText(member.getAge().toString());
                etPhone.setText(member.getPhone());
                etAddr.setText(member.getAddr());
                etEmail.setText(member.getEmail());

                etName.setFocusable(false);

                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                dlg.setTitle("회원 수정");
                dlg.setIcon(R.drawable.ic_menu_allfriends);
                dlg.setView(dialogView);
                dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Member memberDto = new Member();

                        memberDto.setAddr(etAddr.getText().toString());
                        memberDto.setPhone(etPhone.getText().toString());
                        memberDto.setName(etName.getText().toString());
                        memberDto.setAge(Long.parseLong(etAge.getText().toString()));
                        memberDto.setEmail(etEmail.getText().toString());

                        Log.d("update", "onClick: 등록 클릭시 값 확인"+memberDto);

                        MemberService memberService = Retrofit2Client.getInstance().getMemberService();
                        Call<Member> call = memberService.update(memberDto.getName(),memberDto);

                        call.enqueue(new Callback<Member>() {
                            @Override
                            public void onResponse(Call<Member> call, Response<Member> response) {
                                Log.d("body", "onClick:"+response.body());
                                updateItem(response.body(), position);
                            }

                            @Override
                            public void onFailure(Call<Member> call, Throwable t) {

                            }
                        });

                    }
                });
                dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MemberService memberService = Retrofit2Client.getInstance().getMemberService();
                        Call<Void> call = memberService.delete(member.getName());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                removeItem(position);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });

                    }
                });
                dlg.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return memberList==null?0:memberList.size();
    }
}
