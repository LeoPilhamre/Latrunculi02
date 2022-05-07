package Controllers;

import Essentials.Global;
import Systems.Player;

/**
 *
 * @author Leo Pilhamre
 */
public class GameManager {
    
    public void start()
    {
        Player player = new Player();
        
        Global.mainPanel.addMouseListener(player);
    }
    
}
