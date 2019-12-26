package handmadeguns;

import handmadeguns.Util.SoundInfo;
import handmadeguns.Util.TrailInfo;
import handmadeguns.client.render.ModelSetAndData;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HMGAddBullets {
    public static Map<String, Integer> indexlist = new HashMap<String, Integer>();
    public static Map<Integer, ModelSetAndData> modellist = new HashMap<Integer, ModelSetAndData>();
    public static Map<Integer, SoundInfo> soundlist = new HashMap<Integer, SoundInfo>();
    public static Map<Integer, SoundInfo> soundRicochetlist = new HashMap<Integer, SoundInfo>();
    public static Map<Integer, TrailInfo> trailsettings      = new HashMap<Integer, TrailInfo>();
    public static int cnt = -1;


    public static void load(boolean isClient, File file1) {
        //reset

        try {
            File file = file1;
            // File file = new File(configfile,"hmg_handmadeguns.txt");
            if (checkBeforeReadfile(file)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));

                String str;
                String BulletName;
                String objmodel;
                String objtexture;
                float objscale = 0.5f;
                String flyingsoundname;
                float flyingsoundlv = 2;
                float flyingsoundsp = 1;
                float flyingSoundminspeed = 3;
                float flyingSoundmaxdist = 16;
                String ricochetsoundname = null;
                float ricochetsoundlv = 2;
                float ricochetsoundsp = 1;
                float ricochetSoundminspeed = 3;
                float ricochetSoundmaxdist = 16;

                objmodel = null;
                objtexture = null;
                flyingsoundname = null;

                boolean enabletrai       = false;
                float   trailProbability   = 0.2f;
                int     traillength          = 3;
                float   trailWidth         = 0.2f;
                String  trailtexture      = null;
                String  smoketexture      = null;
                float  smokeWidth         = 1f;
                int     smoketime          = 5;
                boolean  trailglow      = true;
                boolean  smokeglow      = true;

                while ((str = br.readLine()) != null) { // 1�s���ǂݍ���
                    // System.out.println(str);
                    String[] type = str.split(",");

                    int guntype = 0;

                    if (type.length != 0) {// 1
                        switch (type[0]) {
                            case "ObjModel":
                                objmodel = type[1];
                                break;
                            case "ObjTexture":
                                objtexture = type[1];
                                break;
                            case "Objscale":
                                objscale = Float.parseFloat(type[1]);
                                break;
                            case "FlyingSoundName":
                                flyingsoundname = type[1];
                                break;
                            case "FlyingSoundLV":
                                flyingsoundlv = Float.parseFloat(type[1]);
                                break;
                            case "FlyingSoundSP":
                                flyingsoundsp = Float.parseFloat(type[1]);
                                break;
                            case "FlyingSound_BulletMinSpeed":
                                flyingSoundminspeed = Float.parseFloat(type[1]);
                                break;
                            case "FlyingSoundmaxdist":
                                flyingSoundmaxdist = Float.parseFloat(type[1]);
                                break;
                            case "RicochetSoundName":
                                ricochetsoundname = type[1];
                                break;
                            case "RicochetSoundLV":
                                ricochetsoundlv = Float.parseFloat(type[1]);
                                break;
                            case "RicochetSoundSP":
                                ricochetsoundsp = Float.parseFloat(type[1]);
                                break;
                            case "RicochetSound_BulletMinSpeed":
                                ricochetSoundminspeed = Float.parseFloat(type[1]);
                                break;
                            case "RicochetSoundmaxdist":
                                ricochetSoundmaxdist = Float.parseFloat(type[1]);
                                break;
                            case "EnableTrail":
                                enabletrai = Boolean.parseBoolean(type[1]);
                                break;
                            case "TrailProbability":
                                trailProbability = Float.parseFloat(type[1]);
                                break;
                            case "TrailLength":
                                traillength = Integer.parseInt(type[1]);
                                break;
                            case "TrailWidth":
                                trailWidth = Float.parseFloat(type[1]);
                                break;
                            case "Trailtexture":
                                trailtexture = type[1];
                                break;
                            case "SmokeTexture":
                                smoketexture = type[1];
                                break;
                            case "SmokeTime":
                                smoketime = Integer.parseInt(type[1]);
                                break;
                            case "SmokeWidth":
                                smokeWidth = Float.parseFloat(type[1]);
                                break;
                            case "TrailGlow":
                                trailglow = Boolean.parseBoolean(type[1]);
                                break;
                            case "SmokeGlow":
                                smokeglow = Boolean.parseBoolean(type[1]);
                                break;
                            case "Name":
            
                                ResourceLocation model = new ResourceLocation("handmadeguns:textures/model/" + objmodel);
                                ResourceLocation texture = new ResourceLocation("handmadeguns:textures/model/" + objtexture);
                                BulletName = type[1];
                                cnt++;
                                if (isClient) {
                                    System.out.println("model" + model);
                                    System.out.println("textures" + texture);
                                    if (objmodel != null && objtexture != null) {
                                        IModelCustom modeling = AdvancedModelLoader.loadModel(model);
                                        modellist.put(cnt, new ModelSetAndData(modeling, texture, objscale));
                                    }
                                    if (trailtexture != null || smoketexture != null) {
                                        System.out.println("debug " + trailtexture);
                                        trailsettings.put(cnt, new TrailInfo(enabletrai, trailProbability, traillength, trailWidth, trailtexture, smoketexture, smokeWidth, smoketime, trailglow, smokeglow));
                                    }
                                }
                                if (flyingsoundname != null)
                                    soundlist.put(cnt, new SoundInfo("handmadeguns:handmadeguns." + flyingsoundname, flyingsoundlv, flyingsoundsp, flyingSoundminspeed, flyingSoundmaxdist));
                                if (ricochetsoundname != null)
                                    soundRicochetlist.put(cnt, new SoundInfo("handmadeguns:handmadeguns." + ricochetsoundname, ricochetsoundlv, ricochetsoundsp, ricochetSoundminspeed, ricochetSoundmaxdist));
                                indexlist.put(BulletName, cnt);
                                System.out.println("debug " + BulletName);
//                            System.out.println("debug" + BulletName + " , " + modellist + " , " + objtexture);
                                break;
                        }
                    } // 1
                }
                br.close(); // �t�@�C�������
            } else {

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
