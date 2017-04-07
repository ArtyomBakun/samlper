package com.arba.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private Integer pid;
    
    @FXML
    private Canvas canvas;

    public Controller() {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
        System.out.println(pid);
        new Drawer(canvas, pid).start();
    }

}
