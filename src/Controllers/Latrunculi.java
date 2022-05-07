package Controllers;

import Essentials.Board;
import Essentials.Global;
import Essentials.Settings;
import GUI.MainFrame;
import GUI.MainPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author Leo Pilhamre
 */
public class Latrunculi {

    /**
     * @param argv the command line arguments
     */
    public static void main(String[] argv) {
        
        // Assign new instances of all member variables of class 'Global'.
        Board board = new Board();
        board.resetBoard();
        
        Global.board = board;
        
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel();
        
        Global.mainFrame = mainFrame;
        Global.mainPanel = mainPanel;
        
        GameManager gameManager = new GameManager();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        
            applyPanelSettings(mainPanel);
            
            applyFrameSettings(mainFrame, mainPanel);
            
            gameManager.start();
            
        });
        
    }
    
    private static void applyFrameSettings(MainFrame mainFrame, MainPanel mainPanel)
    {
        mainFrame.add(mainPanel);
            
        setFrameTransform(mainFrame);
        
        mainFrame.getContentPane().setBackground(Settings.backgroundColor);
        
        mainFrame.setResizable(false);
        
        mainFrame.setVisible(true);
    }
    
    private static void applyPanelSettings(MainPanel mainPanel)
    {
        setPanelTransform(mainPanel);
        
        setPanelBorder(mainPanel);
        
        mainPanel.setBackground(new Color(0, 0, 0, 0));                         // Transparent background
    }
    
    private static void setFrameTransform(MainFrame mainFrame)
    {
        Dimension frameDimension = getFrameDimension(mainFrame);

        mainFrame.setSize(frameDimension);
        mainFrame.setLocationRelativeTo(null);                                  // Center
    }
    
    private static Dimension getFrameDimension(MainFrame mainFrame)
    {
        int paddingX = mainFrame.getInsets().left + mainFrame.getInsets().right;
        int paddingY = mainFrame.getInsets().top + mainFrame.getInsets().bottom;
        int frameSizeX = Settings.frameSizeX + paddingX;
        int frameSizeY = Settings.frameSizeY + paddingY;
        
        Dimension dimension = new Dimension(frameSizeX, frameSizeY);
        
        return dimension;
    }
    
    private static void setPanelTransform(MainPanel mainPanel)
    {
        int panelSize = (int) Settings.cellSize * Settings.cellCount;
        
        Dimension panelDimension = getPanelDimension(panelSize);
        Point panelLocation = getPanelLocation(panelSize);
        
        mainPanel.setSize(panelDimension);
        mainPanel.setLocation(panelLocation);
    }
    
    private static Dimension getPanelDimension(int panelSize)
    {
        Dimension dimension = new Dimension(panelSize, panelSize);
        
        return dimension;
    }
    
    private static Point getPanelLocation(int panelSize)
    {
        int panelY;
        switch (Settings.panelYOption)
        {
            case TOP:
                panelY = 0;

                break;
            case MIDDLE:
                panelY = (int) (Settings.frameSizeY - panelSize) / 2;

                break;
            case BOTTOM:
                panelY = Settings.frameSizeY - panelSize;

                break;
            default:
                panelY = 0;
        }
        
        Point point = new Point(Settings.panelX, panelY);
        
        return point;
    }
    
    private static void setPanelBorder(MainPanel mainPanel)
    {
        Border border = BorderFactory.createLineBorder(Settings.panelBorderColor, Settings.panelBorderThickness, Settings.panelBorderRounded);
        
        mainPanel.setBorder(border);
    }
    
}
