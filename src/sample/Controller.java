package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.xml.sax.SAXException;
import sample.client.Client;
import sample.dao.ConcreteDAO;
import sample.dao.DAO;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private Button addLine;
    @FXML
    private Button addStation;
    @FXML
    private Button updateLine;
    @FXML
    private Button updateStation;
    @FXML
    private Button deleteLine;
    @FXML
    private Button deleteStation;
    @FXML
    private Button save;
    @FXML
    private TextField idLine;
    @FXML
    private TextField colorLine;
    @FXML
    private TextField idStation;
    @FXML
    private TextField name;
    @FXML
    private Label information;

    DAO dao;
    Client client;
    @FXML
    public void initialize() {
        try {
            dao = new ConcreteDAO();
            client = new Client("localhost", 3636);
            printToTreeView();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void printToTreeView() throws IOException, ClassNotFoundException{
        treeView.setRoot(null);
        ArrayList<Line> lines = client.getAll().getLines();

        TreeItem<String> root = new TreeItem<>("Metropolitan");

        for(int i = 0; i < lines.size(); i++){
            String info;
            info = "id: " + lines.get(i).line_id + " Color: " + lines.get(i).color;
            ArrayList<Station> stations = lines.get(i).getStations();
            TreeItem<String> line = new TreeItem<>(info);
            for(int j = 0; j < stations.size(); j++){
                String flightInfo = stations.get(j).station_id + " " + stations.get(j).name;
                TreeItem<String> flight = new TreeItem<>(flightInfo);
                line.getChildren().add(flight);
            }

            root.getChildren().add(line);
        }
        treeView.setRoot(root);
    }
    @FXML
    private void addLine(ActionEvent event) throws IOException, ClassNotFoundException{
        String code = idLine.getText();
        String color = colorLine.getText();
        client.addLine(Integer.valueOf(code), color);
        printToTreeView();
    }
    @FXML
    private void addStation(ActionEvent event) throws IOException, ClassNotFoundException {
        String id = this.idStation.getText();
        String name = this.name.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }
        String idLine = selectedItem.getValue().split(" ")[1];

        client.addStation( Integer.valueOf(idLine), Integer.valueOf(id), name);
        printToTreeView();

    }
    @FXML
    private void updateLine(ActionEvent event) throws IOException, ClassNotFoundException {
        String newCode = idLine.getText();
        String newColor = colorLine.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }

        String lineId = selectedItem.getValue().split(" ")[1];
        String lineColor = selectedItem.getValue().split(" ")[3];

        if(newCode.equals("")){
            newCode = lineId;
        }
        if(newColor.equals("")){
            newColor = lineColor;
        }
        client.updateLine(Integer.parseInt(lineId), Integer.parseInt(newCode), newColor);
        printToTreeView();
    }


    @FXML
    public void deleteLine(ActionEvent event) throws IOException, ClassNotFoundException{
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }
        String lineId = selectedItem.getValue().split(" ")[1];
        client.deleteLine(Integer.valueOf(lineId));
        printToTreeView();
    }
    @FXML
    public void deleteStation(ActionEvent event) throws IOException, ClassNotFoundException{
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected line");
            return;
        }
        String lineId = selectedItem.getParent().getValue().split(" ")[1];
        String stationId = selectedItem.getValue().split(" ")[0];
        client.deleteStation(Integer.parseInt(lineId), Integer.parseInt(stationId));
        printToTreeView();
    }

}

