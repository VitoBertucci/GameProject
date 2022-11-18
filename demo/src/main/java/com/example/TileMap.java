package com.example;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileMap extends Pane {
    private static int size = 25; //length of one side of rectangle tile (i.e. h x w)
    private List<List<Tile>> tileList = new ArrayList<List<Tile>>(); //list used to store all tiles to access later
    List<Tile> selectedTiles = new ArrayList<Tile>();
    private boolean editable = false;
    //Tile class
    public class Tile extends Rectangle {
    public boolean selected = false; //store if tile has been clicked yet
       
        //constructor
        Tile(double x, double y, Color col) {
            //set size of tile with global size up top
            setWidth(size);
            setHeight(size);
            
            //set position of tile in map using parameters
            setX(x);
            setY(y);
            
            //set initial colors of tile
            setFill(col);
            setStrokeWidth(1);
            setStroke(Color.BLACK);
            
            //when tile is clicked, change to red, when clicked again change to beige
            setOnMouseClicked(e -> {
                if(editable == true) {
                    if(selected == false) {
                        setFill(Color.RED);
                        selected = true;
                        selectedTiles.add(this);
                        
                        
                        
                        // int xc = this.getProperties().get("x");
                        // int yc = this.getProperties().get("y");
                        
                        fillNeighbors(this, Color.BLUE);
                        // getTile(xc, yc);
                        // getTile(xc, yc);
                        // getTile(xc, yc);
                        // getTile(xc, yc);
                        
                        // tileList.get(xc - 1).get(yc);
                        // tileList.get(xc + 1).get(yc);
                        // tileList.get(xc).get(yc - 1);
                        // tileList.get(xc).get(yc + 1);
                        
                    } else {
                        setFill(col);
                        selected = false;
                        selectedTiles.remove(this);
                        fillNeighbors(this,col);
                    }
                }
            });
            
            //set mouse entered and exited for the hover over effect
            setOnMouseEntered(e -> {
                if(editable == true) {
                    setFill(Color.PINK);
                }
            });
            setOnMouseExited(e -> {
                if(editable == true) {
                    if(selected == false) {
                        setFill(col);
                    } else {
                        setFill(Color.RED);
                    }
                }
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
                Tile tile = new Tile(xCoord, yCoord, col); //create new tile in that spot
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
    
    public List<Tile> getSelectedTiles() {
        return selectedTiles;
    }
    
    public void setEditable(boolean b) {
        editable = b;
    }
    
    public Tile getTile(int x, int y) {
        return tileList.get(x).get(y);
    }

    public void fillNeighbors(Tile t, Color col) {
        int xc = (int) t.getProperties().get("x");
        int yc = (int) t.getProperties().get("y");
        
        if((xc - 1) >= 0) {
           tileList.get(xc - 1).get(yc).setFill(col);
        }
        if((xc + 1) < tileList.size() - 1) {
           tileList.get(xc + 1).get(yc).setFill(col);
        }
        if((yc - 1) >= 0) {
           tileList.get(xc).get(yc - 1).setFill(col);
        }
        if((yc + 1) < tileList.size() - 1) {
           tileList.get(xc).get(yc + 1).setFill(col);
        }

        /*
        if((xc -1) >= 0) {
            neighbors.add(tileList.get((int)getProperties().get("x") - 1).get(yc));
        }
        if((xc + 1) < tileList.size()-1) {
            neighbors.add(tileList.get((int)getProperties().get("x") + 1).get(yc));
        }
        if((yc - 1) >= 0) {
            neighbors.add(tileList.get((int)getProperties().get("x")).get((int)getProperties().get("y") - 1));
        }
        if((yc + 1) < tileList.get((int)getProperties().get("x")).size()-1) {
            neighbors.add(tileList.get((int)getProperties().get("x")).get((int)getProperties().get("y") + 1));
        }
        return neighbors;
        */
    }
}
