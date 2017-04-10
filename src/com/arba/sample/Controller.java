package com.arba.sample;

import com.arba.sample.model.MemoryItem;
import com.arba.sample.rendering.AllocatedObjectsDrawer;
import com.arba.sample.rendering.MemoryUsageDrawer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private Integer pid;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private TableView<MemoryItem> histoTable;
    
    @FXML
    private TableColumn<MemoryItem, String> nameCol;

    @FXML
    private TableColumn<MemoryItem, Double> percentCol;

    @FXML
    private TableColumn<MemoryItem, Integer> bytesCol;

    @FXML
    private TableColumn<MemoryItem, Integer> itemsCol;

    public Controller() {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nameCol.setCellValueFactory(cell -> cell.getValue().fullNameProperty());
        bytesCol.setCellValueFactory(cell -> cell.getValue().bytesProperty().asObject());
        itemsCol.setCellValueFactory(cell -> cell.getValue().instancesProperty().asObject());
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
//        Platform.runLater(
                new MemoryUsageDrawer(canvas, pid).start();
//        )
        ;
        Platform.runLater(new AllocatedObjectsDrawer(histoTable, pid));
    }

}
