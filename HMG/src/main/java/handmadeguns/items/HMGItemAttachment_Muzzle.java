package handmadeguns.items;


public class HMGItemAttachment_Muzzle extends HMGItemAttachment_Suppressor {
    boolean changeSound = false;
    float soundlevel = 1;
    String soundname = "handmadeguns:handmadeguns.supu";
    boolean changeFlash = false;
    String Flashname = null;
    public HMGItemAttachment_Muzzle() {
        this.maxStackSize = 1;
    }
}
