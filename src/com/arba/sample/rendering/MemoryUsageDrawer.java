package com.arba.sample.rendering;

import com.arba.sample.util.AskJdkUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    public static boolean killed;
    
    public MemoryUsageDrawer(Canvas canvas, Integer pid){
        setDaemon(true);
        setName("Memory usage drawer");
        this.canvas = canvas;
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
        gc.setFill(Color.LIGHTGRAY);
        double h = canvas.getHeight(), w = canvas.getWidth();
        double step = 20;
        List<Double> statistics = new ArrayList<>();
        double[] x, y;
        statistics.add(h*(1.0 - getHeapUsage()));
        Random r = new Random();
        gc.fillRect(0, 0, w, h);
        for(int i = 0; i < 1000 && !killed; i++){
            final int curr = i+2;
            long l = System.currentTimeMillis();
            statistics.add(h*(1.0 - getHeapUsage()));
            x = new double[curr];
            y = new double[curr];
            for(int j = 0; j < curr; j++){
                x[j] = w*j/(i + 1);
                y[j] = statistics.get(j);
            }
            gc.fillRect(0, 0, w, h);
            gc.strokePolyline(x, y, curr);
            System.out.println("time " + i + " = " + (System.currentTimeMillis() - l));
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
