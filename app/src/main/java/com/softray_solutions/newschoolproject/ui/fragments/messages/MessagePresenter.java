package com.softray_solutions.newschoolproject.ui.fragments.messages;

import android.content.Context;
import android.util.Log;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.RoomModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagePresenter {

    private MessagesView listener;
    private Context context;
    private String user_id;

    public MessagePresenter(MessagesView messagesView, Context context,String user_id) {
        this.listener = messagesView;
        this.context = context;
        this.user_id = user_id;
    }

    public void getRoomData(String filter_by)
    {
        if (filter_by.equals("All"))
        {
            getAllRoom();
        }else
            {
                getAllRoomFilter(filter_by);
            }
    }


    private void getAllRoom()
    {
        listener.showProgressDialog();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getRoomData(user_id)
                .enqueue(new Callback<RoomModel>() {
                    @Override
                    public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {
                        listener.hideProgressDialog();
                        if (response.isSuccessful())
                        {
                            if (response.body()!=null&&response.body().getRecords()!=null)
                            {
                                if (response.body().getRecords().size()>0)
                                {
                                    listener.getRoomsData(response.body());
                                    listener.hideNoConversation();

                                }else
                                {
                                    listener.showNoConversation();

                                }
                            }else
                            {
                                listener.showNoConversation();
                            }

                        }else
                        {
                            if (response.code()==500)
                            {
                                listener.error("Server Error");

                            }else
                            {
                                listener.error(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<RoomModel> call, Throwable t) {
                        try {
                            listener.hideProgressDialog();

                            if (t.getMessage()!=null)
                            {
                                Log.e("msg_pres_error",t.getMessage()+"__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    listener.error(context.getString(R.string.check_internet));
                                } else {
                                    listener.error(context.getString(R.string.failed));

                                }
                            }
                        }catch (Exception e)
                        {

                        }


                    }
                });
    }


    private void getAllRoomFilter(String filter_by)
    {

        listener.showProgressDialog();

        MyInterface myInterface = Common.getMyInterface();
        myInterface.getRoomDataFilterBy(user_id,filter_by)
                .enqueue(new Callback<RoomModel>() {
                    @Override
                    public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {
                        listener.hideProgressDialog();
                        if (response.isSuccessful())
                        {
                            if (response.body()!=null&&response.body().getRecords()!=null)
                            {
                                if (response.body().getRecords().size()>0)
                                {
                                    listener.getRoomsData(response.body());
                                    listener.hideNoConversation();

                                }else
                                {
                                    listener.showNoConversation();

                                }
                            }else
                            {
                                listener.showNoConversation();
                            }

                        }else
                        {
                            if (response.code()==500)
                            {
                                listener.error("Server Error");

                            }else
                            {
                                listener.error(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<RoomModel> call, Throwable t) {
                        try {
                            listener.hideProgressDialog();

                            if (t.getMessage()!=null)
                            {
                                Log.e("msg_pres_error",t.getMessage()+"__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    listener.error(context.getString(R.string.check_internet));
                                } else {
                                    listener.error(context.getString(R.string.failed));

                                }
                            }
                        }catch (Exception e)
                        {

                        }


                    }
                });
    }
}
