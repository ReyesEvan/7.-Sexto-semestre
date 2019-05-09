package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    
    private double xOffset, yOffset;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader parameterSelectionLoader = new FXMLLoader(getClass().getResource("ParameterSelectionView.fxml"));
        Parent parameterSelectionView = parameterSelectionLoader.load();
        ParameterSelectionController parameterSelectionController = parameterSelectionLoader.getController();
    
        FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("SimulationView.fxml"));
        Parent simulationView = simulationLoader.load();
        SimulationController simulationController = simulationLoader.getController();
        
        
        Scene scene = new Scene(parameterSelectionView, 500, 650);
        SceneMediator sceneMediator = new SceneMediator(scene);
        
        sceneMediator.addColleague("Simulation view", simulationView);
        sceneMediator.addColleague("Parameter selection view", parameterSelectionView);
        
        parameterSelectionController.setMediator(sceneMediator);
        
        
        Model model = new Model(simulationController);
        
        parameterSelectionController.setModel(model);
        simulationController.setModel(model);

//        model.createGeneticAlgorithm(3, 100, 5, 0.0, false, 1);
        
        
        primaryStage.setTitle("Smart bouncers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
    
        EventHandler<MouseEvent> mousePressed = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        };
        
        EventHandler<MouseEvent> mouseDragged = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            }
        };
        
        parameterSelectionView.setOnMousePressed(mousePressed);
        parameterSelectionView.setOnMouseDragged(mouseDragged);
        
        simulationView.setOnMousePressed(mousePressed);
        simulationView.setOnMouseDragged(mouseDragged);
        
        primaryStage.show();
    }
    


    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        
    }
}
