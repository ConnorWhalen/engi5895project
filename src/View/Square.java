package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/* ************************************
 * A square with a position and a size.
 */
public class Square extends JComponent {

    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    
    public Square(int width, int height){
        this.width = width;
        this.height = height;
        xPosition = 0;
        yPosition = 0;
    }
    
    public void setPosition(int x, int y){
        xPosition = x * width;
        yPosition = y * height;
        
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Rectangle2D.Double(xPosition, yPosition, width, height));
    }
}
