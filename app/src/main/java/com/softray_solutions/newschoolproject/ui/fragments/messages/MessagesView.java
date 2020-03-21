package com.softray_solutions.newschoolproject.ui.fragments.messages;

import com.softray_solutions.newschoolproject.model.RoomModel;

public interface MessagesView {

    void showProgressDialog();
    void hideProgressDialog();
    void showNoConversation();
    void hideNoConversation();
    void getRoomsData(RoomModel roomModel);
    void error(String message);
}
