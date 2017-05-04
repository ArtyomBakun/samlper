package com.arba.sample;

import com.arba.sample.model.MemoryItem;
import com.arba.sample.model.ThreadInfo;
import com.arba.sample.rendering.AllocatedObjectsDrawer;
import com.arba.sample.rendering.MemoryUsageDrawer;
import com.arba.sample.rendering.ThreadsDrawer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private Integer pid;
    
    private MemoryUsageDrawer memoryUsageDrawer;
    private AllocatedObjectsDrawer allocatedObjectsDrawer;
    private ThreadsDrawer threadsDrawer;

    @FXML
    private ScrollPane scrollPane;
    
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
    
    @FXML
    public TableView<ThreadInfo> threadsTable;

    @FXML
    public TableColumn<ThreadInfo, String> threadNameCol;

    @FXML
    public TableColumn<ThreadInfo, String> threadStateCol;

    @FXML
    public TableColumn<ThreadInfo, Boolean> daemonCol;

    @FXML
    public TableColumn<ThreadInfo, String> tidCol;

    @FXML
    public TableColumn<ThreadInfo, String> nidCol;

    @FXML
    public TableColumn<ThreadInfo, Integer> osPrioCol;

    @FXML
    public TextArea sampleInfoText;

    @FXML
    public Button deadlocksButton;

    public Controller() {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nameCol.setCellValueFactory(cell -> cell.getValue().fullNameProperty());
        percentCol.setCellValueFactory(cell -> cell.getValue().weightProperty().asObject());
        bytesCol.setCellValueFactory(cell -> cell.getValue().bytesProperty().asObject());
        itemsCol.setCellValueFactory(cell -> cell.getValue().instancesProperty().asObject());

        threadNameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        osPrioCol.setCellValueFactory(cell -> cell.getValue().osPrioProperty().asObject());
        tidCol.setCellValueFactory(cell -> cell.getValue().tidProperty());
        nidCol.setCellValueFactory(cell -> cell.getValue().nidProperty());
        threadStateCol.setCellValueFactory(cell -> {
            List<String> l = cell.getValue().getState();
            int index = l.size() - 1;
            return new SimpleStringProperty(index >= 0 ? l.get(index) : "");
        });
        daemonCol.setCellValueFactory(cell -> cell.getValue().daemonProperty());
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
        memoryUsageDrawer = new MemoryUsageDrawer(canvas, scrollPane, pid);
        allocatedObjectsDrawer = new AllocatedObjectsDrawer(histoTable, pid);
        threadsDrawer = new ThreadsDrawer(threadsTable, sampleInfoText, deadlocksButton, pid);
        startBackgroundProcesses();
    }

    private void startBackgroundProcesses(){
        memoryUsageDrawer.start();
        Platform.runLater(allocatedObjectsDrawer);
        Platform.runLater(threadsDrawer);
    }

    public MemoryUsageDrawer getMemoryUsageDrawer()
    {
        return memoryUsageDrawer;
    }

    public void setMemoryUsageDrawer(MemoryUsageDrawer memoryUsageDrawer)
    {
        this.memoryUsageDrawer = memoryUsageDrawer;
    }

    public AllocatedObjectsDrawer getAllocatedObjectsDrawer()
    {
        return allocatedObjectsDrawer;
    }

    public void setAllocatedObjectsDrawer(AllocatedObjectsDrawer allocatedObjectsDrawer)
    {
        this.allocatedObjectsDrawer = allocatedObjectsDrawer;
    }

    public ThreadsDrawer getThreadsDrawer()
    {
        return threadsDrawer;
    }

    public void setThreadsDrawer(ThreadsDrawer threadsDrawer)
    {
        this.threadsDrawer = threadsDrawer;
    }
}
