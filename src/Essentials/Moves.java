package Essentials;

import Systems.Validator;

/**
 *
 * @author Leo Pilhamre
 */
public class Moves extends Validator {
    
    public static long getPawnMoves(String color, int dir, int index)
    {
        //https://www.chessprogramming.org/Pawn_Attacks_(Bitboards)#PawnAttacks
        
        // Attack.
        long attackBitboardTR = getAttackBitboard(color, 7 * dir);
        attackBitboardTR = Binary.NOT(attackBitboardTR, (dir == 1) ? Constants.aFile : Constants.hFile);

        long attackBitboardTL = getAttackBitboard(color, 9 * dir);
        attackBitboardTL = Binary.NOT(attackBitboardTL, (dir == 1) ? Constants.hFile : Constants.aFile);

        long attackBitboard = Binary.mergeBitboards(attackBitboardTR, attackBitboardTL);

        long standaloneBit = Binary.getStandaloneBit(index);
        // Move.
        long moveBitboard = getMoveBitboard(standaloneBit, 8 * dir);

        // Double Move.
        long initialBitboard = (dir == 1) ? Constants.WP : Constants.BP;
        initialBitboard = Binary.AND(standaloneBit, initialBitboard);

        long doubleMove = Constants.zero;
        if (!isColliding(8 * dir))
            doubleMove = getMoveBitboard(initialBitboard, 16 * dir);

        //TODO: En Passant

        // Apply to valid moves.
        long validMoves = Binary.mergeBitboards(attackBitboard, moveBitboard, doubleMove);
        
        return validMoves;

    }
    
    public static long getRockMoves(String color, int index)
    {
        int x = index % Settings.cellCount;
        int y = Math.floorDiv(index, Settings.cellCount);
        
        long validMoves = Moves.straight(x, y, color);
        
        return validMoves;
    }
    
    public static long getKnightMoves(String color)
    {
        long bitboard = Constants.zero;
        
        // Move.
        long moveBitboard = getAllBitboard(color, -17);
        moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, -10);
        moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
        moveBitboard = Binary.NOT(moveBitboard, Constants.bFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 6);
        moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
        moveBitboard = Binary.NOT(moveBitboard, Constants.bFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 15);
        moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, -15);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, -6);
        moveBitboard = Binary.NOT(moveBitboard, Constants.gFile);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 10);
        moveBitboard = Binary.NOT(moveBitboard, Constants.gFile);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 17);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        // Apply to valid moves.
        long validMoves = bitboard;
        
        return validMoves;
    }
    
    public static long getBishopMoves(String color, int index)
    {
        int x = index % Settings.cellCount;
        int y = Math.floorDiv(index, Settings.cellCount);
        
        long validMoves = Moves.diagonal(x, y, color);
        
        return validMoves;
    }
    
    public static long getKingMoves(String color)
    {
        long bitboard = Constants.zero;
        
        // Move.
        long moveBitboard = getAllBitboard(color, 1);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 9);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 8);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, 7);
        moveBitboard = Binary.NOT(moveBitboard, + Constants.aFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, -1);
        moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, -9);
        moveBitboard = Binary.NOT(moveBitboard, Constants.aFile);
        bitboard = Binary.OR(bitboard, moveBitboard);
        
        moveBitboard = getAllBitboard(color, -8);
        bitboard = Binary.OR(bitboard, moveBitboard);

        moveBitboard = getAllBitboard(color, -7);
        moveBitboard = Binary.NOT(moveBitboard, Constants.hFile);
        bitboard = Binary.OR(bitboard, moveBitboard);

        // Apply to valid moves.
        long validMoves = bitboard;
        
        return validMoves;
    }
    
    public static long getQueenMoves(String color, int index)
    {
        int x = index % Settings.cellCount;
        int y = Math.floorDiv(index, Settings.cellCount);
        
        long diagonal = Moves.diagonal(x, y, color);
        long straight = Moves.straight(x, y, color);

        long validMoves = Binary.mergeBitboards(diagonal, straight);
        
        return validMoves;
    }
            
    
    
    public static long diagonal(int x, int y, String color)
    {
        long bitboard = Constants.zero;
        
        int rowIndex;
        
        // NW
        rowIndex = 1;
        while (x - rowIndex >= 0)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, 8 * rowIndex + rowIndex));

            if (isColliding(8 * rowIndex + rowIndex))
                break;
            
            rowIndex++;
        }

        // NE
        rowIndex = 1;
        while (x + rowIndex + 1 <= 8)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, 8 * rowIndex - rowIndex));

            if (isColliding(8 * rowIndex - rowIndex))
                break;
            
            rowIndex++;
        }

        // SW
        rowIndex = 1;
        while (x - rowIndex >= 0)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, -8 * rowIndex + rowIndex));

            if (isColliding(-8 * rowIndex + rowIndex))
                break;
            
            rowIndex++;
        }

        // SE
        rowIndex = 1;
        while (x + rowIndex + 1 <= 8)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, -8 * rowIndex - rowIndex));

            if (isColliding(-8 * rowIndex - rowIndex))
                break;
            
            rowIndex++;
        }
        
        return bitboard;
    }
    
    public static long straight(int x, int y, String color)
    {
        int negX = (Settings.cellCount - x) - 1;
        int negY = (Settings.cellCount - y) - 1;
        
        // Move.
        long bitboard = Constants.zero;

        // Left.
        for (int i = 0; i < x; i++)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, i + 1));

            if (isColliding(i + 1))
                break;
        }
        // Right.
        for (int i = 0; i < negX; i++)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, (-(i + 1))));

            if (isColliding(-(i + 1)))
                break;
        }

        // Up.
        for (int i = 0; i < y; i++)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, (i + 1) * 8));

            if (isColliding((i + 1) * 8))
                break;
        }
        // Down.
        for (int i = 0; i < negY; i++)
        {
            bitboard = Binary.OR(bitboard, getAllBitboard(color, (-(i + 1)) * 8));

            if (isColliding((-(i + 1)) * 8))
                break;
        }
        
        return bitboard;
    }
    
}
