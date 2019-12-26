package handmadevehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HMVAddSounds
{
	static List<ArrayList<String>> mainList = new ArrayList();
	public static List Guns = new ArrayList();
	
	public static String[] soundsfile = new String[255];
	
	public static void load(File soundsDir, File jsonDir)
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
						String s = "\"handmadevehicle." + soundsfile[i] + "\"";
						String c = "\"category\"";
						String n = "\"ambient\"";
						String ss = "\"sounds\"";
						bw.write(s + ": {" + c + ": " + n + "," + ss + ": [\"" + soundsfile[i] + "\"]},");
						bw.newLine();
					}
					{
						String s = "\"handmadevehicle." + "nulls" + "\"";
						String c = "\"category\"";
						String n = "\"ambient\"";
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
