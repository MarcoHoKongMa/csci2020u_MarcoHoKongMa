package lab06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class lab06 extends Application {
    private Canvas canvas;

    // >> Bar Chart Data <<
    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1, 308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8, 1335932.6,1472362.0,1583521.9,1613246.3
    };

    // >> Pie Chart Data <<
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().addAll(canvas);

        Scene scene = new Scene(root, 1167, 700);
        primaryStage.setTitle("Lab 06");
        primaryStage.setScene(scene);
        primaryStage.show();

        draw(root, scene.getHeight());
    }

    private void draw(Group root, double canvasHeight) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // >> Draw Bar Chart <<
        int x = 50;
        int h = 0;
        for(int i = 0; i < avgHousingPricesByYear.length; i++){
            // >> Red Bar <<
            h = (int)(avgHousingPricesByYear[i] / (avgCommercialPricesByYear[avgCommercialPricesByYear.length - 1] / (canvasHeight - 100)));
            gc.setFill(Color.RED);
            gc.fillRect(x, 650 - h, 25, h);

            // >> Blue Bar <<
            h = (int)(avgCommercialPricesByYear[i] / (avgCommercialPricesByYear[avgCommercialPricesByYear.length - 1] / (canvasHeight - 100)));
            gc.setFill((Color.BLUE));
            gc.fillRect(x + 25, 650 - h, 25, h);

            x += 60;
        }

        // >> Draw Pie Chart <<
        int total = 0;
        for(int purchases: purchasesByAgeGroup){
            total += purchases;
        }
        x = 0;
        double size = 0;
        for(int i = 0; i < purchasesByAgeGroup.length; i++){
            gc.setFill(pieColours[i]);
            size = 360.5 * ((double)purchasesByAgeGroup[i] / (double)total);
            gc.fillArc(650, 130, 450, 450, x, Math.round(size), ArcType.ROUND);
            x += Math.round(size);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
