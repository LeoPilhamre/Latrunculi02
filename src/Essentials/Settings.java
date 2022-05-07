package Essentials;

import java.awt.Color;

/**
 *
 * @author Leo Pilhamre
 */
public class Settings {
    
    /**
     * 
     */
    
    public enum FrameXOptions
    {
        LEFT,
        MIDDLE,
        RIGHT
    }
    
    public enum FrameYOptions
    {
        TOP,
        MIDDLE,
        BOTTOM
    }
    
    public enum PanelYOptions
    {
        TOP,
        MIDDLE,
        BOTTOM
    }
    
    public static int frameSizeX = 800;
    public static int frameSizeY = 400;
    
    public static FrameXOptions frameXOption = FrameXOptions.MIDDLE;
    public static FrameYOptions frameYOption = FrameYOptions.MIDDLE;
    
    public static Color backgroundColor = new Color(70, 70, 70);
    
    public static int panelX = 65;
    public static PanelYOptions panelYOption = PanelYOptions.MIDDLE;
    
    public static Color panelBorderColor = new Color(255, 255, 255, 150);
    public static int panelBorderThickness = 3;
    public static boolean panelBorderRounded = false;
    
    public static int cellSize = 45;
    public static int cellCount = 8;
    
    public static int validSize = 10;
    public static Color validColor = new Color(255, 0, 0, 100);
    
    public enum BoardMode
    {
        NORMAL,
        FANCY
    }
    
    public static BoardMode boardMode = BoardMode.FANCY;
    
    public static Color tileColor01 = new Color(160, 134, 113);
    public static Color tileColor02 = new Color(72, 62, 53);
    
    public static Color pressOverlayColor = new Color(255, 255, 0, 50);
    
    public static String backgroundImagePath = "/background04.jpg";
    public static Color tileOverlay = new Color(255, 0, 0, 100);
    
    public static int pieceSize = 45;
    
    public static String piecePath(String piece)
    {
        return "/" + piece + ".png";
    }
    
}
