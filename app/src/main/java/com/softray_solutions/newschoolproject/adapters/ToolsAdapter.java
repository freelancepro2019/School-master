package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.Tools;
import com.softray_solutions.newschoolproject.ui.fragments.tools.ToolsPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.MyViewHolder> {

    private Context context;
    private ToolsPresenter presenter;
    private ArrayList<Tools> tools;

    public ToolsAdapter(Context context, ToolsPresenter presenter, ArrayList<Tools> tools) {
        this.context = context;
        this.presenter = presenter;
        this.tools = tools;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_tools_fragmanet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        presenter.bindViewAtPosition(holder, position, tools);
    }

    @Override
    public int getItemCount() {
        return tools.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tools_imageView)
        ImageView imageView;
        @BindView(R.id.tools_title)
        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
