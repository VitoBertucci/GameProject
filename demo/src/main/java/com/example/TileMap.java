package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileMap extends Pane {
    private static int size = 15; //length of one side of rectangle tile (i.e. h x w)
    private List<List<Tile>> list = new ArrayList<List<Tile>>(); //list used to store all tiles to access later
    
    
    //Tile class
    public class Tile extends Rectangle {
        boolean clicked = false; //store if tile has been clicked yet
        
        //constructor
        Tile(double x, double y) {
            
            //set size of tile with global size up top
            setWidth(size);
            setHeight(size);
            
            //set position of tile in map using parameters
            setX(x);
            setY(y);
            
            //set initial colors of tile
            setFill(Color.BEIGE);
            setStrokeWidth(1);
            setStroke(Color.BLACK);
            
            //when tile is clicked, change to red, when clicked again change to beige
            setOnMouseClicked(e -> {
                if(clicked == false) {
                    setFill(Color.RED);
                    clicked = true;
                    
                } else {
                    setFill(Color.BEIGE);
                    clicked = false;
                }
                
                //diplay coordinates of tile in terminal
                System.out.println(getTileAtCoordinates((int)getProperties().get("x"), (int)getProperties().get("y")));
            });
            
            //set mouse entered and exited for the hover over effect
            setOnMouseEntered(e -> {
                setFill(Color.PINK);
            });
            setOnMouseExited(e -> {
                if(clicked == false) {
                    setFill(Color.BEIGE);
                } else {
                    setFill(Color.RED);
                }
            });
        }
    }
    
    //take in mapSize to determine length of square map
    public TileMap(int mapSize) {
        
        //nested for loop to create and display each tile
        for(int x = 0; x < mapSize; x++) {
            List<Tile> tmp = new ArrayList<Tile>();
            list.add(x,tmp);
            for(int y = 0; y < mapSize; y++) {
                int xCoord = x * size; //create pixel coordinate of x
                int yCoord = y * size; //create pixel coordinate of y
                Tile tile = new Tile(xCoord, yCoord); //create new tile in that spot
                tmp.add(y, tile); //add tile to list to access later
                tile.getProperties().put("x", x); //put the x coordinate in the tile's properties
                tile.getProperties().put("y", y); //put the y coordinate in the tile's properties
                getChildren().add(tile); //add tile to grid
            }
        }
    }
    
    //
    public String getTileAtCoordinates(int x, int y) {
        return (list.get(x).get(y).getProperties().get("x") + " " + list.get(x).get(y).getProperties().get("y"));
    }
}
