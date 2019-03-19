package DungeonGeneratorBase;

import hmggvcmob.tile.TileEntityMobSpawner_Extend;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class AddSpawnerHelper {
    String entityname;
    public void setspawner(World world,int x,int y,int z){
        TileEntityMobSpawner_Extend tileentitymobspawner1 = (TileEntityMobSpawner_Extend)world.getTileEntity(x, y, z);
        if (tileentitymobspawner1 != null)tileentitymobspawner1.func_145881_a().setEntityName(entityname);
    }
    public void setnormalspawner(World world,int x,int y,int z){
        TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner)world.getTileEntity(x, y, z);
        if (tileentitymobspawner1 != null)tileentitymobspawner1.func_145881_a().setEntityName(entityname);
    }
}
