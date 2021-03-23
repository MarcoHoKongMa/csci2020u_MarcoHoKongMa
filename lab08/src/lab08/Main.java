package lab08;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class Main extends Application {
    private String currentFilename = "saves/Untitled.csv";
    private TableView<StudentRecord> record;

    // Initialize Columns
    private TableColumn<StudentRecord, String> idColumn;
    private TableColumn<StudentRecord, Float> assignColumn;
    private TableColumn<StudentRecord, Float> midColumn;
    private TableColumn<StudentRecord, Float> examColumn;
    private TableColumn<StudentRecord, Float> markColumn;
    private TableColumn<StudentRecord, Character> letterColumn;

    @Override
    public void start(Stage primaryStage) throws Exception{
        record = new TableView<>();

        // Columns
        setColumns();

        record.setItems(DataSource.getAllMarks());

        // Menu
        Menu fileMenu = new Menu("File");

        // Menu Bar
        MenuBar bar = new MenuBar(fileMenu);

        // Menu Items
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem exit = new MenuItem("Exit");

        fileMenu.getItems().add(newFile);
        fileMenu.getItems().add(openFile);
        fileMenu.getItems().add(saveFile);
        fileMenu.getItems().add(saveAs);
        fileMenu.getItems().add(exit);

        // Add New Data Box
        GridPane addData = new GridPane();
        addData.setAlignment(Pos.TOP_LEFT);
        addData.setHgap(10);
        addData.setVgap(10);
        addData.setPadding(new Insets(15, 25, 25, 15));

        Label sid = new Label("SID:");
        Label assignments = new Label("Assignments:");
        Label midterm = new Label("Midterm:");
        Label exam = new Label("Final Exam:");

        TextField textSid = new TextField();
        textSid.setPromptText("SID");
        TextField textAssignments = new TextField();
        textAssignments.setPromptText("Assignments/100");
        TextField textMidterm = new TextField();
        textMidterm.setPromptText("Midterm/100");
        TextField textExam = new TextField();
        textExam.setPromptText("Final Exam/100");

        Button add = new Button("Add");

        addData.add(sid, 0, 0);
        addData.add(assignments, 2, 0);
        addData.add(midterm, 0, 1);
        addData.add(exam, 2, 1);

        addData.add(textSid, 1, 0);
        addData.add(textAssignments, 3, 0);
        addData.add(textMidterm, 1, 1);
        addData.add(textExam, 3, 1);

        addData.add(add, 1, 4);

        // Menu Items Actions
        EventHandler<ActionEvent> newData = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                record = new TableView<>();
                setColumns();
                currentFilename = "saves/Untitled.csv";

                VBox root = new VBox();
                root.getChildren().addAll(bar);
                root.getChildren().addAll(record);
                root.getChildren().addAll(addData);
                primaryStage.setScene(new Scene(root, 602, 600));
            }
        };
        EventHandler<ActionEvent> openData = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Choose File
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("."));
                File loadFile = fileChooser.showOpenDialog(primaryStage);
                currentFilename = loadFile.getAbsolutePath();
                load();

                // Display
                VBox root = new VBox();
                root.getChildren().addAll(bar);
                root.getChildren().addAll(record);
                root.getChildren().addAll(addData);
                primaryStage.setScene(new Scene(root, 602, 600));
            }
        };
        EventHandler<ActionEvent> saveData = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                save();
            }
        };
        EventHandler<ActionEvent> saveAsData = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Choose File & Save
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("."));
                File loadFile = fileChooser.showOpenDialog(primaryStage);
                currentFilename = loadFile.getAbsolutePath();
                save();
            }
        };
        EventHandler<ActionEvent> quit = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        };
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<StudentRecord> marks = FXCollections.observableArrayList();

                for(StudentRecord studentRecord: record.getItems()) {
                    marks.add(new StudentRecord(idColumn.getCellData(studentRecord), assignColumn.getCellData(studentRecord),
                            midColumn.getCellData(studentRecord), examColumn.getCellData(studentRecord)));
                }
                marks.add(new StudentRecord(textSid.getText(), Float.parseFloat(textAssignments.getText()),
                        Float.parseFloat(textMidterm.getText()), Float.parseFloat(textExam.getText())));

                textSid.setText("");
                textAssignments.setText("");
                textMidterm.setText("");
                textExam.setText("");

                record = new TableView<>();
                setColumns();
                record.setItems(marks);

                // Display
                VBox root = new VBox();
                root.getChildren().addAll(bar);
                root.getChildren().addAll(record);
                root.getChildren().addAll(addData);
                primaryStage.setScene(new Scene(root, 602, 600));
            }
        });

        newFile.setOnAction(newData);
        openFile.setOnAction(openData);
        saveFile.setOnAction(saveData);
        saveAs.setOnAction(saveAsData);
        exit.setOnAction(quit);

        VBox root = new VBox();
        root.getChildren().addAll(bar);
        root.getChildren().addAll(record);
        root.getChildren().addAll(addData);

        Scene recordWin = new Scene(root, 602, 600);
        primaryStage.setTitle("Lab 08");
        primaryStage.setScene(recordWin);
        primaryStage.show();
    }

    private void load() {
        File openFile = new File(currentFilename);
        ObservableList<StudentRecord> marks = FXCollections.observableArrayList();

        try{
            FileReader fileInput = new FileReader(openFile);
            BufferedReader input = new BufferedReader(fileInput);

            record = new TableView<>();
            setColumns();
            String line;
            while((line = input.readLine()) != null) {
                String[] data = line.split(",");
                marks.add(new StudentRecord(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])));
            }
            record.setItems(marks);
            fileInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        File saveFile = new File(currentFilename);

        try{
            PrintWriter output = new PrintWriter(saveFile);

            for(StudentRecord studentRecord: record.getItems()) {
                output.print(idColumn.getCellData(studentRecord) + ",");
                output.print(assignColumn.getCellData(studentRecord) + ",");
                output.print(midColumn.getCellData(studentRecord) + ",");
                output.println(examColumn.getCellData(studentRecord));
            }

            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setColumns() {
        // Columns
        idColumn = new TableColumn<>("SID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        assignColumn = new TableColumn<>("Assignments");
        assignColumn.setMinWidth(100);
        assignColumn.setCellValueFactory(new PropertyValueFactory<>("assignments"));

        midColumn = new TableColumn<>("Midterm");
        midColumn.setMinWidth(100);
        midColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));

        examColumn = new TableColumn<>("Final Exam");
        examColumn.setMinWidth(100);
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));

        markColumn = new TableColumn<>("Final Mark");
        markColumn.setMinWidth(100);
        markColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

        letterColumn = new TableColumn<>("Letter Grade");
        letterColumn.setMinWidth(100);
        letterColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        record.getColumns().addAll(idColumn, assignColumn, midColumn, examColumn, markColumn, letterColumn);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
