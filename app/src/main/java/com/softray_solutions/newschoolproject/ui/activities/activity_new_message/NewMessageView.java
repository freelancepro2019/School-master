package com.softray_solutions.newschoolproject.ui.activities.activity_new_message;

import com.softray_solutions.newschoolproject.model.CategoryModel;
import com.softray_solutions.newschoolproject.model.UserCategoryModel;

import java.util.List;

public interface NewMessageView {
    void showProgress();
    void hideProgress();
    void onCategorySuccess(List<CategoryModel> categoryModelList);
    void onUserByCategorySuccess(List<UserCategoryModel> userCategoryModelList);
    void onMessageSentSuccess();
    void onError(String error);
}
