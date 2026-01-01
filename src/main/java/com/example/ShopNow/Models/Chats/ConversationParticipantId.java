package com.example.ShopNow.Models.Chats;

import java.io.Serializable;
import java.util.Objects;

// Composite ID class
public class ConversationParticipantId implements Serializable {
    private int conversation;
    private int user;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConversationParticipantId that = (ConversationParticipantId) o;
        return Objects.equals(conversation, that.conversation) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversation, user);
    }
// equals and hashCode...
}
