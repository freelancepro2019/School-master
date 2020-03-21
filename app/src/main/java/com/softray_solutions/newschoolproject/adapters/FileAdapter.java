package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.databinding.FileRowBinding;
import com.softray_solutions.newschoolproject.model.FileModel;
import com.softray_solutions.newschoolproject.ui.activities.activity_file.FileActivity;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FileModel> list;
    private Context context;
    private LayoutInflater inflater;
    private FileActivity activity;


    public FileAdapter(List<FileModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (FileActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        FileRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.file_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FileModel model = list.get(position);
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setFile(model);
        if (model.getName().endsWith(".pdf"))
        {
            myHolder.binding.image.setImageResource(R.drawable.pdf);
        }else if (model.getName().endsWith(".doc")||model.getName().endsWith(".docx"))
        {
            myHolder.binding.image.setImageResource(R.drawable.word);

        }

        myHolder.itemView.setOnClickListener(view -> {
            FileModel model2 = list.get(myHolder.getAdapterPosition());
            activity.setItemData(model2);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public FileRowBinding binding;

        public MyHolder(@NonNull FileRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
