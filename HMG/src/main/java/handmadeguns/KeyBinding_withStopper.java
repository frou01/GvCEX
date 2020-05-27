package handmadeguns;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBinding_withStopper {
    public KeyBinding keyBinding;
    public boolean stopper;
    public KeyBinding_withStopper(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_){
        keyBinding = new KeyBinding(p_i45001_1_,p_i45001_2_,p_i45001_3_);
        stopper = false;
    }

    public boolean isKeyDown(){
        if(keyBinding.getKeyCode() == Keyboard.KEY_NONE)return false;
        boolean flag = Keyboard.isKeyDown(keyBinding.getKeyCode());
        if (!flag) stopper = false;
        if (stopper) {
            return false;
        } else if (flag) {
            stopper = true;
            return true;
        } else return false;
    }

}
