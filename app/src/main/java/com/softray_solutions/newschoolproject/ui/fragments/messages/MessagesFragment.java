package com.softray_solutions.newschoolproject.ui.fragments.messages;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.FilterAdapter;
import com.softray_solutions.newschoolproject.adapters.RoomAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.databinding.DialogFilterBinding;
import com.softray_solutions.newschoolproject.databinding.FragmentMessagesBinding;
import com.softray_solutions.newschoolproject.model.ChatUserModel;
import com.softray_solutions.newschoolproject.model.RoomModel;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.model.UserTypeModel;
import com.softray_solutions.newschoolproject.ui.activities.activity_chat.ChatActivity;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class MessagesFragment extends Fragment implements MessagesView{
    private SharedPreferences preferences;
    private User user;
    private FragmentMessagesBinding binding;
    private MainActivity activity;
    private int selected_pos_filter =0;
    private String filter_by = "All";
    private AlertDialog dialog;
    private int selected_room_pos = -1;
    private RoomAdapter adapter;
    private List<RoomModel.Records> recordsList;
    private MessagePresenter presenter;
    private LinearLayoutManager manager;
    private FilterAdapter filterAdapter;
    private List<UserTypeModel> userTypeModelList;
    private AppPrefsHelper appPrefsHelper;
    private String lang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_messages,container,false);
        initView();
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("user",user.getId()+"_");
        userTypeModelList = new ArrayList<>();
        Picasso.get().load(Uri.parse(user.getImg())).placeholder(R.drawable.default_avatar).fit().into(binding.image);
        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        adapter = new RoomAdapter(recordsList,activity,this,user.getId());
        binding.recView.setAdapter(adapter);
        presenter = new MessagePresenter(this,activity,user.getId());
        presenter.getRoomData(filter_by);
        binding.refreshLayout.setOnRefreshListener(() -> presenter.getRoomData(filter_by));

        addUsersType();
        filterAdapter = new FilterAdapter(userTypeModelList,activity,this);
        binding.llFilter.setOnClickListener(view1 -> createFilterDialogAlert());

    }

    private void addUsersType() {
        userTypeModelList.add(new UserTypeModel("الكل","All","All"));

        if (user.getType().equals(Common.STUDENT))
        {
            userTypeModelList.add(new UserTypeModel("الإدارة","Admin","U"));
            userTypeModelList.add(new UserTypeModel("معلمين","Staff","E"));
            userTypeModelList.add(new UserTypeModel("طلاب","Student","S"));

        }

        if (user.getType().equals(Common.FATHER))
        {
            userTypeModelList.add(new UserTypeModel("الإدارة","Admin","U"));
            userTypeModelList.add(new UserTypeModel("معلمين","Staff","E"));

        }

        if (user.getType().equals(Common.TEACHER))
        {
            userTypeModelList.add(new UserTypeModel("الإدارة","Admin","U"));
            userTypeModelList.add(new UserTypeModel("معلمين","Staff","E"));
            userTypeModelList.add(new UserTypeModel("طلاب","Students","S"));
            userTypeModelList.add(new UserTypeModel("أولياء أمور","Parents","F"));

        }


    }

    private void initView() {
        recordsList = new ArrayList<>();
        activity = (MainActivity) getActivity();
        preferences = activity.getSharedPreferences("userData", MODE_PRIVATE);
        appPrefsHelper = new AppPrefsHelper(preferences);
        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
        user = gson.fromJson(userString, User.class);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        binding.refreshLayout.setColorSchemeResources(R.color.colorAccent);

        if (appPrefsHelper.getSelectedLanguage().equals("not selected"))
        {
            lang = Locale.getDefault().getLanguage();
        }else
        {
            lang = appPrefsHelper.getSelectedLanguage();
        }

    }

    private void createFilterDialogAlert() {
        dialog = new AlertDialog.Builder(activity)
                .create();

        DialogFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_filter, null, false);
        binding.recViewFilter.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewFilter.setAdapter(filterAdapter);
        if (filterAdapter!=null)
        {
            filterAdapter.setSelected_pos(selected_pos_filter);
        }


        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }


    public void setItemFilterData(int selected_pos, UserTypeModel model)
    {
        dialog.dismiss();

        this.selected_pos_filter = selected_pos;
        filter_by = model.getType();
        if (lang.equals("ar"))
        {
            binding.tvFilter.setText(model.getAr_name());

        }else
            {
                binding.tvFilter.setText(model.getEn_name());

            }
        presenter.getRoomData(filter_by);


    }

    public void setItemRoomData(RoomModel.Records model, int adapterPosition) {

        this.selected_room_pos =adapterPosition;

        ChatUserModel chatUserModel;

        if (model.getFrom_user().equals(user.getId())) {
            chatUserModel = new ChatUserModel(model.getTo_user(), model.getTo_userName(), "", model.getChat_id(), model.getConversation_id());

        } else {
            chatUserModel = new ChatUserModel(model.getFrom_user(), model.getFrom_userName(), "", model.getChat_id(), model.getConversation_id());

        }
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra("data",chatUserModel);
        startActivityForResult(intent,100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK)
        {
            if (data != null && data.hasExtra("refresh")) {
                boolean refresh = data.getBooleanExtra("refresh", false);
                if (refresh) {
                    presenter.getRoomData(filter_by);
                }
            }

            if (selected_room_pos!=-1&&recordsList.size()>0)
            {
                RoomModel.Records records = recordsList.get(selected_room_pos);
                records.setRead("1");
                recordsList.set(selected_room_pos,records);
                adapter.notifyItemChanged(selected_room_pos);
                selected_room_pos = -1;


            }
        }
    }

    @Override
    public void showProgressDialog() {
        binding.tvNoConversation.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
        recordsList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgressDialog() {
        binding.progBar.setVisibility(View.GONE);
        binding.refreshLayout.setRefreshing(false);

    }

    @Override
    public void showNoConversation() {
        binding.tvNoConversation.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConversation() {
        binding.tvNoConversation.setVisibility(View.GONE);

    }

    @Override
    public void getRoomsData(RoomModel roomModel) {
        recordsList.clear();

        if (roomModel!=null&&roomModel.getRecords()!=null)
        {
            recordsList.addAll(roomModel.getRecords());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void error(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.refreshLayout.setRefreshing(false);

    }


}
