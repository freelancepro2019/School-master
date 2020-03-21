package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Time_Ago;
import com.softray_solutions.newschoolproject.databinding.RoomRowBinding;
import com.softray_solutions.newschoolproject.model.RoomModel;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;
import com.softray_solutions.newschoolproject.ui.fragments.messages.MessagesFragment;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoomModel.Records> list;
    private Context context;
    private LayoutInflater inflater;
    private MainActivity activity;
    private MessagesFragment fragment;
    private String current_user_id;


    public RoomAdapter(List<RoomModel.Records> list, Context context, MessagesFragment fragment,String current_user_id) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (MainActivity) context;
        this.fragment = fragment;
        this.current_user_id = current_user_id;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        RoomRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.room_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RoomModel.Records model = list.get(position);
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setUserId(current_user_id);
        myHolder.binding.setTime(Time_Ago.getTimeAgo(Long.parseLong(model.getCreated_at())*1000,context));


        myHolder.itemView.setOnClickListener(view -> {

            RoomModel.Records model2 = list.get(myHolder.getAdapterPosition());
            fragment.setItemRoomData(model2,myHolder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public RoomRowBinding binding;

        public MyHolder(@NonNull RoomRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }






}
