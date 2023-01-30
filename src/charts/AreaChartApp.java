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
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AreaChartApp extends Application {
    public Parent createContent() {
        String fileName = "src/charts/data.csv";
        List<String[]> data = new ArrayList<>();

        //read the csv file 
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

        //make my chart lables
        ObservableList<XYChart.Series<String, Number>> areaChartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Country vs Deaths");

        for (String[] row : data) {
            series.getData().add(new XYChart.Data<>(row[0], Double.parseDouble(row[3])));
        }

        areaChartData.add(series);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Deaths");

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Country");

        AreaChart<String, Number> chart = new AreaChart<>(new CategoryAxis(), new NumberAxis(), areaChartData);
        chart.setTitle("Country vs Deaths");

        //create a check box
        List<CheckBox> countryCheckBoxes = new ArrayList<>();
        for (String[] row : data) {
            CheckBox checkBox = new CheckBox(row[0]);
            checkBox.setSelected(true); // set the checkbox to be selected by default
            countryCheckBoxes.add(checkBox);
        }
        
        VBox checkBoxContainer = new VBox();
        checkBoxContainer.getChildren().addAll(countryCheckBoxes);
        
        chart.getChildren().add(checkBoxContainer);

        //creates a check box for each country if box is checked it will show data if it isnt it wont show the data
        for (CheckBox checkBox : countryCheckBoxes) {
            checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                for(XYChart.Data<String, Number> data: series.getData()){
                    if(data.getXValue().equals(checkBox.getText())){
                        if(!isNowSelected){
                          data.getNode().setVisible(false);
                        }else{
                          data.getNode().setVisible(true);
                        }
                    }
                }
            });
        }

        return chart;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        
        VBox root = new VBox();
        root.getChildren().addAll(chart, checkBoxContainer);
        primaryStage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
