package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.ChatGroup;

public interface ChatGroupHandlerListener {
    void onAddChatGroup(ChatGroup chatGroup);

    void onUpdateChatGroup(ChatGroup chatGroup);

    void onRemoveChatGroup(ChatGroup chatGroup);
}
