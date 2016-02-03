package View;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.AnimationPrototypeModel;

/* ***********************************************************************
 * View for moving a square around in a box.
 * Creates an AnimationPrototypeModel which updates the square's position.
 */
public class ViewPrototype extends JFrame {
    
    private AnimationPrototypeModel model;
    private Square square;
    private int tilesAcross;
    private int tilesUpandDown;
    
    public ViewPrototype(){
        model = new AnimationPrototypeModel(this);
        tilesAcross = model.getWidth();
        tilesUpandDown = model.getHeight();
        
        int squareWidth = 50;
        int squareHeight = 50;
        square = new Square(squareWidth, squareHeight);
        getContentPane().add(square);
        
        setSize((squareWidth * tilesAcross), (squareHeight * tilesUpandDown));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        refresh();
    }
    
    public void refresh(){
        square.setPosition(model.getX(), model.getY());
        square.repaint();
    }
    
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new ViewPrototype();
            }
        });
    }

}
