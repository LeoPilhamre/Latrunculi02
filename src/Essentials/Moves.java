package Essentials;

import Systems.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo Pilhamre
 */
public class Moves extends Validator {
    
    public static long diagonal(int x, int y, String color)
    {
        long moveBitboard = Constants.zero;
        
        int rowIndex;
                
        // NW
        rowIndex = 1;
        while (x - rowIndex >= 0)
        {
            if (isColliding(color, 8 * rowIndex + rowIndex))
                break;

            moveBitboard = Binary.OR(moveBitboard, getAllBitboard(color, 8 * rowIndex + rowIndex));
            rowIndex++;
        }

        // NE
        rowIndex = 1;
        while (x + rowIndex + 1 <= 8)
        {
            if (isColliding(color, 8 * rowIndex - rowIndex))
                break;

            moveBitboard = Binary.OR(moveBitboard, getAllBitboard(color, 8 * rowIndex - rowIndex));
            rowIndex++;
        }

        // SW
        rowIndex = 1;
        while (x - rowIndex >= 0)
        {
            if (isColliding(color, -8 * rowIndex + rowIndex))
                break;

            moveBitboard = Binary.OR(moveBitboard, getAllBitboard(color, -8 * rowIndex + rowIndex));
            rowIndex++;
        }

        // SE
        rowIndex = 1;
        while (x + rowIndex + 1 <= 8)
        {
            if (isColliding(color, -8 * rowIndex - rowIndex))
                break;

            moveBitboard = Binary.OR(moveBitboard, getAllBitboard(color, -8 * rowIndex - rowIndex));
            rowIndex++;
        }

        // Apply to valid moves.
        long validMoves = mergeBitboards(moveBitboard);
        
        return validMoves;
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
            if (isColliding(color, i + 1))
                break;

            bitboard = Binary.OR(bitboard, getAllBitboard(color, i + 1));
        }
        // Right.
        for (int i = 0; i < negX; i++)
        {
            if (isColliding(color, (-(i + 1))))
                break;

            bitboard = Binary.OR(bitboard, getAllBitboard(color, (-(i + 1))));
        }

        // Up.
        for (int i = 0; i < y; i++)
        {
            if (isColliding(color, (i + 1) * 8))
                break;

            bitboard = Binary.OR(bitboard, getAllBitboard(color, (i + 1) * 8));
        }
        // Down.
        for (int i = 0; i < negY; i++)
        {
            if (isColliding(color, (-(i + 1)) * 8))
                break;

            bitboard = Binary.OR(bitboard, getAllBitboard(color, (-(i + 1)) * 8));
        }

        // Apply to valid moves.
        long validMoves = bitboard;
        
        return validMoves;
    }
    
}
