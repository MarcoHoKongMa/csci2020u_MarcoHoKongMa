package lab07;

import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class lab07 extends Application {
    private Canvas canvas;

    // >> Pie Chart Data <<
    private static int total = 0;
    private static Map<String, Integer> warningCounts;
    private static String[] colName = {
            "FLASH FLOOD", "SEVERE THUNDERSTORM", "SPECIAL MARINE", "TORNADO"
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };
    private static Color[] strokeColours = {
            Color.DARKCYAN, Color.DARKGOLDENROD, Color.ORANGERED,
            Color.DARKRED, Color.DARKGREEN, Color.DARKVIOLET
    };

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().addAll(canvas);

        // >> Insert Data <<
        File sourceData = new File("weatherwarnings-2015.csv");
        int col = 5;

        try {
            FileReader fileInput = new FileReader(sourceData);
            BufferedReader input = new BufferedReader(fileInput);

            // >> Count Warnings <<
            warningCounts = new TreeMap<>();
            String line;
            while((line = input.readLine()) != null) {
                String[] data = line.split(",");
                if(warningCounts.containsKey(data[col])) {
                    int prev = warningCounts.get(data[col]);
                    warningCounts.put(data[col], prev + 1);
                }
                else {
                    warningCounts.put(data[col], 1);
                }
                total++;
            }

            input.close();
            fileInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 795, 490);
        primaryStage.setTitle("Lab 07");
        primaryStage.setScene(scene);
        primaryStage.show();

        draw(root);
    }

    private void draw(Group root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // >> Draw Legend <<
        int x = 50;
        int y = 95;
        for(int i = 0; i < colName.length; i++) {
            gc.setFill(pieColours[i]);
            gc.fillRect(x, y, 55, 35);
            gc.setStroke(strokeColours[i]);
            gc.strokeRect(x, y, 55, 35);
            gc.setFill(Color.BLACK);
            gc.fillText(colName[i], x + 65, y + 22);
            y += 50;
        }

        // >> Draw Pie Chart <<
        x = 0;
        double size = 0;
        for(int i = 0; i < colName.length; i++) {
            size = 360.5 * ((double)warningCounts.get(colName[i]) / (double)total);
            gc.setFill(pieColours[i]);
            gc.fillArc(350, 40, 400, 400, x, Math.round(size), ArcType.ROUND);
            gc.setStroke(strokeColours[i]);
            gc.strokeArc(350, 40, 400, 400, x, Math.round(size), ArcType.ROUND);
            x += Math.round(size);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
