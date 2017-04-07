package com.arba.sample.rendering;

import javafx.scene.canvas.Canvas;

public class AllocatedObjectsDrawer extends Thread
{
    private Canvas canvas;
    private Integer pid;
    public static boolean killed;

    public AllocatedObjectsDrawer(Canvas canvas, Integer pid){
        setDaemon(true);
        setName("Allocated objects drawer");
        this.canvas = canvas;
        this.pid = pid;
    }

    @Override
    public void run()
    {
        
    }
}
