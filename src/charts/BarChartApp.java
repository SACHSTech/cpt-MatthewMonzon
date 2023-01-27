/* ....Show License.... */
package charts;
 
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
 
/**
 * A chart that displays rectangular bars with heights indicating data values
 * for categories. Used for displaying information when at least one axis has
 * discontinuous or discrete data.
 */
public class BarChartApp extends Application {
    public Parent createContent() {
        String fileName = "C:/Users/matth/Downloads/cpt-MatthewMonzon 1/cpt-MatthewMonzon/src/charts/data.csv";
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
          String line;
          br.readLine(); // Skip the first line (headers)
          while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            data.add(values);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Entity");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rate");
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Entity vs Rate");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String[] row : data) {
          series.getData().add(new XYChart.Data<>(row[0], Double.parseDouble(row[3])));
        }

        chart.getData().add(series);

        return chart;
    }
 
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
 
    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}