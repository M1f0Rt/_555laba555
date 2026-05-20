package org.example._555laba555.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example._555laba555.fileManager.Conservation;
import org.example._555laba555.service.ServiceManager;

import java.io.File;

public class mainpage extends Application {
    private ServiceManager serviceManager;
    private Conservation conservation;
    private String currentFile;
    private Stage primaryStage;

    @Override
    public void init(){
        this.serviceManager = new ServiceManager();
        try{
            this.currentFile = "records.csv";
            this.conservation = new Conservation(currentFile);
        }catch (Exception e){
            System.err.println("Ошибка загруски в lavafx: "+e.getMessage());
        }
    }
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();

        MenuBar menuBar =new MenuBar();
        Menu fileMnu = new Menu("ФАЙЛ");
        MenuItem loadItem = new MenuItem("Загрузить файл");
        loadItem.setOnAction(e -> handleLoadFile());

        MenuItem saveItem = new MenuItem("Сохранить");
        saveItem.setOnAction(e -> {
            try { conservation.save(serviceManager); } catch (Exception ex) { showError(ex.getMessage()); }
        });

        fileMnu.getItems().addAll(loadItem, saveItem);
        menuBar.getMenus().add(fileMnu);
        root.setTop(menuBar);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Реактивы", new reagview(serviceManager, conservation)));
        tabPane.getTabs().add(new Tab("Партии", new batchview(serviceManager, conservation)));
        tabPane.getTabs().add(new Tab("Движения", new moveview(serviceManager, conservation)));
        root.setCenter(tabPane);

        primaryStage.setScene(new Scene(root, 1000, 700));
        updateTitle();
        primaryStage.show();
    }
    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            try {
                this.currentFile = selectedFile.getAbsolutePath();
                this.conservation = new Conservation(currentFile);
                this.conservation.load(serviceManager);
                updateTitle();
            } catch (Exception ex) {
                showError("Ошибка при смене файла: " + ex.getMessage());
            }
        }
    }
    private void updateTitle() {
        primaryStage.setTitle("Не работающий файл — [" + currentFile + "]");
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }



}