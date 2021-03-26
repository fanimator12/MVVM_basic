package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

import java.io.IOException;

public class ViewHandler {
    private ViewModelFactory viewModelFactory;
    private ConvertViewController convertViewController;
    private Stage primaryStage;
    private Scene currentScene;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        openView("converter");
    }

    public void openView(String view){
        Region root = null;
        switch (view){
            case "converter":
                root = loadConvertView("converter.fxml");
                break;
        }
        currentScene.setRoot(root);
        String title = "";
        if(root.getUserData() != null)
            title += root.getUserData();
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }

    private Region loadConvertView(String fxmlFile) {
        Region root = null;
        if(convertViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                root = loader.load();
                convertViewController = loader.getController();
                convertViewController.init(this, viewModelFactory.getConvertViewModel(), root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            convertViewController.reset();
        }
        return convertViewController.getRoot();
    }
}
