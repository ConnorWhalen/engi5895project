package model;

import View.ViewPrototype;
import comms.AnimationClientPrototype;

/* ***********************************************************************************
 * Model for a square in a grid. Creates a client which updates the square's position.
 * When the position is updated it refreshes the view.
 */
public class AnimationPrototypeModel {

    private static int MODEL_WIDTH = 10;
    private static int MODEL_HEIGHT = 10;

    private int squareX;
    private int squareY;
    private ViewPrototype view;
    private AnimationClientPrototype client;
    
    public AnimationPrototypeModel(ViewPrototype view){
        squareX = 0;
        squareY = 0;
        this.view = view;
        client = new AnimationClientPrototype(this);
        client.initComms();
    }

    public void update(String direction){
        if (direction.equals("up") && squareY > 0){
            squareY--;
        } else if (direction.equals("down") && squareY < (MODEL_HEIGHT-1)){
            squareY++;
        } else if (direction.equals("left") && squareX > 0){
            squareX--;
        } else if (direction.equals("right") && squareY < (MODEL_WIDTH-1)){
            squareX++;
        }
        view.refresh();
    }
    
    public int getWidth(){
        return MODEL_WIDTH;
    }
    
    public int getHeight(){
        return MODEL_HEIGHT;
    }
    
    public int getX(){
        return squareX;
    }
    
    public int getY(){
        return squareY;
    }
}
