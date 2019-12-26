package DungeonGeneratorBase;

import java.util.Arrays;

public class BlockPos {
    public int x;
    public int y;
    public int z;
    public BlockPos(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public BlockPos(String x,String y,String z){
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
    }
    public boolean equals(Object o){
        return o instanceof BlockPos && this.x == ((BlockPos) o).x && this.y == ((BlockPos) o).y && this.z == ((BlockPos) o).z;
    }
    public int hashCode () {
        int[] data = new int[3];
        data[0] = x;
        data[1] = y;
        data[2] = z;
        return Arrays.  hashCode ( data );
    }

    @Override
    public String toString() {
        return "" + x +" , "+ y +" , "+ z;
    }
}
