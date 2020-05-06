package com.softray_solutions.newschoolproject.ui.activities.activity_chat;

import android.content.Context;
import android.util.Log;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.MessageDataModel;
import com.softray_solutions.newschoolproject.model.MessageModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatPresenter {
    private Context context;
    private ChatView listener;

    public ChatPresenter(Context context, ChatView chatView) {
        this.context = context;
        this.listener = chatView;
    }

    public void sendMessage(String chat_id, String user_id, String to_user_id, String message, String file) {
        if (!message.isEmpty() && !file.isEmpty()) {
            sendMessageFileText(chat_id, user_id, to_user_id, message, file);
        } else if (!message.isEmpty()) {
            sendMessageText(chat_id, user_id, to_user_id, message);
        } else if (!file.isEmpty()) {
            sendFile(chat_id, user_id, to_user_id, file);
        }
    }


    private void sendMessageFileText(String chat_id, String user_id, String to_user_id, String message, String file) {

        RequestBody chat_id_part = Common.getRequestBodyText(chat_id);
        RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody to_user_id_part = Common.getRequestBodyText(to_user_id);
        RequestBody msg_part = Common.getRequestBodyText(message);
        MultipartBody.Part file_part = Common.getMultiPartFile(context, file, "fileUpload");
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendChatTextFile(chat_id_part, user_id_part, to_user_id_part, msg_part, file_part)
                .enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.onMessageSendSuccess(response.body());
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                        try {

                            if (t.getMessage() != null) {
                                Log.e("msg_chat_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    listener.onError(context.getString(R.string.check_internet));
                                } else {
                                    listener.onError(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void sendMessageText(String chat_id, String user_id, String to_user_id, String message) {

        RequestBody chat_id_part = Common.getRequestBodyText(chat_id);
        RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody to_user_id_part = Common.getRequestBodyText(to_user_id);
        RequestBody msg_part = Common.getRequestBodyText(message);
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendChatText(chat_id_part, user_id_part, to_user_id_part, msg_part)
                .enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.onMessageSendSuccess(response.body());
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                        try {

                            if (t.getMessage() != null) {
                                Log.e("msg_pres_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    listener.onError(context.getString(R.string.check_internet));
                                } else {
                                    listener.onError(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void sendFile(String chat_id, String user_id, String to_user_id, String file) {

        RequestBody chat_id_part = Common.getRequestBodyText(chat_id);
        RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody to_user_id_part = Common.getRequestBodyText(to_user_id);
        MultipartBody.Part file_part = Common.getMultiPartFile(context, file, "fileUpload");
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendChatFile(chat_id_part, user_id_part, to_user_id_part, file_part)
                .enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.onMessageSendSuccess(response.body());
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                        try {

                            if (t.getMessage() != null) {
                                Log.e("msg_chat_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    listener.onError(context.getString(R.string.check_internet));
                                } else {
                                    listener.onError(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }

    public void getAllChatMessages(String chat_id, String conversation_id, String user_id, String to_user_id) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getChatMessage(user_id, to_user_id, conversation_id, chat_id)
                .enqueue(new Callback<MessageDataModel>() {
                    @Override
                    public void onResponse(Call<MessageDataModel> call, Response<MessageDataModel> response) {
                        listener.hideProgress();
                        if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null) {
                            listener.onMessagesSuccess(response.body());
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MessageDataModel> call, Throwable t) {
                        try {
                            listener.hideProgress();
                            if (t.getMessage() != null) {
                                Log.e("msg_chat_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    listener.onError(context.getString(R.string.check_internet));
                                } else {
                                    listener.onError(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }

}
