package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientRunner extends Application implements Runnable {
    final double DEFAULT_SCENE_WIDTH = 800;
    final double DEFAULT_SCENE_HEIGHT = 600;
    boolean hasDBeenPressed = false;
    Stroke stroke = new Stroke();
    Color randColor = new Color(Math.random(), Math.random(), Math.random(), 1);
    StrokeContainer sc = new StrokeContainer();
    GraphicsContext gc2 = null;
    Scanner scan = new Scanner(System.in);
    Client client = null;
    String serverIp = "";
    Thread clientThread = null;



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Client");



        // we're using a grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(true);

        // add buttons and canvas to the grid
        Text sceneTitle = new Text("Welcome to Paint application");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0);

        Button button = new Button("Sample paint button");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("I can switch to another scene here ...");
                client = new Client();
                clientThread = new Thread(client);
                clientThread.start();
//                primaryStage.setScene(loginScene);
//                startSecondStage();
            }
        });

        // add canvas
        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);

        GraphicsContext gc1 = canvas.getGraphicsContext2D();

        gc1.setFill(randColor);
        gc1.setStroke(randColor);
        gc1.setLineWidth(5);

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
//                System.out.println("x: " + e.getX() + ", y: " + e.getY());
                stroke.x = e.getX();
                stroke.y = e.getY();
                if (hasDBeenPressed) {
                    gc1.fillOval(stroke.x, stroke.y, stroke.strokeSize, stroke.strokeSize);
                    client.sendStroke(stroke);
//                    sc.addStroke(stroke);
                }
            }
        });

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.D){
                    hasDBeenPressed = !hasDBeenPressed;
                    System.out.println(e.getText());
                }

                if (e.getCode() == KeyCode.UP){
                    stroke.IncreaseStrokeSize();
                    System.out.println(stroke.strokeSize);
                }

                if (e.getCode() == KeyCode.DOWN){
                    stroke.DecreaseStrokeSoze();
                    System.out.println(stroke.strokeSize);
                }
            }
        });


        grid.add(canvas, 0, 2);


        //set our grid layout on the scene
        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);



        primaryStage.setScene(defaultScene);
        primaryStage.show();
    }

//    public void startSecondStage() {
//        Stage secondaryStage = new Stage();
//        secondaryStage.setTitle("Welcome to JavaFX");
//
//        // we're using a grid layout
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//        grid.setGridLinesVisible(false);
////        grid.setPrefSize(primaryStage.getMaxWidth(), primaryStage.getMaxHeight());
//
//        // add buttons and canvas to the grid
//        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);
//        gc2 = canvas.getGraphicsContext2D();
//        gc2.setFill(randColor);
//        gc2.setStroke(randColor);
//        gc2.setLineWidth(5);
//        grid.add(canvas, 0, 1);
//
//        Text sceneTitle = new Text("Welcome to Paint application");
//        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(sceneTitle, 0, 0);
//
//        Button button = new Button("Sample paint button");
//        HBox hbButton = new HBox(10);
//        hbButton.setAlignment(Pos.TOP_LEFT);
//        hbButton.getChildren().add(button);
//        grid.add(hbButton, 0, 1);
//
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                System.out.println("I can switch to another scene here ...");
//            }
//        });
//
//        // set our grid layout on the scene
//        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);
//
//        secondaryStage.setScene(defaultScene);
//        System.out.println("About to show the second stage");
//
//        secondaryStage.show();
//    }
//
    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void run() {

    }
}
