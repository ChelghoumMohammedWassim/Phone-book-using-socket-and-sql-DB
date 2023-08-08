package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ThreadedSocket extends Thread {
    private Socket clientSoket;

    public ThreadedSocket(Socket client) {
        this.clientSoket = client;
    }

    public void run() {
        DBHelper dbHelper = new DBHelper();

        try {

            BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSoket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSoket.getOutputStream(), true);

            String line;
            boolean exite = false;

            while ((line = buffer.readLine()) != null && !exite) {

                if (line.trim().toUpperCase().equals("/QUIT")) {
                    writer.println("Connection Closed");
                    System.out.println("Client disconnected");
                    exite = true;
                } else if (line.trim().toUpperCase().equals("/ALL")){
                    writer.println(dbHelper.getAll());
                }else {
                    writer.println(dbHelper.getPhoneNumberof(line.trim().toUpperCase()));
                }

            }

            clientSoket.close();

        } catch (SocketException se) {
            System.out.println(se);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}