package lab10.Client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    private Socket socket = null;
    private PrintWriter clientOutput = null;

    private TextField user = null;
    private TextField message = null;

    @Override
    public void start(Stage primaryStage) {

        try{
            socket = new Socket("127.0.0.1", 1024);
            clientOutput = new PrintWriter(socket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }


        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.setHgap(12);
        root.setVgap(12);
        root.setPadding(new Insets(25, 25, 25, 25));

        // Label
        Label name = new Label("Username:");
        Label text = new Label("Message:");

        // Textfield
        user = new TextField();
        message = new TextField();
        user.setPrefWidth(170);
        message.setPrefWidth(170);

        // Button
        Button send = new Button("Send");
        Button exit = new Button("Exit");

        send.setOnAction(e -> {
            clientOutput.println(user.getText() + ": " +message.getText());
            clientOutput.flush();
            message.clear();
        });

        exit.setOnAction(e -> {
            try {
                socket.close();
                clientOutput.close();
            } catch(IOException c) {
                c.printStackTrace();
            }
            System.exit(0);
        });

        root.add(name, 0, 0);
        root.add(text, 0, 1);

        root.add(user, 1, 0);
        root.add(message, 1, 1);

        root.add(send, 0, 2);
        root.add(exit, 0, 3);

        primaryStage.setTitle("SimpleBBS Client v1.0");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
