/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Essentials.Binary;
import Essentials.Global;
import Essentials.Settings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author leox965a
 */
public class MainPanel extends javax.swing.JPanel {
    
    public static class Highlight
    {
        public Point point;
        public Color color;
        
        public int size;
        
        public enum Shape
        {
            SQUARE,
            CIRCLE
        }
        public Shape shape;
        
        public Highlight(Point point, Color color, int size, Shape shape)
        {
            this.point = point;
            this.color = color;
            
            this.size = size;
            
            this.shape = shape;
        }
    }
    public List<Highlight> highlights = new ArrayList<Highlight>();
    
    private Map<String, BufferedImage> pieceImages = new HashMap<String, BufferedImage>();
    private BufferedImage backgroundImage;
    
    
    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        initComponents();
        
        setImages();
    }
    
    
    private void setImages()
    {
        backgroundImage = getImage(Settings.backgroundImagePath);
        
        Map<String, Long> bitboards = Global.board.getBoard();
        
        BufferedImage image;
        String piece;
        for (String key : bitboards.keySet())
        {
            piece = key;
            
            String path = Settings.piecePath(piece);
            image = getImage(path);

            pieceImages.put(piece, image);
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        paintGrid(g);
        
        paintPieces(g);
        
        paintHighlights(g);
    }

    private void paintGrid(Graphics g)
    {
        for (int x = 0; x < Settings.cellCount; x++)
        {
            for (int y = 0; y < Settings.cellCount; y++)
            {
                paintTile(g, x, y);
            }
        }
    }
    
    private void paintTile(Graphics g, int x, int y)
    {
        int cordX = x * Settings.cellSize;
        int cordY = y * Settings.cellSize;

        switch (Settings.boardMode)
        {
            case NORMAL:
                Color color = Settings.tileColor01;
                if ((x + y) % 2 == 0)
                    color = Settings.tileColor02;

                g.setColor(color);

                g.fillRect(cordX, cordY, Settings.cellSize, Settings.cellSize);

                break;
            case FANCY:
                int cropSize = (int) (backgroundImage.getWidth() / Settings.cellCount);
                int cropCordX = cropSize * x;
                int cropCordY = cropSize * y;

                BufferedImage croppedImage = cropImage(backgroundImage, cropCordX, cropCordY, cropSize, cropSize);

                g.drawImage(croppedImage, cordX, cordY, Settings.cellSize, Settings.cellSize, this);
                
                if ((x + y) % 2 == 0)
                {
                    g.setColor(Settings.tileOverlay);

                    g.fillRect(cordX, cordY, Settings.cellSize, Settings.cellSize);
                }

                break;
        }
    }
    
    private void paintPieces(Graphics g)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        String piece;
        long bitboard;
        for (String key : bitboards.keySet())
        {
            piece = key;
            bitboard = bitboards.get(key);
        
            paintPiece(g, piece, bitboard);
        }
    }
    
    private void paintPiece(Graphics g, String piece, long bitboard)
    {
        BufferedImage image = pieceImages.get(piece);
        
        int addOffset = (Settings.cellSize - Settings.pieceSize) / 2;
        
        long bit;

        int file, rank;

        int xBaseOffset, yBaseOffset;
        int x, y;
        for (int i = 0; i < 64; i++)
        {
            bit = Binary.getBit(bitboard, i);

            if (bit == 1L)
            {
                file = (i % 8);
                rank = Math.floorDiv(i, 8);

                xBaseOffset = file * Settings.cellSize;
                x = xBaseOffset + addOffset;

                yBaseOffset = rank * Settings.cellSize;
                y = yBaseOffset + addOffset;

                g.drawImage(image, x, y, Settings.pieceSize, Settings.pieceSize, this);
            }
        }
    }
    
    private void paintHighlights(Graphics g)
    {
        Highlight highlight;
        
        Point point;
        Color color;
        
        int size;
        
        Highlight.Shape shape;
        
        int pivotX, pivotY;
        
        int cordX, cordY;
        for (int x = 0; x < Settings.cellCount; x++)
        {
            for (int y = 0; y < Settings.cellCount; y++)
            {
                cordX = x * Settings.cellSize;
                cordY = y * Settings.cellSize;
                
                for (int i = 0; i < highlights.size(); i++)
                {
                    highlight = highlights.get(i);

                    point = highlight.point;
                    color = highlight.color;

                    size = highlight.size;

                    shape = highlight.shape;

                    if (point.x == x && point.y == y)
                    {
                        pivotX = cordX + (Settings.cellSize - size) / 2;
                        pivotY = cordY + (Settings.cellSize - size) / 2;

                        g.setColor(color);

                        switch(shape)
                        {
                            case SQUARE:
                                g.fillRect(pivotX, pivotY, size, size);

                                break;
                            case CIRCLE:
                                g.fillOval(pivotX, pivotY, size, size);

                                break;
                        }
                    }
                }
            }
        }
    }
    
    private BufferedImage getImage(String path)
    {
        try
        {
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(path));
            
            return image;
            
        } catch (IOException e)
        {
            return null;
        }
    }
    
    private BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height)
    {
        BufferedImage crop = image.getSubimage(x, y, width, height);
        
        return crop;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
