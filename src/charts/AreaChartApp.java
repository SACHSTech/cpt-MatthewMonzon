package charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AreaChartApp extends Application {
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

        ObservableList<XYChart.Series<String, Number>> areaChartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Entity vs Rate");

        for (String[] row : data) {
            series.getData().add(new XYChart.Data<>(row[0], Double.parseDouble(row[3])));
        }

        areaChartData.add(series);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rate");

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Entity");

        AreaChart<String, Number> chart = new AreaChart<>(new CategoryAxis(), new NumberAxis(), areaChartData);
        chart.setTitle("Entity vs Rate");

        return chart;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
