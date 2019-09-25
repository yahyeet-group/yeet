package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.ChatMessage;

public interface ChatMessageHandlerListener {
    void onAddChatMessage(ChatMessage chatMessage);

    void onUpdateChatMessage(ChatMessage chatMessage);

    void onRemoveChatMessage(ChatMessage chatMessage);
}
