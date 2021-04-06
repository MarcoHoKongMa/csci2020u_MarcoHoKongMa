package lab10.Server;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ServerWindow extends Application {
    protected static TextArea messages;

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.setHgap(12);
        root.setVgap(12);
        root.setPadding(new Insets(25, 25, 25, 25));

        // TextArea
        messages = new TextArea();
        messages.setPrefWidth(500);
        messages.setPrefHeight(350);

        // Button
        Button exit = new Button("Exit");

        exit.setOnAction(e -> {
            System.exit(0);
        });

        root.add(messages, 0, 0);
        root.add(exit, 0, 1);

        primaryStage.setTitle("SimpleBBS Server v1.0");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();
    }


    public static void show() {
        launch();
    }
}
