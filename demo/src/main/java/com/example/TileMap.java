package com.example;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileMap extends Pane {
    private static int size = 25; //length of one side of rectangle tile (i.e. h x w)
    private List<List<Tile>> tileList = new ArrayList<List<Tile>>(); //list used to store all tiles to access later
    List<Tile> ownedTiles = new ArrayList<Tile>();
    private boolean editable = false;
    public Color playerColor = Color.BLACK;
    public Color secondaryColor = Color.BLACK;
    
    //Tile class
    public class Tile extends Rectangle {
    public boolean owned = false; //store if tile has been clicked yet
    public boolean contested = false;
    public Color tileColor;
       
        //constructor
        Tile(double x, double y) {
            //set size of tile with global size up top
            setWidth(size);
            setHeight(size);
            
            //set position of tile in map using parameters
            setX(x);
            setY(y);
            
            //set initial colors of tile
            setFill(Color.LIGHTGRAY);
            setStrokeWidth(1);
            setStroke(Color.BLACK);
            
            //when tile is clicked, change to red, when clicked again change to beige
            setOnMouseClicked(e -> {
                if(editable == true) {
                    if(owned == false) {
                        owned = true;
                        ownedTiles.add(this);
                        setFill(playerColor);
                        fillNeighbors(this);
                    }
                }
            });
            
            //set mouse entered and exited for the hover over effect
            setOnMouseEntered(e -> {
                setStrokeWidth(5);
            });
            setOnMouseExited(e -> {
                setStrokeWidth(1);
            });
        }
    }
    
    //take in mapSize to determine length of square map
    public TileMap(int mapSize, Color col) {
        
        //nested for loop to create and display each tile
        for(int x = 0; x < mapSize; x++) {
            List<Tile> tmp = new ArrayList<Tile>();
            tileList.add(x,tmp);
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
    
    public String getTileCoordinates(Tile t) {
        return ("(" + t.getProperties().get("x") + "," + t.getProperties().get("y") + ")");
    }
    
    public List<Tile> getOwnedTiles() {
        return ownedTiles;
    }
    
    public void setEditable(boolean b) {
        editable = b;
    }
    
    
    public Tile getTile(int x, int y) {
        return tileList.get(x).get(y);
    }

    public void fillNeighbors(Tile t) {
        for(int i=-1; i<=1; i++) {
            if(((int)t.getProperties().get("x"))+i >= 0 && ((int)t.getProperties().get("x"))+i <= tileList.size()-1) {
                for(int j=-1; j<=1; j++) {
                    if(((int)t.getProperties().get("y"))+j >= 0 && ((int)t.getProperties().get("y"))+j <= tileList.size()-1) {
                        if(tileList.get((int) t.getProperties().get("x") + i).get((int) t.getProperties().get("y") + j).owned != true)
                        tileList.get((int) t.getProperties().get("x") + i).get((int) t.getProperties().get("y") + j).setFill(secondaryColor);
                        tileList.get((int) t.getProperties().get("x") + i).get((int) t.getProperties().get("y") + j).contested = true;
                    }
                }
            }
        }
    }
}
