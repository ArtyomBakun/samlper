package com.arba.sample.rendering;

import com.arba.sample.model.MemoryItem;
import com.arba.sample.util.AskJdkUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AllocatedObjectsDrawer extends Thread
{
    private TableView histoTable;
    private Integer pid;
    private ObservableList<MemoryItem> items;
    public static boolean killed;

    public AllocatedObjectsDrawer(TableView histoTable, Integer pid){
        setDaemon(true);
        setName("Allocated objects drawer");
        this.histoTable = histoTable;
        this.pid = pid;
        items = FXCollections.observableArrayList();
    }

    @Override
    public void run()
    {
        histoTable.setItems(items);

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                setDaemon(true);
                System.out.println("runn");
                initMemoryHistogram();
                histoTable.refresh();
            }
        }, 100, 3000);
//        try
//        {
//            for (int i = 0; i < 4; i++)
//            {
//                getMemoryHistogram();
//                System.out.println(i);
//                Thread.sleep(5000);
//            }
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }
    
    private void getMemoryHistogram(){
        List<String> l = AskJdkUtils.getMemoryMapForProcesses(pid);
        for (int i = 3; i < 3000; i++)
        {
            items.set(i - 3, items.get(i - 3).update(l.get(i)));
        }
    }
    private void initMemoryHistogram(){
        List<String> l = AskJdkUtils.getMemoryMapForProcesses(pid);
        for (int i = 3; i < l.size() - 1; i++)
        {
            items.add(new MemoryItem().update(l.get(i)));
        }
    }
}
