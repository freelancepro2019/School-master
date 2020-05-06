package com.softray_solutions.newschoolproject.ui.activities.activity_chat;

import com.softray_solutions.newschoolproject.model.MessageDataModel;
import com.softray_solutions.newschoolproject.model.MessageModel;

public interface ChatView {
    void hideProgress();

    void onMessageSendSuccess(MessageModel messageModel);

    void onMessagesSuccess(MessageDataModel messageDataModel);

    void onError(String msg);


}
