package socialapp.backend.chats.message;


import jakarta.persistence.*;
import socialapp.backend.chats.Chat;
import socialapp.backend.posts.Post;
import socialapp.backend.users.User;

import java.util.Date;
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

    private Date date = new Date();

    public Message(String content, User sender, Chat chat) {
        this.content = content;
        this.sender = sender;
        this.chat = chat;
    }

    public UUID getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}

