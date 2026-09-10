package socialapp.backend.chats;

import jakarta.persistence.*;
import socialapp.backend.chats.message.Message;
import socialapp.backend.posts.Post;
import socialapp.backend.users.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany
    private List<User> participants;

    private Date date = new Date();

    public Chat(Post post, User initiator, User recipient) {
        this.post = post;
        this.messages = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.participants.add(recipient);
        this.participants.add(initiator);
    }

    protected Chat() { }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Post getPost() {
        return post;
    }
}
