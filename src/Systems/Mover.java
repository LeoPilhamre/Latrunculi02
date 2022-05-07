package Systems;

import Essentials.Binary;
import Essentials.Global;
import java.util.Map;

/**
 *
 * @author Leo Pilhamre
 */
public class Mover {
    
    public static void move(String piece, int index, int targetIndex)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long bitboard = bitboards.get(piece);
        
        bitboard = Binary.setBit(bitboard, index, 0L);
        
        removeAtIndex(targetIndex);
        bitboard = Binary.setBit(bitboard, targetIndex, 1L);
        
        Global.board.setBoard(piece, bitboard);
    }
    
    private static void removeAtIndex(int index)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long bitboard;
        for (String key : bitboards.keySet())
        {
            bitboard = bitboards.get(key);
            
            bitboard = Binary.setBit(bitboard, index, 0L);
            
            bitboards.put(key, bitboard);
        }
        
        Global.board.setBoard(bitboards);
    }
    
}
