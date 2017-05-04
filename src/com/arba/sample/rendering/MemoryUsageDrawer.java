package com.arba.sample.rendering;

import com.arba.sample.util.AskJdkUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MemoryUsageDrawer extends Thread
{
    private Canvas canvas;
    private Integer pid;
    public boolean killed;
    private ScrollPane scrollPane;

    public MemoryUsageDrawer(Canvas canvas, ScrollPane scrollPane, Integer pid){
        setDaemon(true);
        setName("Memory usage drawer");
        this.canvas = canvas;
        this.scrollPane = scrollPane;
        this.pid = pid;
    }

    @Override
    public void run()
    {
        try
        {
            drawMemoryUsageGraphic(canvas);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private double getHeapUsage(){
        double usage = 0;
        String[] s = AskJdkUtils.getShortMemoryUsageForProcesses(pid).get(1).split("\\s+");
        usage = Double.parseDouble(s[4]);
        return usage/100;
    }
    
    private void drawMemoryUsageGraphic(Canvas canvas) throws InterruptedException
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        double h = canvas.getHeight(), w = canvas.getWidth();
        double step = 10, y0 = h*(1.0 - getHeapUsage()), y1, x0 = step;
        Random r = new Random();
        for(long i = 0; !killed; i++){
            long l = System.currentTimeMillis();
            y1 = h*(1.0 - getHeapUsage());
            if(x0 > w){
                w += step;
                canvas.setWidth(w);
                scrollPane.setHvalue(1.0);
            }
            gc.strokeLine((x0-step), y0, x0, y1);
            x0 += step;
            y0 = y1;
            System.out.println("com.arba.sample.rendering.MemoryUsageDrawer.getHeapUsage call#" + i + " time#" + (System.currentTimeMillis() - l) + " ms");
            Thread.sleep(1000);
        }
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

}
