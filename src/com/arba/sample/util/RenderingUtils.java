package com.arba.sample.util;

import javafx.scene.control.TableView;

public class RenderingUtils
{
    public static void refreshTableView(TableView table){
        table.setVisible(false);
        table.setVisible(true);
    }
}
