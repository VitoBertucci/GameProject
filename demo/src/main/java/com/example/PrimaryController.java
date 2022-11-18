package com.example;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PrimaryController {
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML 
    private HBox topMaps;
    @FXML 
    private HBox bottomMaps;
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
    
    
    
    boolean gameStarted; //track game state
    private int round; //track round count
    private int turn; //track turn value
    private int pc; //track player count selection
    
    
    public TileMap createNewMap(int size, Color col) {
        TileMap map = new TileMap(size, col);
        return map;
    }
    
    public void resetGame() {
        gameStarted = false;
        round = 0;
        turn = 0;
        startButton.setVisible(true);
        playerCountChoice.getItems().clear();
        playerCountChoice.getItems().addAll(1,2,3,4);
        playerCountChoice.hide();
        topMaps.getChildren().clear();
        bottomMaps.getChildren().clear();
        playerTurn.setText(String.valueOf(turn));
        roundCount.setText(String.valueOf(round));
        playerCount.setText(null);
        textBox.setWrapText(true);
        textBox.setEditable(false);
        textBox.setText("Select amount of players, then press 'start game' to get started.");
    }
    
    @FXML
    public void initialize() throws Exception {
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
            for(int i = 1; i <= pc; i++) {
                if(i <= 2) {
                    topMaps.getChildren().add(createNewMap(4, Color.GREEN));
                } else {
                    bottomMaps.getChildren().add(createNewMap(4, Color.BLUE));
                }
            }
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            playerCount.setText(String.valueOf(pc));
            startButton.setVisible(false);
            textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");
        }
    }
    
    @FXML 
    private void resetButtonPress() throws Exception{
        resetGame();
    }

    @FXML 
    private void finishTurnButtonPress() throws IOException{
        if(gameStarted == true) {
            if (turn == pc){
                round++;
                turn = 1;
            } else {
                turn++;        
            }
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            textBox.setText("It is now player " + String.valueOf(turn) + "'s turn");
        }
    }
}
