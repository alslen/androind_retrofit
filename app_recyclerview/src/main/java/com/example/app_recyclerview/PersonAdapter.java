package com.example.app_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    // 인터페이스
    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener;
    //setter
    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener){
        this.onLongItemClickListener = onLongItemClickListener;
    }


    private List<Person> personList;

    private PersonListener listener;

    public void setListener(PersonListener listener){
        this.listener = listener;
    }

    public PersonAdapter(List<Person> personList){
        this.personList = personList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvPhone;

        public ViewHolder(@NonNull android.view.View itemView, final PersonListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvname);
            tvPhone = itemView.findViewById(R.id.tvphone);

            // 이름 출력
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
            
            // 전화번호 출력(롱클릭)
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    onLongItemClickListener.onLongItemClick(position);
                    return false;
                }
            });
        }
    }

    // 추가
    public void addItem(Person person){
        personList.add(person);
        notifyDataSetChanged();
    }

    public Person getItem(int position){
       Person person =  personList.get(position);
        return person;
    }

    public void removeItem(int position){
        personList.remove(position);
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, listener);  // main과 apdater를 연결시키기 위해 listener를 추가했음.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.tvName.setText(person.getName());
        holder.tvPhone.setText(person.getPhone());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 // Toast.makeText(v.getContext(), person.getName()+" 선택됨", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//               // Toast.makeText(v.getContext(), person.getPhone()+"", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return personList==null?0:personList.size();
    }
}
