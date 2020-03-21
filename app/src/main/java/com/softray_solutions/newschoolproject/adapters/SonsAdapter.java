package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.Son;
import com.softray_solutions.newschoolproject.ui.activities.sons.SonsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SonsAdapter extends RecyclerView.Adapter<SonsAdapter.MainViewHolder> {
    private List<Son> sonsList;
    private SonsPresenter presenter;

    public SonsAdapter(List<Son> sonsList, SonsPresenter presenter) {
        this.sonsList = sonsList;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_son, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        presenter.setupHolder(holder, position, sonsList);
    }

    @Override
    public int getItemCount() {
        return sonsList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.son_text_view)
        public
        TextView textView;
        @BindView(R.id.arrow)
        public
        ImageView imageView;

        private MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
