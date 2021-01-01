import chat.View;

import java.sql.SQLException;

/**
 * Lanzador de la aplicación. Esta clase no debe ser modificada.
 */
public class Main {

    public static void main (String [] args) {
        View view = new View();
        try {
            view.show();
        } catch(SQLException e) {
            System.err.println("Se ha producido un error en la conexión con la base de datos");
            e.printStackTrace();
        }
    }
}
