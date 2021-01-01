package chat;

import chat.model.ChatRoom;
import chat.model.Message;
import chat.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de la aplicación. Por favor, revise detenidamente la clase y complete las partes omitidas
 * atendiendo a los comentarios indicados mediante @TODO
 */
public class Controller {

    private Session session;

    /**
     * Crea un nuevo controlador
     */
    public Controller () {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        this.session = sessionFactory.openSession();
    }

    /**
     * Crea un nuevo usuario en la aplicación
     * @param nickname nombre de usuario
     * @return el nuevo usuario creado
     * @throws SQLException
     */
    public User createUser(String nickname) throws SQLException {
        // @TODO complete este metodo para crear de forma presistente un usuario
            User usuario = new User(nickname);
            session.beginTransaction();
            session.save(usuario);
            session.getTransaction().commit();
            return usuario;
    }

    /**
     * Crea una nueva sala de chat
     * @param user usuario que crea la sala de chat
     * @param CRname nombre de la sala de chat
     * @return la nueva sala de chat creada
     * @throws SQLException
     */
    public ChatRoom createChatRoom (User user, String CRname) throws SQLException {
        // @TODO complete este metodo para crear de forma presistente una sala de chat
        ChatRoom sala = new ChatRoom(CRname, user);
        session.beginTransaction();
        session.save(sala);
        session.getTransaction().commit();
        return sala;
    }

    /**
     * Crea un nuevo mensaje en una sala de chat
     * @param user usuario que crea el mensaje
     * @param chatRoom sala de chat en la que se crea el mensaje
     * @param content contenido del mensaje
     * @throws SQLException
     */
    public void sendMessage (User user, ChatRoom chatRoom, String content) throws SQLException {
        // @TODO complete este metodo para crear de forma presistente un mensaje
        Message mensaje = new Message(content,chatRoom,user);
        chatRoom.getMessages().add(mensaje);
        session.beginTransaction();
        session.save(mensaje);
        session.getTransaction().commit(); //Error de serialize
    }

    /**
     * Recupera un listado con todas las salas de chat
     * @return listado con las salas de chat
     * @throws SQLException
     */
    public List<ChatRoom> getChatRooms () throws SQLException {
        // @TODO complete este metodo para devolver todas las salas de chat
        Query query = session.createQuery("FROM ChatRoom");
        return query.getResultList();
    }

    /**
     * Borra los mensajes que envió ese usuario al chat
     * @param user usuario que creó los mensajes
     * @param chatRoom sala de chat en la que se crearon los mensajes
     * @return número de mensajes borrados(int)
     * @throws SQLException
     */
    public int deleteMessages(User user, ChatRoom chatRoom) throws SQLException {
        // @TODO complete este metodo para eliminar los mensajes que envió el usuario que tiene la sesión abierta
        // @TODO en el chat seleccionado
        // utilice la función "createQuery" dentro de una transacción
        // dentro del "createQuery" realice la consulta hql con "delete from"
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Message WHERE creator = :creator AND chatRoom = :chatRoom");
        query.setParameter("creator", user);
        query.setParameter("chatRoom", chatRoom);
        int messagesDeleted = query.executeUpdate();
        session.getTransaction().commit();
        return messagesDeleted;
    }
}
