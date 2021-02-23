package lab04;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 04");

        // Grid
        GridPane regist = new GridPane();
        regist.setAlignment(Pos.TOP_LEFT);
        regist.setHgap(10);
        regist.setVgap(10);
        regist.setPadding(new Insets(25, 25, 25, 25));

        // Scene
        Scene registWin = new Scene(regist, 852, 480);
        primaryStage.setScene(registWin);

        // Label
        Label OutUser = new Label("Username:");
        Label OutPass = new Label("Password:");
        Label OutFname = new Label("Full Name:");
        Label OutEmail = new Label("E-Mail");
        Label OutPhone = new Label("Phone #:");
        Label OutDate = new Label("Date of Birth:");

        // Inputs
        TextField InUser = new TextField();
        PasswordField InPass = new PasswordField();
        TextField InFname = new TextField();
        TextField InEmail = new TextField();
        TextField InPhone = new TextField();
        DatePicker InDate = new DatePicker();

        // Button
        Button reg = new Button("Register");
        HBox regBtn = new HBox(10);
        regBtn.setAlignment(Pos.TOP_LEFT);
        regBtn.getChildren().add(reg);

        // Insertion UI
        regist.add(OutUser, 0, 1);
        regist.add(InUser, 1, 1);
        regist.add(OutPass, 0, 2);
        regist.add(InPass, 1, 2);
        regist.add(OutFname, 0, 3);
        regist.add(InFname, 1, 3);
        regist.add(OutEmail, 0, 4);
        regist.add(InEmail, 1, 4);
        regist.add(OutPhone, 0, 5);
        regist.add(InPhone, 1, 5);
        regist.add(OutDate, 0, 6);
        regist.add(InDate, 1, 6);
        regist.add(regBtn, 1, 7);

        // Register Event
        reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Full Name: " + InFname.getCharacters());
                System.out.println("E-Mail: " + InEmail.getCharacters());
                System.out.println("Phone #: " + InPhone.getCharacters());
                System.out.println("Date of Birth: " + InDate.getValue());
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
