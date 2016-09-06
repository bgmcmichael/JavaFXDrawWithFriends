package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by fenji on 8/25/2016.
 */
public class Client implements Runnable {
    public Socket socket = null;
    public String serverAddress = "";
    public String jsonStroke = "";
    public boolean messageSent = false;
    PrintWriter out = null;
    BufferedReader in = null;
    Serializer serializer = new Serializer();

    public void run() {
        String userName = "";
        String homeIp = "";
        Scanner scan = new Scanner(System.in);
        try {
            // connect to the server on the target port
            System.out.println("what ip do you want to connect to?");
            homeIp = scan.nextLine();
            Socket client = new Socket(homeIp, 8005);

            // once we connect to the server, we also have an input and output stream
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // send the server initial message
            out.println("initmessage");
            // read what the server returns
            String serverResponse = in.readLine();
            System.out.println(serverResponse);
//            while (true) {
//                String output = scan.nextLine();
//                if (output.equalsIgnoreCase("exitprogram")) {
//                    break;
//                }
//                out.println(output);
//                serverResponse = in.readLine();
//                System.out.println(serverResponse);
//            }

            // close the connection
//            client.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    public void sendStroke (Stroke stroke){
        out.println(serializer.jsonSerialize(stroke));
        try {
            in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
