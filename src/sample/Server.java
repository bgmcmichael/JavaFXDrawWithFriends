package sample;

import javafx.scene.canvas.GraphicsContext;
import jodd.json.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fenji on 8/25/2016.
 */
public class Server implements Runnable{
    Socket client = null;
    Stroke stroke = null;
    Serializer serializer = new Serializer();
    GraphicsContext gc1 = null;
    RunnableGC runGC = null;


    public  Server(GraphicsContext gc1){
        this.gc1 = gc1;
    }

    public void drawOnCanvas() {

    }

    public void handleClientInput(){
        System.out.println("starting server...");
        try {
            ServerSocket server = new ServerSocket(8005);
            System.out.println("listener is ready to connect.");

            client = server.accept();
            // display information about who just connected to our server

            System.out.println("Incoming connection from " + client.getInetAddress().getHostAddress());

            // this is how we read from the client
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // this is how we write back to the client
            PrintWriter outputToClient = new PrintWriter(client.getOutputStream(), true);
            // read from the input until the client disconnects
            String inputLine;
            inputLine = clientInput.readLine();
            System.out.println("Received from client: " + inputLine);
            outputToClient.println("Message received loud and clear");

            //continue to listen for messages
            while ((inputLine = clientInput.readLine()) != null) {
                System.out.println(inputLine);
                stroke = serializer.jsonDeserialize(inputLine);
                runGC = new RunnableGC(gc1, stroke);
                Thread runGCThread = new Thread(runGC);
                runGCThread.start();
                outputToClient.println("Message received loud and clear");

            }

        }catch (IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    @Override
    public void run() {
        handleClientInput();
    }
}


