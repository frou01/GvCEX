package handmadeguns;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static handmadeguns.HandmadeGunsCore.tabshmg;

public class HMGAddTabs
{
	public static List Attach = new ArrayList();
	public static List Magazines = new ArrayList();

	public static void load(File configfile, boolean isClient, File file)
	{
		String tabname = null;
		String iconname = null;
		try {
			//File file = new File(configfile,"hmg_handmadeguns.txt");
			if (checkBeforeReadfile(file))
			{

				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				String str;
				while((str = br.readLine()) != null) {  // 1行ずつ読み込む
					//System.out.println(str);
					String[] type = str.split(",");
					if(type[0].equals("name")){
						tabname = type[1];
					}
					else if(type[0].equals("Icon")){
						iconname = type[1];
					}
					else if(type[0].equals("End")){
						tabshmg.put(tabname,new HMGCreativeTab_ForCustom(tabname,iconname));
					}

				}
				br.close();  // ファイルを閉じる
			}
			else
			{

			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
