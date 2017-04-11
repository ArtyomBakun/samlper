package com.arba.sample.rendering;

import com.arba.sample.model.MemoryItem;
import com.arba.sample.util.AskJdkUtils;
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
        this.histoTable = histoTable;
        this.pid = pid;
        items = FXCollections.observableArrayList();
    }

    @Override
    public void run()
    {
        histoTable.setItems(items);
        Timer t = new Timer();
        t.schedule(new TimerTask()
        {
            long count = 0;

            @Override
            public void run()
            {
                setName("Allocated objects drawer" + count);
                if(killed){
                    t.cancel();
                }
                long l = System.currentTimeMillis();
                getMemoryHistogram();
                System.out.println("com.arba.sample.rendering.AllocatedObjectsDrawer.getMemoryHistogram call#" + count + " time#" + (System.currentTimeMillis() - l) + " ms");
                histoTable.setVisible(false);
                histoTable.setVisible(true);
                count++;
            }
        }, 0, 1000);
    }
    
    private void getMemoryHistogram(){
        List<String> l = AskJdkUtils.getMemoryMapForProcesses(pid);
        items.clear();
        double total = 0;
        for (int i = 3; i < l.size() - 1; i++)
        {
            items.add(new MemoryItem().update(l.get(i)));
            total += items.get(i - 3).getBytes();
        }
        final double totalBytes = total;
        items.forEach(i -> i.setWeight((i.getBytes()/totalBytes) * 100));
        for (MemoryItem i : items)
        {
            i.setWeight((i.getBytes()/total) * 100);
        }
    }
}
