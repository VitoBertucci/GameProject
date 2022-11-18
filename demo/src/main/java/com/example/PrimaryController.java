package com.example;
import java.io.IOException;
import java.util.HashMap;

import com.example.TileMap.Tile;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PrimaryController {
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML 
    private Pane mapBox;
    @FXML
    private VBox box;
    @FXML 
    private ImageView viewer;
    @FXML 
    private TextArea textBox;
    @FXML 
    private Label playerTurn;
    @FXML
    private Label roundCount;
    @FXML 
    private Label playerCount;
    @FXML
    ChoiceBox<Integer> playerCountChoice = new ChoiceBox<Integer>();
    
    private HashMap<Integer, TileMap> playerMaps = new HashMap<Integer, TileMap>();
    private HashMap<Integer, Color> playerColors = new HashMap<Integer, Color>();
    
    
    boolean gameStarted; //track game state
    private int round; //track round count
    private int turn; //track turn value
    private int pc; //track player count selection
    
    //create new maps with size and color
    public TileMap createNewMap(int size, Color col) {
        TileMap map = new TileMap(size, col);
        return map;
    }
    
    //reset game functionality
    public void resetGame() {
        gameStarted = false;
        round = 0;
        turn = 0;
        startButton.setVisible(true);
        playerCountChoice.getItems().clear();
        playerCountChoice.getItems().addAll(1,2,3,4);
        playerCountChoice.hide();
        mapBox.getChildren().clear();
        playerTurn.setText(String.valueOf(turn));
        roundCount.setText(String.valueOf(round));
        playerCount.setText(null);
        textBox.setWrapText(true);
        textBox.setEditable(false);
        textBox.setText("Select amount of players, then press 'start game' to get started.");
    }
    
    @FXML
    public void initialize() throws Exception {
        playerColors.put(1, Color.BLUE);
        playerColors.put(2, Color.GREEN);
        playerColors.put(3, Color.PURPLE);
        playerColors.put(4, Color.ORANGE);
        resetGame();
    }
    
    @FXML
    private void startButtonPress() throws IOException {
        if (playerCountChoice.getValue() == null) {
            textBox.setText("Select player count and then press 'Start Game'");
        } else {
            gameStarted = true;
            turn = 1;
            round = 1;
            pc = (int) playerCountChoice.getValue();
            TileMap mainMap = new TileMap(10, Color.BEIGE);
            mainMap.setEditable(true);
            mapBox.getChildren().add(mainMap);
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            playerCount.setText(String.valueOf(pc));
            startButton.setVisible(false);
            textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");
            playerMaps.get(turn).setEditable(true);
        }
    }
    
    @FXML 
    private void resetButtonPress() throws Exception {
        resetGame();
    }

    @FXML
    private void finishTurnButtonPress() throws IOException{
        playerMaps.get(turn).setEditable(false);
        if(gameStarted == true) {
            if (turn == pc) {
                textBox.clear();
                for(Integer i : playerMaps.keySet()) {
                    textBox.appendText("Player " + i + "'s tiles: ");
                    for(Tile t : playerMaps.get(i).getSelectedTiles()) {
                        textBox.appendText(playerMaps.get(i).getTileCoordinates(t));
                    }
                    textBox.appendText("\n");
                }
                round++;
                turn = 1;
            } else {
                turn++;        
            }
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            textBox.appendText("\nIt is now player " + String.valueOf(turn) + "'s turn");
            playerMaps.get(turn).setEditable(true);
        }
    }
}
