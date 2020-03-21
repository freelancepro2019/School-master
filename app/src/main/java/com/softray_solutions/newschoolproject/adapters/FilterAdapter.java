package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.databinding.FilterRowBinding;
import com.softray_solutions.newschoolproject.model.UserTypeModel;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;
import com.softray_solutions.newschoolproject.ui.fragments.messages.MessagesFragment;

import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserTypeModel> list;
    private Context context;
    private LayoutInflater inflater;
    private MainActivity activity;
    private MessagesFragment fragment;
    private int selected_pos = 0;
    private String lang;


    public FilterAdapter(List<UserTypeModel> list, Context context,MessagesFragment fragment) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (MainActivity) context;
        this.fragment = fragment;
        SharedPreferences userPref = context.getSharedPreferences("userData", MODE_PRIVATE);
        AppPrefsHelper appPrefsHelper = new AppPrefsHelper(userPref);
        if (appPrefsHelper.getSelectedLanguage().equals("not selected"))
        {
            lang = Locale.getDefault().getLanguage();
        }else
            {
                lang = appPrefsHelper.getSelectedLanguage();
            }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        FilterRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.filter_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);
        if (position==selected_pos)
        {
            myHolder.binding.rb.setChecked(true);
        }else
            {
                myHolder.binding.rb.setChecked(false);

            }


        myHolder.itemView.setOnClickListener(view -> {

            UserTypeModel model = list.get(myHolder.getAdapterPosition());
            selected_pos = myHolder.getAdapterPosition();
            fragment.setItemFilterData(selected_pos,model);
            notifyDataSetChanged();

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public FilterRowBinding binding;

        public MyHolder(@NonNull FilterRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void setSelected_pos(int pos)
    {
        this.selected_pos =pos;
        notifyDataSetChanged();
    }




}
