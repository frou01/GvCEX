package handmadevehicle.command;

import handmadevehicle.AddNewVehicle;
import handmadevehicle.AddWeapon;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.model.ModelFormatException;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadevehicle.HMVehicle.HMV_Proxy;

public class HMV_CommandReloadparm extends CommandBase implements ICommand{

    @Override
    public String getCommandName() {
        return "HMV_ReloadALL";
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        System.out.println(""+var1);
    
        File packdir = new File(HMV_Proxy.ProxyFile(), "handmadeVehicles_Packs");
        packdir.mkdirs();
        {
        
            File[] packlist = packdir.listFiles();
            Arrays.sort(packlist, new Comparator<File>() {
                public int compare(File file1, File file2){
                    return file1.getName().compareTo(file2.getName());
                }
            });
        
            for (File apack : packlist) {
            
            
                File weaponDir = new File(apack, "AddWeapon");
                File[] fileWeapon = weaponDir.listFiles();
                Arrays.sort(fileWeapon, new Comparator<File>() {
                    public int compare(File file1, File file2) {
                        return file1.getName().compareTo(file2.getName());
                    }
                });
                for (int num = 0; num < fileWeapon.length; num++) {
                    if (fileWeapon[num].isFile()) {
                        try {
                            AddWeapon.load(true, fileWeapon[num]);
                        } catch (ModelFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            
                File vehicleDir = new File(apack, "AddVehicle");
                File[] fileVehicle = vehicleDir.listFiles();
                Arrays.sort(fileVehicle, new Comparator<File>() {
                    public int compare(File file1, File file2) {
                        return file1.getName().compareTo(file2.getName());
                    }
                });
                for (int num = 0; num < fileVehicle.length; num++) {
                    if (fileVehicle[num].isFile()) {
                        try {
                            new AddNewVehicle().load(true, fileVehicle[num]);
                        } catch (ModelFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}