import acm.program.*;
import acm.graphics.*;
import java.awt.Color;
import java.awt.event.*;

public class LastDay extends GraphicsProgram
{
    private static final int NUMVERTS = 3;
    private int[] xCoords;
    private int[] yCoords;
    private int counter;
    
    public void run()
    {
        xCoords = new int[NUMVERTS];
        yCoords = new int[NUMVERTS];
    }
    
    public void mouseClicked(MouseEvent event)
    {
        if (counter == NUMVERTS)
        {
            return;
        }
        int x = event.getX();
        int y = event.getY();
        xCoords[counter] = x;
        yCoords[counter] = y;
        if (counter > 0)
        {
            GLine edge = new GLine(x, y, xCoords[counter-1], yCoords[counter-1]);
            add(edge);
        }
        if (counter == NUMVERTS - 1)
        {
            GLine edge = new GLine(x, y, xCoords[0], yCoords[0]);
            add(edge);
            mystery();
        }
        counter++;
    }
    
    private void mystery()
    {
        int index = (int)(Math.random()*NUMVERTS);
        int currX = xCoords[index];
        int currY = yCoords[index];
        for (int i=0; i<10000; i++)
        {
            index = (int)(Math.random()*NUMVERTS);
            int nextX = xCoords[index];
            int nextY = yCoords[index];
            int avgX = (currX + nextX)/2;
            int avgY = (currY + nextY)/2;
            GOval dot = new GOval(avgX, avgY, 1, 1);
            dot.setColor(Color.red);
            add(dot);
            currX = avgX;
            currY = avgY;
        }
    }
}