package com.arba.sample;

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

public class Drawer extends Thread
{
    private Canvas canvas;
    private Integer pid;
    public static boolean killed;
    
    public Drawer(Canvas canvas, Integer pid){
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

    private void drawGrid(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        for (int x = 0; x <= width; x+=10) {
            double x1 = x + 0.5 ;
            gc.moveTo(x1, 0);
            gc.lineTo(x1, height);
            gc.stroke();
        }

        for (int y = 0; y <= height; y+=10) {
            double y1 = y + 0.5 ;
            gc.moveTo(0, y1);
            gc.lineTo(width, y1);
            gc.stroke();
        }
    }

    private void drawZones(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        LinearGradient lg = new LinearGradient(0.5, -0.4, 0.5, 1, true,
                CycleMethod.NO_CYCLE, new Stop(0.0, Color.RED), new Stop(1.0, Color.GREEN));
        gc.setStroke(lg);
        gc.setLineWidth(20);
        gc.stroke();
    }

    private double getHeapUsage(){
        double usage = 0;
        List<String> info = AskJdkUtils.getMemoryUsageForProcesses(pid);
        for (int i = 0; i < info.size(); i++)
        {
            if(info.get(i).contains("PS Old Generation")){
                usage = Double.parseDouble(info.get(i + 4).trim().replace("% used", ""));
            }
        }
        System.out.println(usage);
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
            System.out.println(Arrays.toString(y));
            gc.strokePolyline(x, y, curr);
            Thread.sleep(1000);
            System.out.println("time " + i + " = " + (System.currentTimeMillis() - l));
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
