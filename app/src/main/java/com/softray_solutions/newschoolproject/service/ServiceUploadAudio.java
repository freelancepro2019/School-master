package com.softray_solutions.newschoolproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.MessageModel;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceUploadAudio extends Service {
    private String file_path;
    private String user_id;
    private String chat_id;
    private String to_user;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        file_path = intent.getStringExtra("path");
        user_id = intent.getStringExtra("user_id");
        chat_id = intent.getStringExtra("chat_id");
        to_user = intent.getStringExtra("to_user");
        uploadAudio();
        return START_STICKY;
    }

    private void uploadAudio() {

         RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody to_user_id_part = Common.getRequestBodyText(to_user);
        RequestBody chat_id_part = Common.getRequestBodyText(chat_id);

        MultipartBody.Part file_part = Common.getMultiPartAudio(file_path, "audio");
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendChatAudioFile(user_id_part, to_user_id_part,chat_id_part, file_part)
                .enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        stopSelf();
                        if (response.isSuccessful() && response.body() != null) {

                            EventBus.getDefault().post(response.body());
                            //listener.onMessageSendSuccess(response.body());
                        } else {
                            MessageModel messageModel = new MessageModel();
                            messageModel.setStatus(false);
                            EventBus.getDefault().post(messageModel);
                            if (response.code() == 500) {

                                Toast.makeText(ServiceUploadAudio.this,"Server Error", Toast.LENGTH_SHORT).show();

                                //listener.onError("Server Error");

                            } else {
                                Toast.makeText(ServiceUploadAudio.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                //listener.onError(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                        try {
                            MessageModel messageModel = new MessageModel();
                            messageModel.setStatus(false);
                            EventBus.getDefault().post(messageModel);
                            stopSelf();

                            if (t.getMessage() != null) {
                                Log.e("msg_chat_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {

                                    Toast.makeText(ServiceUploadAudio.this,getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                                    //listener.onError();
                                } else {
                                    Toast.makeText(ServiceUploadAudio.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    //listener.onError();

                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });

        stopSelf();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
