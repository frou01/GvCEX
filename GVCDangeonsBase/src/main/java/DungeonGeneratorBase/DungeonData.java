package DungeonGeneratorBase;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DungeonData {

    public Map<BlockPos, Block> blocks = new HashMap();
    public Map<BlockPos, Integer> metas = new HashMap();
//    public Map<BlockPos, Block> blocks2 = new HashMap();
//    public Map<BlockPos, Integer> metas2 = new HashMap();
//    public Map<BlockPos, Block> blocks3 = new HashMap();
//    public Map<BlockPos, Integer> metas3 = new HashMap();
    public Map<BlockPos, AddSpawnerHelper> spawnerpos = new HashMap();
    public Map<BlockPos, AddChestHelper> chests = new HashMap();
    public Map<BlockPos, Class<? extends Entity>> entitys = new HashMap();
    public Map<BlockPos, String> turrets = new HashMap();
    public int[] offset = new int[]{0,0,0};
    public int levelhightoffset = 0;
    public boolean fixed_from_Ground = false;
    public boolean fillStone_to_Ground = false;
    public boolean overWrite_Ocean = false;
    public int minx = -1;
    public int miny = -1;
    public int minz = -1;
    public int maxx = -1;
    public int maxy = -1;
    public int maxz = -1;
    public int offsetx = -1;
    public int offsety = -1;
    public int offsetz = -1;

    public int minHeight = 0;
    public int MaxHeight = 256;
    public int placeAir = 0;


    public void setBlock(int x, int y, int z, Block block){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        blocks.put(pos,block);
    }
    public void setBlock(int x, int y, int z, Block block, int meta){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        blocks.put(pos,block);
        metas.put(pos,meta);
    }
    public void setBlock(int x, int y, int z, Block block, int meta,int mode){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        blocks.put(pos,block);
        metas.put(pos,meta);
    }
    public void setSpawner(int x, int y, int z,String string){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        AddSpawnerHelper spawnerHelper = new AddSpawnerHelper();
        spawnerHelper.entityname = string;
        spawnerpos.put(pos,spawnerHelper);
    }
    public void addEntity(int x, int y, int z, Class<? extends Entity> entityClass){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        entitys.put(pos,entityClass);
    }
    public void addTurret(int x, int y, int z, String gunname,float defaultyaw){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        turrets.put(pos,gunname + ";" + defaultyaw);
    }
    public void addTurretwithOffset(int x, int y, int z, String gunname,float defaultyaw,float ofX,float ofY,float ofZ){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        turrets.put(pos,gunname + ";" + defaultyaw + ";" + ofX + ";" + ofY + ";" + ofZ);
    }
    public void addChests(int x,int y,int z,String[] items){
        BlockPos pos = new BlockPos(0,0,0);
        pos.x = x;
        pos.y = y;
        pos.z = z;
        chests.put(pos,new AddChestHelper(items));
    }
//    public void setblock2(int x,int y,int z,Block block){
//        BlockPos pos = new BlockPos(0,0,0);
//        pos.x = x;
//        pos.y = y;
//        pos.z = z;
//        blocks2.put(pos,block);
//    }
//    public void setblock2(int x,int y,int z,Block block,int meta){
//        BlockPos pos = new BlockPos(0,0,0);
//        pos.x = x;
//        pos.y = y;
//        pos.z = z;
//        blocks2.put(pos,block);
//        metas2.put(pos,meta);
//    }
//    public void setblock3(int x,int y,int z,Block block){
//        BlockPos pos = new BlockPos(0,0,0);
//        pos.x = x;
//        pos.y = y;
//        pos.z = z;
//        blocks3.put(pos,block);
//    }
//    public void setblock3(int x,int y,int z,Block block,int meta){
//        BlockPos pos = new BlockPos(0,0,0);
//        pos.x = x;
//        pos.y = y;
//        pos.z = z;
//        blocks3.put(pos,block);
//        metas3.put(pos,meta);
//    }
    public static DungeonData loadDungeon(BufferedReader bufferedReader){
        {
            try {
                DungeonData temp = new DungeonData();
                String line;
                int minx = -1;
                int miny = -1;
                int minz = -1;
                int maxx = -1;
                int maxy = -1;
                int maxz = -1;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        String[] calm = line.split(",");

                        if (calm[0].equals("Fixed_as_Ground")) {
                            temp.fixed_from_Ground = true;
                        } else if (calm[0].equals("OverWrite_Ocean")) {
                            temp.overWrite_Ocean = true;
                        } else if (calm[0].equals("FillStone_to_Ground")) {
                            temp.fillStone_to_Ground = true;
                        } else if (calm[0].equals("levelhightoffset")) {
                            temp.levelhightoffset = Integer.parseInt(calm[1]);
                        } else if (calm[0].equals("Offset")) {
                            int offsetx = Integer.parseInt(calm[1]);
                            int offsety = Integer.parseInt(calm[2]);
                            int offsetz = Integer.parseInt(calm[3]);
                            temp.offset = new int[]{offsetx, offsety, offsetz};
                        } else if (calm[0].equals("Min/MaxHeight")) {
                            temp.MaxHeight = Integer.parseInt(calm[1]);
                            temp.minHeight = Integer.parseInt(calm[2]);
                        } else if (calm[0].equals("PlaceAir")) {
                            temp.placeAir = Integer.parseInt(calm[1]);
                        } else if (calm[0].equals("Chest")) {
                            String[] tempItems = new String[27];
                            for(int id = 0;id < 27 && id + 4 < calm.length;id++){
                                tempItems[id] = calm[id + 4];
                            }
                            temp.addChests(Integer.parseInt(calm[1]),
                                    Integer.parseInt(calm[2]),
                                    Integer.parseInt(calm[3]),tempItems);
                        } else if (calm[0].equals("Entity")) {
                            int setx = Integer.parseInt(calm[1]);
                            int sety = Integer.parseInt(calm[2]);
                            int setz = Integer.parseInt(calm[3]);
                            temp.addEntity(setx, sety, setz, (Class<? extends EntityLivingBase>) EntityList.stringToClassMapping.get(calm[4]));
//                        System.out.println("debug " + EntityList.stringToClassMapping.get(calm[4]));
                        } else if (calm[0].equals("Turret")) {
                            int setx = Integer.parseInt(calm[1]);
                            int sety = Integer.parseInt(calm[2]);
                            int setz = Integer.parseInt(calm[3]);
                            temp.addTurret(setx, sety, setz, calm[4], Float.parseFloat(calm[5]));
//                        System.out.println("debug " + EntityList.stringToClassMapping.get(calm[4]));
                        } else if (calm.length == 5 && calm[3].equals("Entity")) {
                            int setx = Integer.parseInt(calm[0]);
                            int sety = Integer.parseInt(calm[1]);
                            int setz = Integer.parseInt(calm[2]);
                            temp.addEntity(setx, sety, setz, (Class<? extends EntityLivingBase>) EntityList.stringToClassMapping.get(calm[4]));
//                        System.out.println("debug " + EntityList.stringToClassMapping.get(calm[4]));
                        } else if (calm.length == 6 && calm[3].equals("Turret")) {
                            int setx = Integer.parseInt(calm[0]);
                            int sety = Integer.parseInt(calm[1]);
                            int setz = Integer.parseInt(calm[2]);
                            temp.addTurret(setx, sety, setz, calm[4], Float.parseFloat(calm[5]));
//                        System.out.println("debug " + EntityList.stringToClassMapping.get(calm[4]));
                        } else if (calm.length == 9 && calm[3].equals("Turret")) {
                            int setx = Integer.parseInt(calm[0]);
                            int sety = Integer.parseInt(calm[1]);
                            int setz = Integer.parseInt(calm[2]);
                            temp.addTurretwithOffset(setx, sety, setz, calm[4], Float.parseFloat(calm[5]),
                                    Float.parseFloat(calm[6]),
                                    Float.parseFloat(calm[7]),
                                    Float.parseFloat(calm[8]));
//                        System.out.println("debug " + EntityList.stringToClassMapping.get(calm[4]));
                        } else if (calm[0].equals("Spawner")) {
                            temp.setSpawner(Integer.parseInt(calm[1]), Integer.parseInt(calm[2]), Integer.parseInt(calm[3]), calm[4]);
                        } else if(calm.length>3){
                            String[] block = calm[3].split(":");
                            if (
                                    minx == miny &&
                                            miny == minz &&
                                            minz == -1) {
                                minx = Integer.parseInt(calm[0]);
                                miny = Integer.parseInt(calm[1]);
                                minz = Integer.parseInt(calm[2]);
                            }

                            minx = Integer.parseInt(calm[0]) < minx ? Integer.parseInt(calm[0]) : minx;
                            miny = Integer.parseInt(calm[1]) < miny ? Integer.parseInt(calm[1]) : miny;
                            minz = Integer.parseInt(calm[2]) < minz ? Integer.parseInt(calm[2]) : minz;

                            maxx = Integer.parseInt(calm[0]) > maxx ? Integer.parseInt(calm[0]) : maxx;
                            maxy = Integer.parseInt(calm[1]) > maxy ? Integer.parseInt(calm[1]) : maxy;
                            maxz = Integer.parseInt(calm[2]) > maxz ? Integer.parseInt(calm[2]) : maxz;
                            if (calm.length > 4)
                                temp.setBlock(Integer.parseInt(calm[0]), Integer.parseInt(calm[1]), Integer.parseInt(calm[2]), GameRegistry.findBlock(block[0], block[1]), Integer.parseInt(calm[4]));
                            else
                                temp.setBlock(Integer.parseInt(calm[0]), Integer.parseInt(calm[1]), Integer.parseInt(calm[2]), GameRegistry.findBlock(block[0], block[1]), 0);
                        }
                    }
                }
                temp.minx = minx - 1;
                temp.miny = miny - 1;
                temp.minz = minz - 1;
                temp.maxx = maxx + 1;
                temp.maxy = maxy + 1;
                temp.maxz = maxz + 1;
                return temp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static boolean checkBeforeReadfile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canRead()) {
                return true;
            }
        }

        return false;
    }
}
