package com.example.myapplication;

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

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {

    private List<Phone> phoneList;

    public PhoneAdapter(List<Phone> phoneList){
        this.phoneList = phoneList;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvTel;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTel = itemView.findViewById(R.id.tvTel);
        }
    }
    
    // insert
    public void addItem(Phone phone){
        phoneList.add(phone);
        notifyDataSetChanged();  // 새로고침
    }

    // update
    public void updateItem(Phone phone, int position){
        //phoneList.set(position, phone);
        Phone p = phoneList.get(position);
        p.setName(phone.getName());
        p.setTel(phone.getTel());
        notifyDataSetChanged();
    }

    // delete
    public void removeItem(int position){
        phoneList.remove(position);
        notifyDataSetChanged();  // 새로고침
    }


    @NonNull
    @Override
    public PhoneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_list, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {  // 특정 위치값을 가지고 있음.
        Phone phone = phoneList.get(position);
        holder.tvName.setText(phone.getName());
        holder.tvTel.setText(phone.getTel());
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {  // holder에 있는 item하나를 클릭하는 이벤트
            @Override
            public void onClick(View v) {
                View dialogView = v.inflate(v.getContext(), R.layout.layout_add_concat, null);
                final EditText etName = dialogView.findViewById(R.id.etname);
                final EditText etTel = dialogView.findViewById(R.id.ettel);

                etName.setText(phone.getName());
                etTel.setText(phone.getTel());

                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                dlg.setTitle("연락처 수정");
                dlg.setView(dialogView);
                dlg.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Phone phoneDto = new Phone();

                        phoneDto.setName(etName.getText().toString());
                        phoneDto.setTel(etTel.getText().toString());

                        Log.d("update", "onClick: 등록 클릭시 값 확인"+phoneDto);

                        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
                        Call<Phone> call = phoneService.update(phone.getId(), phoneDto);

                        call.enqueue(new Callback<Phone>() {
                            @Override
                            public void onResponse(Call<Phone> call, Response<Phone> response) {
                                Log.d("body", "onClick:"+response.body());
                                updateItem(response.body(), position);  // response.body() : Phone객체
                            }

                            @Override
                            public void onFailure(Call<Phone> call, Throwable t) {

                            }
                        });

                    }
                });
                dlg.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
                        Call<Void> call = phoneService.deleteById(phone.getId());
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
        return phoneList==null?0:phoneList.size();
    }
}
