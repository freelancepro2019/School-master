package com.softray_solutions.newschoolproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.databinding.SpinnerRowBinding;
import com.softray_solutions.newschoolproject.model.CategoryModel;

import java.util.List;

public class SpinnerCategoryAdapter extends BaseAdapter {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    public SpinnerCategoryAdapter(List<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_row,viewGroup,false);
        CategoryModel model = list.get(i);
        binding.setName(model.getName());
        return binding.getRoot();
    }
}
