package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.TeacherLibraryModel;
import com.softray_solutions.newschoolproject.ui.fragments.revisions.LibraryListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherLibraryAdapter extends RecyclerView.Adapter<TeacherLibraryAdapter.MainViewHolder> {
    private List<TeacherLibraryModel> dataSet;
    private LibraryListPresenter presenter;

    public TeacherLibraryAdapter(List<TeacherLibraryModel> dataSet, LibraryListPresenter presenter) {
        this.dataSet = dataSet;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.library_row, viewGroup, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        presenter.setTeacherLibraries(mainViewHolder, dataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.library_text_view)
        public
        TextView textView;
        @BindView(R.id.library_arrow)
        public
        ImageView imageView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
