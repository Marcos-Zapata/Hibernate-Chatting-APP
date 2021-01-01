package chat;

import chat.model.ChatRoom;
import chat.model.Message;
import chat.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Implementa la interfaz gráfica basada en línea de comandos de la aplicación. Esta clase no
 * debe ser modificada.
 */
public class View {

    private Controller controller;

    private User user;

    public View () {
    }

    public void show() throws SQLException {
        this.showLoginMenu();
    }

    private void showLoginMenu() throws SQLException {
        System.out.println("\nBienvenido a nuestro chat\nPor favor, ingresa tu nombre de usuario: ");

        String username;

        do {
            Scanner s = new Scanner(System.in);
            username = s.nextLine().trim();
        } while (username.length() == 0);

        this.controller = new Controller();

        this.user = controller.createUser(username);

        this.showMainMenu();
    }

    private void showMainMenu() throws SQLException {

        int option;

        do {
            System.out.println("\n¡Hola " + user.getUsername() + "!\nElija una acción:\n [1] Acceder a una sala de chat\n [2] Crear una sala de chat\n [3] Salir");

            Scanner s = new Scanner(System.in);
            String line = s.nextLine().trim();

            try {
                option = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                option = 0;
            }
        } while (option < 1 || option > 3);

        switch (option) {
            case 1:  this.showAccessChatRoomMenu();
                     break;

            case 2:  this.showCreateChatRoomMenu();
                     break;

            default: System.out.println("\n¡Hasta pronto!");
        }

    }

    private void showCreateChatRoomMenu() throws SQLException {
        String name;

        do {
            System.out.println("\nEscriba el nombre de la sala de chat:");

            Scanner s = new Scanner(System.in);
            name = s.nextLine().trim();
        } while (name.length() == 0);

        controller.createChatRoom(this.user, name);

        this.showMainMenu();
    }

    private void showAccessChatRoomMenu() throws SQLException {

        int option;

        List<ChatRoom> chatRooms = controller.getChatRooms();

        do {
            System.out.print("\nElija la sala de chat:");

            for (int i = 0; i < chatRooms.size(); i++) {
                System.out.print("\n [" + (i+1) + "] " + chatRooms.get(i).getName());
            }

            System.out.println("\n [" + (chatRooms.size()+1) + "] Volver");

            Scanner s = new Scanner(System.in);
            String line = s.nextLine().trim();

            try {
                option = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                option = 0;
            }
        } while (option < 0 || option > (chatRooms.size() + 1));

        if (option == (chatRooms.size()+1)) {
            this.showMainMenu();

        } else {
            ChatRoom chatRoom = chatRooms.get(option - 1);
            this.showChatRoomMenu(chatRoom);
        }
    }

    private void showChatRoomMenu(ChatRoom chatRoom) throws SQLException {
        int option;

        do {
            System.out.println("\nElija una acción:\n [1] Leer mensajes\n [2] Enviar un mensaje\n [3] Borrar tus mensajes\n [4] Volver");

            Scanner s = new Scanner(System.in);
            String line = s.nextLine().trim();

            try {
                option = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                option = 0;
            }
        } while (option < 1 || option > 4);

        switch (option) {
            case 1:  this.showChatRoomMessages(chatRoom);
                     break;

            case 2:  this.showSendMessageMenu(chatRoom);
                     break;

            case 3: this.deleteSentMessages(chatRoom);
                    break;

            default: this.showMainMenu();
        }
    }

    private void showChatRoomMessages(ChatRoom chatRoom) throws SQLException {
        System.out.println("\nMensajes de la sala de chat:");

        if (chatRoom.getMessages().size() == 0) {
            System.out.println("\nNo se han encontrado mensajes en esta sala de chat");
        } else {
            for (Message message : chatRoom.getMessages()) {
                System.out.print("\n" + message.toString());
            }
        }

        System.out.println("\n\nPresione ENTER para continuar");

        Scanner s = new Scanner(System.in);
        s.nextLine();

        this.showChatRoomMenu(chatRoom);
    }

    private void showSendMessageMenu(ChatRoom chatRoom) throws SQLException {

        String text;

        do {
            System.out.println("\nEscriba el contenido del mensaje:");

            Scanner s = new Scanner(System.in);
            text = s.nextLine().trim();
        } while (text.length() == 0);

        controller.sendMessage(user, chatRoom, text);

        this.showChatRoomMenu(chatRoom);
    }

    private void deleteSentMessages(ChatRoom chatRoom) throws SQLException {
        int msg_borrados=0;
        System.out.println("Borrando mensajes...");

        msg_borrados=controller.deleteMessages(user, chatRoom);
        if (msg_borrados>0) System.out.println("Se han borrado "+ msg_borrados + " mensajes de "+user.getUsername());
        else System.out.println("No se han encontrado mensajes en este chat de "+user.getUsername());

        this.showChatRoomMenu(chatRoom);
    }
}
