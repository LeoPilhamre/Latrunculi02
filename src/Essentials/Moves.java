package Essentials;

import Systems.Validator;

/**
 *
 * @author Leo Pilhamre
 */
public class Moves extends Validator {
    
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
