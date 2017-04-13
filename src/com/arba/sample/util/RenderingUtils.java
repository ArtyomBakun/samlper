package com.arba.sample.util;

import com.arba.sample.Main;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class RenderingUtils
{
    public static void refreshTableView(TableView table){
        table.setVisible(false);
        table.setVisible(true);
    }
    
    public static void setIcon(Stage stage){
        stage.getIcons().add(new Image(
                Main.class.getResourceAsStream("resources" + File.separator + "icon.png")));
    }
    
    public static void setIcon(Dialog dialog){
        setIcon((Stage) dialog.getDialogPane().getScene().getWindow());
    }
}
