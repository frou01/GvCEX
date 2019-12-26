package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.HMGGunMaker;
import handmadeguns.network.PacketReloadparm;
import net.minecraftforge.client.model.ModelFormatException;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatch_Reloadparm implements IMessageHandler<PacketReloadparm, IMessage> {
    @Override
    public IMessage onMessage(PacketReloadparm message, MessageContext ctx) {
        System.out.println("debug");
        File packdir = new File(HMG_proxy.ProxyFile(), "handmadeguns_Packs");
        packdir.mkdirs();
        File[] packlist = packdir.listFiles();
        Arrays.sort(packlist, new Comparator<File>() {
            public int compare(File file1, File file2){
                return file1.getName().compareTo(file2.getName());
            }
        });
        for (File apack : packlist) {
            File direjs = new File(apack, "addscripts");

            File diregun = new File(apack, "guns");
            File[] filegun = diregun.listFiles();
            Arrays.sort(filegun, new Comparator<File>() {
                public int compare(File file1, File file2) {
                    return file1.getName().compareTo(file2.getName());
                }
            });
            for (int ii = 0; ii < filegun.length; ii++) {
                if (filegun[ii].isFile()) {
                    try {
                        new HMGGunMaker().load(true, filegun[ii]);
                    } catch (ModelFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
