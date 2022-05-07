package Essentials;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Leo Pilhamre
 */
public class Board {
    
    private Map<String, Long> bitboards;

    public Board() {
        this.bitboards = new HashMap<String, Long>() {};
    }
    
    
    /**
     * Resets the board to default situation.
     */
    public void resetBoard()
    {
        this.bitboards.put("WP", Constants.WP);
        this.bitboards.put("WR", Constants.WR);
        this.bitboards.put("WN", Constants.WN);
        this.bitboards.put("WB", Constants.WB);
        this.bitboards.put("WK", Constants.WK);
        this.bitboards.put("WQ", Constants.WQ);
        
        this.bitboards.put("BP", Constants.BP);
        this.bitboards.put("BR", Constants.BR);
        this.bitboards.put("BN", Constants.BN);
        this.bitboards.put("BB", Constants.BB);
        this.bitboards.put("BK", Constants.BK);
        this.bitboards.put("BQ", Constants.BQ);
    }
    
    /**
     * Gets the bitboards.
     * @return bitboards
     */
    public Map<String, Long> getBoard()
    {
        return this.bitboards;
    }
    
    public void setBoard(Map<String, Long> bitboards)
    {
        this.bitboards = bitboards;
    }
    
    public void setBoard(String piece, long bitboard)
    {
        this.bitboards.put(piece, bitboard);
    }
    
    public String getPieceAtPoint(Point point)
    {
        int i = getIndexOfPoint(point);
        
        long bitboard;
        
        long bit;
        for (String key : this.bitboards.keySet())
        {
            bitboard = this.bitboards.get(key);
            
            bit = Binary.getBit(bitboard, i);
            
            if (bit == 1L)
            {
                return key;
            }
        }
        
        return null;
    }
    
    public int getIndexOfPoint(Point point)
    {
        return (point.x + (point.y * 8));
    }
    
}
