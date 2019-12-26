package handmadeguns;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemLilyPad;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class HMGAddSounds
{
	static List<ArrayList<String>> mainList = new ArrayList();
	public static List Guns = new ArrayList();

	public static String[] soundsfile = new String[255];

	public static void load(File soundsDir,File jsonDir)
	{
		File[] filelist1 = soundsDir.listFiles();
		if(filelist1 != null) {
			for (int i = 0; i < filelist1.length; i++) {
				if (filelist1[i].isFile()) {
					soundsfile[i] = getNameWithoutExtension(filelist1, i);
					//soundsfile[i] = filelist1[i].getName();
				}
			}

			try {
				File newfile = new File(jsonDir, "sounds.json");

				if (newfile.createNewFile()) {
					BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
					bw.write("{");
					bw.newLine();
					for (int i = 0; i < filelist1.length; i++) {
						String s = "\"handmadeguns." + soundsfile[i] + "\"";
						String c = "\"category\"";
						String n = "\"hostile\"";
						String ss = "\"sounds\"";
						bw.write(s + ": {" + c + ": " + n + "," + ss + ": [\"" + soundsfile[i] + "\"]},");
						bw.newLine();
					}
					{
						String s = "\"handmadeguns." + "nulls" + "\"";
						String c = "\"category\"";
						String n = "\"hostile\"";
						String ss = "\"sounds\"";
						bw.write(s + ": {" + c + ": " + n + "," + ss + ": [\"" + "nulls" + "\"]}");
						bw.newLine();
					}
					bw.write("}");
					bw.close();
				}
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getNameWithoutExtension(File[] filelist1, int i)
	{
		String fileName = filelist1[i].getName();
		int index = fileName.lastIndexOf('.');
		if (index!=-1)
		{
			return fileName.substring(0, index);
		}
		return "";
	}

	private static boolean checkBeforeReadfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
				return true;
			}
		}

		return false;
	}
}
