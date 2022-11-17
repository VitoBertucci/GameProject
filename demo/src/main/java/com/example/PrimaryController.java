package com.example;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PrimaryController {
    @FXML
    private Label label;
    @FXML
    private Pane mapPane;
    @FXML
    private VBox box;
    
    

    
    
    @FXML
    private void buttonPress() throws IOException {
        mapPane.getChildren().add(new TileMap(15));
    }
}
