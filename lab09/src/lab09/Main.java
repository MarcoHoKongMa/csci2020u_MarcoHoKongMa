package lab09;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main extends Application {
    private Canvas canvas;
    private int size = 0;
    private float max = 0.0f;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Lab 09: Stock Performance");

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(canvas);

        List<Float> googClosing = new ArrayList<Float>(downloadStockPrices("GOOG"));
        List<Float> aaplClosing = new ArrayList<Float>(downloadStockPrices("AAPL"));

        size = googClosing.size();
        if(aaplClosing.size() > size) {
            size = aaplClosing.size();
        }

        // Find Max
        for(int i = 0; i < size; i++) {
            if(googClosing.get(i) > max) {
                max = googClosing.get(i);
            }
            if(aaplClosing.get(i) > max) {
                max = aaplClosing.get(i);
            }
        }

        primaryStage.setScene(new Scene(root, 900, 650));
        primaryStage.show();

        drawLinePlot(googClosing, aaplClosing);
    }

    private void drawLinePlot(List<Float> list1, List<Float> list2) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.strokeLine(50, 50, 50, 600);
        gc.strokeLine(50, 600, 850, 600);

        plotLine(list1, Color.RED);
        plotLine(list2, Color.BLUE);
    }

    private void plotLine(List<Float> list, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double prevX = 50.0;
        double prevY = 600.0;
        double dx = 800.0f / (float) size;

        gc.setStroke(color);
        for(float value: list) {
            if(prevY == 600.0) {
                prevY = 600.0 - (550 * (value / max));
            }
            else {
                gc.strokeLine(prevX, prevY, prevX + dx, 600.0 - (550 * (value / max)));
                prevX += dx;
                prevY = 600.0 - (550 * (value / max));
            }
        }
    }

    private List<Float> downloadStockPrices(String symbol) throws IOException {
        URL netURL = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + symbol + "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true");

        URLConnection conn = netURL.openConnection();
        conn.setDoOutput(false);
        conn.setDoInput(true);

        List<Float> close = new ArrayList<Float>();

        BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = input.readLine();
        int closeIndex = -1;
        String[] columnNames = line.split(",");
        for(int i = 0; i < columnNames.length; i++) {
            if(columnNames[i].equals("Close")) {
                closeIndex = i;
                break;
            }
        }

        if(closeIndex < 0) {
            System.err.println("Error: Column name not found");
            System.exit(1);
        }

        while((line = input.readLine()) != null) {
            String[] data = line.split(",");
            close.add(Float.parseFloat(data[closeIndex]));
        }

        input.close();
        return close;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
