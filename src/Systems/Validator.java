package Systems;

import Essentials.Binary;
import Essentials.Constants;
import Essentials.Global;
import Essentials.Moves;
import Essentials.Settings;
import java.util.ArrayList;
import java.util.List;
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
        
        int x = index % Settings.cellCount;
        int y = Math.floorDiv(index, Settings.cellCount);
        
        long validMoves;
        validMoves = Constants.zero;
        
        List<Long> moveBitboards = new ArrayList<Long>();
        long moveBitboard;
        
        long attackBitboard;
        
        int dir = 1;
        String color = "W";
        if (piece.startsWith("B"))
        {
            color = "B";
            dir = -1;
        }
        
        String pieceChar = piece.substring(1);
        switch(pieceChar)
        {
            case "P":
                //https://www.chessprogramming.org/Pawn_Attacks_(Bitboards)#PawnAttacks
                
                // Attack.
                long attackBitboardTR = getAttackBitboard(color, 7 * dir);
                attackBitboardTR = Binary.NOT(attackBitboardTR, (dir == 1) ? Constants.aFile : Constants.hFile);
                
                long attackBitboardTL = getAttackBitboard(color, 9 * dir);
                attackBitboardTL = Binary.NOT(attackBitboardTL, (dir == 1) ? Constants.hFile : Constants.aFile);
                
                attackBitboard = mergeBitboards(attackBitboardTR, attackBitboardTL);
                
                long standaloneBit = Binary.getStandaloneBit(index);
                // Move.
                moveBitboard = getMoveBitboard(standaloneBit, 8 * dir);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = mergeBitboards(moveBitboards);
                
                // Double Move.
                long initialBitboard = (dir == 1) ? Constants.WP : Constants.BP;
                initialBitboard = Binary.AND(standaloneBit, initialBitboard);
                
                long doubleMove = Constants.zero;
                if (!isColliding(color, 8 * dir))
                    doubleMove = getMoveBitboard(initialBitboard, 16 * dir);
                
                //TODO: En Passant
                
                // Apply to valid moves.
                validMoves = mergeBitboards(validMoves, attackBitboard, moveBitboard, doubleMove);
                
                break;
            case "R":
                validMoves = Moves.straight(x, y, color);
                
                break;
            case "N":
                // Move.
                moveBitboard = getAllBitboard(color, -17);
                moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -10);
                moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
                moveBitboard = Binary.NOT(moveBitboard, Constants.bFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 6);
                moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
                moveBitboard = Binary.NOT(moveBitboard, Constants.bFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 15);
                moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -15);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -6);
                moveBitboard = Binary.NOT(moveBitboard, Constants.gFile);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 10);
                moveBitboard = Binary.NOT(moveBitboard, Constants.gFile);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 17);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = mergeBitboards(moveBitboards);
                
                // Apply to valid moves.
                validMoves = mergeBitboards(validMoves, moveBitboard);

                break;
            case "B":
                validMoves = Moves.diagonal(x, y, color);
                
                break;
            case "K":
                // Move.
                moveBitboard = getAllBitboard(color, 1);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 9);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 8);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, 7);
                moveBitboard = Binary.NOT(moveBitboard, + Constants.aFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -1);
                moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -9);
                moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -8);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = getAllBitboard(color, -7);
                moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
                moveBitboards.add(moveBitboard);
                
                moveBitboard = mergeBitboards(moveBitboards);
                
                // Apply to valid moves.
                validMoves = mergeBitboards(validMoves, moveBitboard);
                
                break;
            case "Q":
                long diagonal = Moves.diagonal(x, y, color);
                long straight = Moves.straight(x, y, color);
                
                validMoves = mergeBitboards(diagonal, straight);
                
                break;
        }
        
        return validMoves;
    }
    
    public static long mergeBitboards(long... bitboards)
    {
        long mergedBitboard = Constants.zero;
        
        for (long bitboard : bitboards)
        {
            mergedBitboard = Binary.OR(mergedBitboard, bitboard);
        }
        
        return mergedBitboard;
    }
    
    public static long mergeBitboards(List<Long> bitboards)
    {
        long mergedBitboard = Constants.zero;
        
        for (long bitboard : bitboards)
        {
            mergedBitboard = Binary.OR(mergedBitboard, bitboard);
        }
        
        return mergedBitboard;
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
    
    public static boolean isColliding(String color, int offset)
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
            if (!piece.startsWith(color))
                continue;
            
            bits = bitboards.get(piece);
            
            validBitboard = Binary.NOT(bitboard, bits);
            
            if (bitboard != validBitboard)
                return true;
        }
        
        return false;
    }
    
}
