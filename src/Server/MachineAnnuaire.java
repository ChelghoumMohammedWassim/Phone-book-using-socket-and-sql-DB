package Server;

import java.net.ServerSocket;
import java.net.SocketException;
import java.sql.SQLException;

public class MachineAnnuaire {
    public static void main(String[] args) throws SQLException {
        //System.out.println(new DBHelper().getPhoneNumberof("Chelghoum Mohammed Ouassim"));
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Witing for Client:");
            while (true) {
                ThreadedSocket th = new ThreadedSocket(serverSocket.accept());
                System.out.println("Client connected");
                th.start();
            }
        } catch(SocketException se){
            System.out.println(se);
        } catch(Exception e){
            System.out.println(e);
        }
    }

}

