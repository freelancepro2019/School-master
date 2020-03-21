package com.softray_solutions.newschoolproject.adapters;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyOwnViewHolder> {
    private List<DrawerItem> items;
    private Map<Class<? extends DrawerItem>, Integer> viewTypes;
    private SparseArray<DrawerItem> holderFactories;

    private OnItemSelectedListener listener;

    public DrawerAdapter(List<DrawerItem> items) {
        this.items = items;
        this.viewTypes = new HashMap<>();
        this.holderFactories = new SparseArray<>();

        processViewTypes();
    }

    @NonNull
    @Override
    public MyOwnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyOwnViewHolder holder = holderFactories.get(viewType).createViewHolder(parent);
        holder.adapter = this;

        return holder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull MyOwnViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    private void processViewTypes() {
        int type = 0;
        for (DrawerItem item : items) {
            if (!viewTypes.containsKey(item.getClass())) {
                viewTypes.put(item.getClass(), type);
                holderFactories.put(type, item);
                type++;
            }
        }
    }

    public void setSelected(int position) {
        DrawerItem newChecked = items.get(position);
        if (!newChecked.isSelectable()) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            DrawerItem item = items.get(i);
            if (item.isChecked()) {
                item.setChecked(false);
                notifyItemChanged(i);
                break;
            }
        }

        newChecked.setChecked(true);
        notifyItemChanged(position);

        if (listener != null) {
            listener.onItemSelected(position);
        }
    }

    public void setCheckedItem(int position) {
        unSelectAll();

        DrawerItem item = items.get(position);

        item.setSelectable(false);

        item.setChecked(true);
        notifyDataSetChanged();
    }

    public void unSelectAll() {
        for (int i = 0; i < items.size(); i++) {
            DrawerItem item = items.get(i);

            item.setSelectable(true);
            item.setChecked(false);
        }
        notifyDataSetChanged();
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    static abstract class MyOwnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DrawerAdapter adapter;

        public MyOwnViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            DrawerItem item = adapter.items.get(getAdapterPosition());
            if (item.isSelectable()) {
                adapter.setSelected(getAdapterPosition());
            }
        }
    }

}
