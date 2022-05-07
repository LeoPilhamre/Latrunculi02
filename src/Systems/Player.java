package Systems;

import Essentials.Binary;
import Essentials.Constants;
import Essentials.Global;
import Essentials.Settings;
import GUI.MainPanel.Highlight;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Leo Pilhamre
 */
public class Player implements MouseListener {
    
    private Point focusPoint;
    private String piece;
    
    private long validMoves;
    
            
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        Point point = e.getPoint();
        
        if (e.getButton() == Constants.rightMouseButton)
        {
            clear();
            
            return;
        }
        
        Point tilePoint = getTilePoint(point);
        if (tilePoint == null)
            return;
        
        piece = Global.board.getPieceAtPoint(tilePoint);
        
        if (focusPoint == null)
        {
            // Highlight valid moves
            if (piece == null)
                return;
            
            // Highlight & declare focus point.
            highlight(tilePoint);
        }
        else
        {
            // TODO: CHECK VALIDATION & MOVE IF POSSIBLE -> 'focusPoint = null'
            
            int indexOfPoint = Global.board.getIndexOfPoint(focusPoint);
            int targetIndex = Global.board.getIndexOfPoint(tilePoint);
            
            long bit = Binary.getBit(validMoves, targetIndex);
            
            if (bit == 1L)
            {
                piece = Global.board.getPieceAtPoint(focusPoint);
                
                Mover.move(piece, indexOfPoint, targetIndex);
                
                clear();
            } else
            {
                clear();
                
                if (piece != null)
                {
                    // Highlight & declare focus point.
                    highlight(tilePoint);
                }
            }
            
        }
        
    }
    
    private void clear()
    {
        focusPoint = null;
                
        Global.mainPanel.highlights.clear();
        Global.mainPanel.repaint();
    }
    
    private void highlight(Point tilePoint)
    {
        highlightTile(tilePoint);

        focusPoint = tilePoint;

        validMoves = Validator.getValidMoves(piece, Global.board.getIndexOfPoint(focusPoint));
        highlightValidMoves(validMoves);
    }
    
    private void highlightValidMoves(long validMoves)
    {
        int file, rank;
        
        Point point;
        
        long bit;
        for (int i = 0; i < 64; i++)
        {
            bit = Binary.getBit(validMoves, i);

            if (bit == 1L)
            {
                file = i % 8;
                rank = Math.floorDiv(i, 8);
                
                point = new Point(file, rank);
                
                Highlight highlight = new Highlight(point, Settings.validColor, Settings.validSize, Highlight.Shape.CIRCLE);

                Global.mainPanel.highlights.add(highlight);
            }
        }

        Global.mainPanel.repaint();
    }
    
    private void highlightTile(Point tilePoint)
    {
        Highlight highlight = new Highlight(tilePoint, Settings.pressOverlayColor, Settings.cellSize, Highlight.Shape.SQUARE);
                    
        Global.mainPanel.highlights.add(highlight);

        Global.mainPanel.repaint();
    }
    
    private Point getTilePoint(Point pressedPoint)
    {
        Point tile;
        for (int x = 0; x < Settings.cellCount; x++)
        {
            for (int y = 0; y < Settings.cellCount; y++)
            {
                tile = getTile(pressedPoint, x, y);
                
                if (tile != null)
                    return tile;
            }
        }
        
        return null;
    }
    
    private Point getTile(Point pressedPoint, int x, int y)
    {
        Point point = new Point(x, y);
        
        int originX = x * Settings.cellSize;
        int originY = y * Settings.cellSize;
        
        int offsetX = originX + Settings.cellSize;
        int offsetY = originY + Settings.cellSize;
        
        if (originX <= pressedPoint.x && pressedPoint.x <= offsetX)
            if (originY <= pressedPoint.y && pressedPoint.y <= offsetY)
                return point;
        
        return null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
