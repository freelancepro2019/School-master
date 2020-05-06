package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.RevisionLibrary;
import com.softray_solutions.newschoolproject.ui.fragments.revisions.LibraryListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MainViewHolder> {
    private List<RevisionLibrary> libraries;
    private LibraryListPresenter presenter;

    public LibraryAdapter(List<RevisionLibrary> libraries, LibraryListPresenter presenter) {
        this.libraries = libraries;
        this.presenter = presenter;
    }
    @NonNull
    @Override
    public LibraryAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_row, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapter.MainViewHolder holder, int position) {
        presenter.setTitles(holder, position, libraries);
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.library_text_view)
        public
        TextView textView;
        @BindView(R.id.library_arrow)
        public
        ImageView imageView;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
