package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.ChatMessage;
import com.yahyeet.boardbook.model.repository.IChatMessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatMessageHandler {

    private IChatMessageRepository chatMessageRepository;
    private List<ChatMessageHandlerListener> listeners = new ArrayList<>();
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatMessageHandler(IChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    CompletableFuture<ChatMessage> create(ChatMessage chatMessage) {
        return chatMessageRepository.create(chatMessage).thenApply((u) -> {
            addChatMessage(u);

            notifyListenersOnChatMessageAdd(u);

            return u;
        });
    }

    CompletableFuture<ChatMessage> find(String id) {
        ChatMessage chatMessage = findChatMessage(id);

        if (chatMessage == null) {
            return chatMessageRepository.find(id).thenApply((cm -> {
                addChatMessage(cm);

                return cm;
            }));
        }

        return CompletableFuture.completedFuture(chatMessage);
    }


    CompletableFuture<ChatMessage> update(ChatMessage chatMessage) {
        return chatMessageRepository.update(chatMessage).thenApply((u) -> {
            updateChatMessage(u);

            notifyListenersOnChatMessageUpdate(u);

            return u;
        });
    }


    CompletableFuture<Void> remove(ChatMessage chatMessage) {
        return chatMessageRepository.remove(chatMessage).thenApply((v) -> {
            removeChatMessage(chatMessage);

            notifyListenersOnChatMessageRemove(chatMessage);

            return null;
        });
    }

    CompletableFuture<List<ChatMessage>> all() {
        return chatMessageRepository.all();
    }

    public void addListener(ChatMessageHandlerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChatMessageHandlerListener listener) {
        listeners.remove(listener);
    }

    private void notifyListenersOnChatMessageAdd(ChatMessage chatMessage) {
        for (ChatMessageHandlerListener listener : listeners) {
            listener.onAddChatMessage(chatMessage);
        }
    }

    private void notifyListenersOnChatMessageUpdate(ChatMessage chatMessage) {
        for (ChatMessageHandlerListener listener : listeners) {
            listener.onUpdateChatMessage(chatMessage);
        }
    }

    private void notifyListenersOnChatMessageRemove(ChatMessage chatMessage) {
        for (ChatMessageHandlerListener listener : listeners) {
            listener.onRemoveChatMessage(chatMessage);
        }
    }

    private ChatMessage findChatMessage(String id) {
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.getId().equals(id)) {
                return chatMessage;
            }
        }

        return null;
    }

    private void addChatMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }

    private void updateChatMessage(ChatMessage chatMessage) {
        for (int index = 0; index < chatMessages.size(); ++index) {
            if (chatMessage.getId().equals(chatMessages.get(index).getId())) {
                chatMessages.set(index, chatMessage);
            }
        }
    }

    private void removeChatMessage(ChatMessage chatMessage) {
        for (int index = 0; index < chatMessages.size(); ++index) {
            if (chatMessage.getId().equals(chatMessages.get(index).getId())) {
                chatMessages.remove(index);
                return;
            }
        }
    }
}
