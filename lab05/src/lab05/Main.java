package lab05;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TableView<StudentRecord> record = new TableView<>();

        // Columns
        TableColumn<StudentRecord, String> idColumn = new TableColumn<>("SID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        TableColumn<StudentRecord, Float> assignColumn = new TableColumn<>("Assignments");
        assignColumn.setMinWidth(100);
        assignColumn.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        TableColumn<StudentRecord, Float> midColumn = new TableColumn<>("Midterm");
        midColumn.setMinWidth(100);
        midColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        TableColumn<StudentRecord, Float> examColumn = new TableColumn<>("Final Exam");
        examColumn.setMinWidth(100);
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        TableColumn<StudentRecord, Float> markColumn = new TableColumn<>("Final Mark");
        markColumn.setMinWidth(100);
        markColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        TableColumn<StudentRecord, Character> letterColumn = new TableColumn<>("Letter Grade");
        letterColumn.setMinWidth(100);
        letterColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        record.setItems(DataSource.getAllMarks());
        record.getColumns().addAll(idColumn, assignColumn, midColumn, examColumn, markColumn, letterColumn);

        VBox report = new VBox();
        report.getChildren().addAll(record);

        Scene recordWin = new Scene(report);
        primaryStage.setTitle("Lab 05");
        primaryStage.setScene(recordWin);
        primaryStage.show();
    }


    public static void main(String[] args) { launch(args); }
}
