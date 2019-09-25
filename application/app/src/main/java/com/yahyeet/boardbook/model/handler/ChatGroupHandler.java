package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.repository.IChatGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatGroupHandler {

    private IChatGroupRepository chatGroupRepository;
    private List<ChatGroupHandlerListener> listeners = new ArrayList<>();
    private List<ChatGroup> chatGroups = new ArrayList<>();

    public ChatGroupHandler(IChatGroupRepository chatGroupRepository) {
        this.chatGroupRepository = chatGroupRepository;
    }

    CompletableFuture<ChatGroup> create(ChatGroup chatGroup) {
        return chatGroupRepository.create(chatGroup).thenApply((u) -> {
            addChatGroup(u);

            notifyListenersOnChatGroupAdd(u);

            return u;
        });
    }

    CompletableFuture<ChatGroup> find(String id) {
        ChatGroup chatGroup = findChatGroup(id);

        if (chatGroup == null) {
            return chatGroupRepository.find(id).thenApply((cg -> {
                addChatGroup(cg);

                return cg;
            }));
        }

        return CompletableFuture.completedFuture(chatGroup);
    }


    CompletableFuture<ChatGroup> update(ChatGroup chatGroup) {
        return chatGroupRepository.update(chatGroup).thenApply((u) -> {
            updateChatGroup(u);

            notifyListenersOnChatGroupUpdate(u);

            return u;
        });
    }


    CompletableFuture<Void> remove(ChatGroup chatGroup) {
        return chatGroupRepository.remove(chatGroup).thenApply((v) -> {
            removeChatGroup(chatGroup);

            notifyListenersOnChatGroupRemove(chatGroup);

            return null;
        });
    }

    CompletableFuture<List<ChatGroup>> all() {
        return chatGroupRepository.all();
    }

    public void addListener(ChatGroupHandlerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChatGroupHandlerListener listener) {
        listeners.remove(listener);
    }

    private void notifyListenersOnChatGroupAdd(ChatGroup chatGroup) {
        for (ChatGroupHandlerListener listener : listeners) {
            listener.onAddChatGroup(chatGroup);
        }
    }

    private void notifyListenersOnChatGroupUpdate(ChatGroup chatGroup) {
        for (ChatGroupHandlerListener listener : listeners) {
            listener.onUpdateChatGroup(chatGroup);
        }
    }

    private void notifyListenersOnChatGroupRemove(ChatGroup chatGroup) {
        for (ChatGroupHandlerListener listener : listeners) {
            listener.onRemoveChatGroup(chatGroup);
        }
    }

    private ChatGroup findChatGroup(String id) {
        for (ChatGroup chatGroup : chatGroups) {
            if (chatGroup.getId().equals(id)) {
                return chatGroup;
            }
        }

        return null;
    }

    private void addChatGroup(ChatGroup chatGroup) {
        chatGroups.add(chatGroup);
    }

    private void updateChatGroup(ChatGroup chatGroup) {
        for (int index = 0; index < chatGroups.size(); ++index) {
            if (chatGroup.getId().equals(chatGroups.get(index).getId())) {
                chatGroups.set(index, chatGroup);
            }
        }
    }

    private void removeChatGroup(ChatGroup chatGroup) {
        for (int index = 0; index < chatGroups.size(); ++index) {
            if (chatGroup.getId().equals(chatGroups.get(index).getId())) {
                chatGroups.remove(index);
                return;
            }
        }
    }
}
