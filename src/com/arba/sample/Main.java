package com.arba.sample;

import com.arba.sample.model.Proc;
import com.arba.sample.rendering.AllocatedObjectsDrawer;
import com.arba.sample.rendering.MemoryUsageDrawer;
import com.arba.sample.util.AskJdkUtils;
import com.arba.sample.util.RenderingUtils;
import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        RenderingUtils.setIcon(primaryStage);
        Integer pid = selectProcessPid();
        if(pid != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Sampler");
            primaryStage.setScene(new Scene(root, 600, 600));
            primaryStage.show();
            Controller controller = loader.getController();
            controller.setPid(pid);
            primaryStage.setOnCloseRequest(event -> {
                System.out.println("Stopping...");
                controller.getMemoryUsageDrawer().killed = true;
                controller.getAllocatedObjectsDrawer().killed = true;
                controller.getThreadsDrawer().killed = true;
                Platform.exit();
                System.exit(0);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Integer selectProcessPid(){
        Dialog<Integer> dialog = new Dialog<>();
        RenderingUtils.setIcon(dialog);
        dialog.setTitle("Connect to process");
        dialog.setHeaderText("Choose the process by PID:");

        List<Proc> procs = getJavaProcesses();
        final ToggleGroup group = new ToggleGroup();
        TextArea area = new TextArea();
        area.setPrefHeight(20);
        VBox vbradio = new VBox(20);
        ButtonType buttonTypeConnect = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            if (group.getSelectedToggle() != null) {
                area.setText(((Proc)group.getSelectedToggle()).getCmdLine());
            }
        });
        procs.forEach(p -> p.setToggleGroup(group));
        procs.get(0).setSelected(true);
        vbradio.getChildren().addAll(procs);
        vbradio.getChildren().add(area);
        dialog.getDialogPane().setContent(vbradio);
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypeConnect, buttonTypeCancel);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeConnect) {
                return ((Proc)group.getSelectedToggle()).getPid();
            }
            return null;
        });
        Optional<Integer> result = dialog.showAndWait();
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
    
    private List<Proc> getJavaProcesses(){
        return AskJdkUtils.findJavaProcesses()
                .stream()
                .map(line -> new Proc(line))
                .collect(Collectors.toList());
    }
}
