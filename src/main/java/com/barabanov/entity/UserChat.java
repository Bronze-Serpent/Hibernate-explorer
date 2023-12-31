package com.barabanov.entity;

import com.barabanov.listner.UserChatListener;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "chat"})
@EqualsAndHashCode(exclude = {"user", "chat"}, callSuper = true)
@Entity
@Table(name = "users_chat")
@EntityListeners(UserChatListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserChat extends AuditableDateEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;


    public void setUser(User user)
    {
        this.user = user;
        this.user.getUserChats().add(this);
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
        this.chat.getUserChats().add(this);
    }

}
