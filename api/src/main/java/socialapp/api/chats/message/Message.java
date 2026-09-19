package socialapp.api.chats.message;


import jakarta.persistence.*;
import socialapp.api.chats.Chat;
import socialapp.api.users.User;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;

    private Instant createdAt = Instant.now();

    private MessageState state = MessageState.SENT;

    public Message(String content, User sender, Chat chat) {
        this.content = content;
        this.sender = sender;
        this.chat = chat;
    }

    public enum MessageState {
        SENT,
        READ
    }

    protected Message() {}

    public UUID getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public MessageState getState() {
        return state;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

