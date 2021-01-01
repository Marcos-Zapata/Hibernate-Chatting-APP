package chat.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Modela un mensaje. Por favor, revise detenidamente la clase y complete las partes omitidas
 * atendiendo a los comentarios indicados mediante @TODO
 */
// @TODO completar las anotaciones de la clase
@Entity
@Table(name = "Message")
public class Message {

    // @TODO completar las anotaciones del atributo id (autogenerado)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @TODO completar las anotaciones del atributo text
    @Column(name = "text", nullable = false)
    private String text;

    // @TODO completar las anotaciones del atributo chatRoom
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "chatRoom")
    private ChatRoom chatRoom;

    // @TODO completar las anotaciones del atributo creator
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator")
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts")
    private Date ts;

    public Message () {

    }

    public Message (String text, ChatRoom chatRoom, User creator) {
        this.text = text;
        this.chatRoom = chatRoom;
        this.creator = creator;
    }

    public String getText() {
        return this.text;
    }

    public User getCreator() {
        return this.creator;
    }

    public ChatRoom getChatRoom() {
        return this.chatRoom;
    }

    public String toString() {
        return "[" + this.creator.getUsername() + "]: " + text;
    }
}
