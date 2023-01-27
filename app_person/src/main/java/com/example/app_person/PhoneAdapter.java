package com.example.app_person;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    // 인터페이스 생성
    public interface PhoneListener {
        void onItemClick(int position);
    }

    List<Phone> phoneList;
    private PhoneListener listener;
    public void setListener(PhoneListener listener){
        this.listener = listener;
    }

    public PhoneAdapter(List<Phone> phoneList){
        this.phoneList = phoneList;
    }

    class PhoneViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvTel;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvname);
            tvTel = itemView.findViewById(R.id.tvtel);
            
            // item을 클릭하면
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
        }
    }

    public void addItem(Phone phone){
        phoneList.add(phone);
        notifyDataSetChanged();
    }

    public Phone getItem(int position){
        Phone phone = phoneList.get(position);
        return phone;
    }

    public void updateItem(Phone phone, int position){

    }

    @NonNull
    @Override
    public PhoneAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.PhoneViewHolder holder, int position) {

        Phone phone = phoneList.get(position);
        holder.tvName.setText(phone.getName());
        holder.tvTel.setText(phone.getTel());
    }

    @Override
    public int getItemCount() {
        return phoneList==null?0:phoneList.size();
    }
}
