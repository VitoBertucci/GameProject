package com.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.TileMap.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PrimaryController {
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button rollButton;
    @FXML 
    private FlowPane mapBox;
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
    private Label rollValue;
    @FXML
    ChoiceBox<Integer> playerCountChoice = new ChoiceBox<Integer>();
    
    private HashMap<Integer, List<Tile>> playerTiles = new HashMap<Integer, List<Tile>>(); //store list of owned tiles per player
    private HashMap<Integer, Color> playerColors = new HashMap<Integer, Color>(); //store list of colors for each players
    private HashMap<Integer, Color> playerSecondaryColors = new HashMap<Integer, Color>(); //store list of secondary colors for each players
    
    
    boolean gameStarted; //track game state
    private int round; //track round count
    private int turn; //track turn value
    private int pc; //track player count selection
    private int playerRollValue; //track value of dice roll
    private TileMap map; //tile map
    
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
        
        //clear all player owned tile lists
        for(List l : playerTiles.values()) {
            l.clear();
        }
        
        playerTurn.setText(String.valueOf(turn));
        roundCount.setText(String.valueOf(round));
        playerCount.setText(null);
        textBox.setText("Select amount of players, then press 'start game' to get started.");
    }
    
    @FXML 
    private void resetButtonPress() throws Exception {
        resetGame();
    }
    
    @FXML
    public void initialize() throws Exception {
        
        //each player gets a list for their owned tiles
        playerTiles.put(1, new ArrayList<Tile>());
        playerTiles.put(2, new ArrayList<Tile>());
        playerTiles.put(3, new ArrayList<Tile>());
        playerTiles.put(4, new ArrayList<Tile>());
        
        //each player gets a primary color for owned tiles
        playerColors.put(1, Color.BLUE);
        playerColors.put(2, Color.GREEN);
        playerColors.put(3, Color.PURPLE);
        playerColors.put(4, Color.ORANGE);
        
        //each player gets a secondary color for contested tiles
        playerSecondaryColors.put(1, Color.LIGHTBLUE);
        playerSecondaryColors.put(2, Color.LIGHTGREEN);
        playerSecondaryColors.put(3, Color.LIGHTPINK);
        playerSecondaryColors.put(4, Color.LIGHTYELLOW);
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
            
            map = new TileMap(10, Color.BEIGE);
            map.playerColor = playerColors.get(turn);
            map.secondaryColor = playerSecondaryColors.get(turn);
            map.setEditable(true);
            mapBox.getChildren().add(map);
            
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            playerCount.setText(String.valueOf(pc));
            
            startButton.setVisible(false);
            
            textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");
        }
    }
    
    
    
    @FXML
    private void finishTurnButtonPress() throws IOException {

        if(gameStarted == true) {
            //add current players tiles to their list for access
            playerTiles.get(turn).addAll(map.getOwnedTiles());
            
            //display the roll value on each of that player's owned tiles for that turn
            for(Tile t : map.getOwnedTiles()) {
                t.text.setText(String.valueOf(playerRollValue));
                t.value = playerRollValue;
            }
            
            //if all players have completed their turn, go to next round
            if (turn == pc) {
                textBox.clear();
                round++;
                turn = 1;
            } else {
                turn++;        
            }
            
            //reset values for next turn
            map.getOwnedTiles().clear();//clear list for next player to add tiles to
            playerRollValue = 0;
            rollValue.setText(null);
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            textBox.appendText("It is now player " + String.valueOf(turn) + "'s turn");
            map.playerColor = playerColors.get(turn);
            map.secondaryColor = playerSecondaryColors.get(turn);
        }
    }
    
    //roll die, set value and display
    @FXML 
    private void rollDiceButtonPress() throws IOException{
        playerRollValue = Dice.roll();
        rollValue.setText(String.valueOf(playerRollValue));
    }
}
