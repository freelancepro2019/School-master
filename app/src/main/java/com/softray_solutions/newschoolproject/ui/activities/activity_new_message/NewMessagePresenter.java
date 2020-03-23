package com.softray_solutions.newschoolproject.ui.activities.activity_new_message;

import android.content.Context;
import android.util.Log;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.CategoryModel;
import com.softray_solutions.newschoolproject.model.UserCategoryModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMessagePresenter {

    private Context context;
    private NewMessageView listener;

    public NewMessagePresenter(Context context, NewMessageView listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getCategoryByCurrentUserType(String type)
    {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getCategory(type)
                .enqueue(new Callback<List<CategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                        if (response.isSuccessful() && response.body() != null ) {
                            listener.onCategorySuccess(response.body());
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                        try {
                            listener.hideProgress();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

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

    public void getUsersByCategory(String user_id,String school_id,String lang,String category)
    {
        listener.showProgress();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getCategoryUsers(user_id, school_id, lang, category)
                .enqueue(new Callback<List<UserCategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<UserCategoryModel>> call, Response<List<UserCategoryModel>> response) {
                        listener.hideProgress();
                        if (response.isSuccessful() && response.body() != null ) {
                            listener.onUserByCategorySuccess(response.body());
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<UserCategoryModel>> call, Throwable t) {
                        try {
                            listener.hideProgress();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

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

    public void sendFirstMessage(String user_id,String school_id,String category,List<String> toUsers,String message,String file)
    {
        Log.e("message",message+"_att"+file);
        if (!file.isEmpty()&&!message.isEmpty())
        {
            sendFirstMessageFile(user_id,school_id,category,toUsers,message,file);
        }else if(!message.isEmpty())
        {
            sendFirstText(user_id,school_id,category,toUsers,message);
        }else if(!file.isEmpty())
        {
            sendFirstFile(user_id,school_id,category,toUsers,file);

        }
    }

    private void sendFirstFile(String user_id, String school_id, String category, List<String> toUsers, String file) {

        RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody school_id_part = Common.getRequestBodyText(school_id);
        RequestBody category_part = Common.getRequestBodyText(category);
        List<RequestBody> requestBodyList = getResponseBodyList(toUsers);
        MultipartBody.Part file_part = Common.getMultiPartFile(context, file, "attachment");

        listener.showProgress();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendFirstFile(user_id_part, school_id_part,requestBodyList, category_part,file_part)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        listener.hideProgress();
                        if (response.isSuccessful() && response.body() != null ) {
                            listener.onMessageSentSuccess();
                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            listener.hideProgress();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

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

    private void sendFirstText(String user_id, String school_id, String category, List<String> toUsers, String message) {

        RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody school_id_part = Common.getRequestBodyText(school_id);
        RequestBody category_part = Common.getRequestBodyText(category);
        RequestBody message_part = Common.getRequestBodyText(message);
        List<RequestBody> requestBodyList = getResponseBodyList(toUsers);

        listener.showProgress();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendFirstText(user_id_part, school_id_part,requestBodyList, category_part,message_part)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        listener.hideProgress();
                        if (response.isSuccessful() && response.body() != null ) {
                            listener.onMessageSentSuccess();

                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            listener.hideProgress();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

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

    private void sendFirstMessageFile(String user_id, String school_id, String category, List<String> toUsers, String message, String file) {

        RequestBody user_id_part = Common.getRequestBodyText(user_id);
        RequestBody school_id_part = Common.getRequestBodyText(school_id);
        RequestBody category_part = Common.getRequestBodyText(category);
        RequestBody message_part = Common.getRequestBodyText(message);
        List<RequestBody> requestBodyList = getResponseBodyList(toUsers);
        MultipartBody.Part file_part = Common.getMultiPartFile(context, file, "attachment");

        listener.showProgress();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.sendFirstTextFile(user_id_part, school_id_part,requestBodyList, category_part,message_part,file_part)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        listener.hideProgress();
                        if (response.isSuccessful() && response.body() != null ) {
                            listener.onMessageSentSuccess();

                        } else {
                            if (response.code() == 500) {
                                listener.onError("Server Error");

                            } else {
                                listener.onError(context.getString(R.string.failed));

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            listener.hideProgress();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

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

    private List<RequestBody> getResponseBodyList(List<String> ids)
    {
        List<RequestBody> requestBodyList = new ArrayList<>();
        for (String id :ids)
        {
            RequestBody requestBody = Common.getRequestBodyText(id);
            requestBodyList.add(requestBody);
        }

        return requestBodyList;
    }
}
