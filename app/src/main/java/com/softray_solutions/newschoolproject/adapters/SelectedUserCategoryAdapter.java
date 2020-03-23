package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.databinding.FileRowBinding;
import com.softray_solutions.newschoolproject.databinding.UserCategoryRowBinding;
import com.softray_solutions.newschoolproject.model.FileModel;
import com.softray_solutions.newschoolproject.model.UserCategoryModel;
import com.softray_solutions.newschoolproject.ui.activities.activity_file.FileActivity;
import com.softray_solutions.newschoolproject.ui.activities.activity_new_message.CreateNewMessageActivity;

import java.util.List;

public class SelectedUserCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserCategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private CreateNewMessageActivity activity;


    public SelectedUserCategoryAdapter(List<UserCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (CreateNewMessageActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        UserCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_category_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        UserCategoryModel model = list.get(position);
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(model);

        myHolder.binding.imageDelete.setOnClickListener(view -> {
            UserCategoryModel model2 = list.get(myHolder.getAdapterPosition());
            activity.setItemDataToDelete(model2,myHolder.getAdapterPosition());

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public UserCategoryRowBinding binding;

        public MyHolder(@NonNull UserCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
