package Essentials;

/**
 *
 * @author Leo Pilhamre
 */
public class Binary {
    
    public static long shift(long bitboard, int offset)
    {
        return (offset > 0) ? bitboard >> offset : bitboard << Math.abs(offset);
    }
    
    public static long getStandaloneBit(int index)
    {
        long bitboard = Constants.zero;
        
        bitboard = setBit(bitboard, index, 1L);
        
        return bitboard;
    }
    
    public static long setBit(long b1, int index, long value)
    {
        return value == 1 ? (b1 | (1L << index)) : (b1 & ~(1L << index));
    }
    
    public static long getBit(long bits, int index)
    {
        return (bits >> index) & 1;
    }
    
    public static long OR(long b1, long b2)
    {
        return b1 | b2;
    }
    
    public static long AND(long b1, long b2)
    {
        return b1 & b2;
    }
    
    public static long XOR(long b1, long b2)
    {
        return b1 ^ b2;
    }
    
    public static long NOT(long b1, long b2)
    {
        return XOR(AND(b1, b2), b1);
    }
    
    public static long COMPLEMENT(long b1)
    {
        return ~b1;
    }
    
}
