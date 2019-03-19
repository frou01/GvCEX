package handmadeguns.Util;

public class TrailInfo {
    public boolean enabletrai       = false;
    public float   trailProbability   = 0.2f;
    public int     traillength          = 3;
    public float   trailWidth         = 0.2f;
    public String  trailtexture      = null;
    public String  smoketexture      = null;
    public float   smokeWidth         = 1f;
    public int     smoketime          = 5;
    public boolean  trailglow      = true;
    public boolean  smokeglow      = true;

    public TrailInfo(boolean enable,float prob,int length,float width,String texture,String smoke,float smokeW,int smoketime,boolean trailglow,boolean smokeglow){
        this.enabletrai = enable;
        this.trailProbability = prob;
        this.traillength = length;
        this.trailWidth = width;
        this.trailtexture = texture;
        this.smoketexture = smoke;
        this.smokeWidth = smokeW;
        this.smoketime = smoketime;
        this.trailglow = trailglow;
        this.smokeglow = smokeglow;
    }
}
