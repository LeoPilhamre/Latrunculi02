package Systems;

import Essentials.Binary;
import Essentials.Constants;
import Essentials.Global;
import Essentials.Moves;
import java.util.Map;

/**
 *
 * @author Leo Pilhamre
 */
public class Validator {
    
    private static int index;
    
    
    public static long getValidMoves(String piece, int index)
    {
        Validator.index = index;
        
        long validMoves;
        validMoves = Constants.zero;
        
        int dir = 1;
        String color = "W";
        if (piece.startsWith("B"))
        {
            color = "B";
            dir = -1;
        }
        
        String pieceChar = piece.substring(1);
        switch (pieceChar)
        {
            case "P":
                validMoves = Moves.getPawnMoves(color, dir, index);
                
                break;
            case "R":
                validMoves = Moves.getRockMoves(color, index);
                
                break;
            case "N":
                validMoves = Moves.getKnightMoves(color);

                break;
            case "B":
                validMoves = Moves.getBishopMoves(color, index);
                
                break;
            case "K":
                validMoves = Moves.getKingMoves(color);
                
                break;
            case "Q":
                validMoves = Moves.getQueenMoves(color, index);
                
                break;
        }
        
        return validMoves;
    }
    
    public static boolean isInCheck(String color)
    {
        int dir = (color == "W") ? 1 : -1;
        
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long kingBitboard = bitboards.get(color + "K");
        
        String piece;
        long bitboard;
        for (String key : bitboards.keySet())
        {
            piece = key;
            if (piece.startsWith(color))
                continue;
            
            bitboard = bitboards.get(piece);
            
            
        }
        
        
        return false;
    }
    
    
    public static long getAttackBitboard(String color, int offset)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long validBitboard = Constants.zero;
        
        long bitboard = Binary.getStandaloneBit(index);
        
        long attackBitboard = Binary.shift(bitboard, offset);
        
        String piece;
        long bits;
        for (String key : bitboards.keySet())
        {
            piece = key;
            if (piece.startsWith(color))
                continue;
            
            bits = bitboards.get(piece);

            validBitboard = Binary.OR(validBitboard, Binary.AND(attackBitboard, bits));
        }
        
        return validBitboard;
    }
    
    public static long getMoveBitboard(long bitboard, int offset)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long moveBitboard = Binary.shift(bitboard, offset);
        
        long validBitboard = moveBitboard;
        
        long bits;
        for (String key : bitboards.keySet())
        {
            bits = bitboards.get(key);

            validBitboard = Binary.XOR(validBitboard, Binary.NOT(moveBitboard, bits));
        }
        
        return validBitboard;
    }
    
    public static long getAllBitboard(String color, int offset)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long bitboard = Binary.getStandaloneBit(index);
        bitboard = Binary.shift(bitboard, offset);
        
        long validBitboard = bitboard;
        
        String piece;
        long bits;
        for (String key : bitboards.keySet())
        {
            piece = key;
            if (!piece.startsWith(color))
                continue;
            
            bits = bitboards.get(piece);

            validBitboard = Binary.NOT(validBitboard, bits);
        }
        
        return validBitboard;
    }
    
    public static boolean isColliding(int offset)
    {
        Map<String, Long> bitboards = Global.board.getBoard();
        
        long bitboard = Binary.getStandaloneBit(index);
        
        bitboard = Binary.shift(bitboard, offset);
        
        long validBitboard;
        
        String piece;
        long bits;
        for (String key : bitboards.keySet())
        {
            piece = key;
            
            bits = bitboards.get(piece);
            
            validBitboard = Binary.NOT(bitboard, bits);
            
            if (bitboard != validBitboard)
                return true;
        }
        
        return false;
    }
    
}
