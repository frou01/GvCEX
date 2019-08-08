package DungeonGeneratorBase;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


public class DungeonData_withSettings {
	boolean biomeRestrictionType = false;//true:black false:white
	int[] biomeSetting = null;
	float frequency = 0.5f;
	boolean hasFixedPosition;
	boolean onStrongHold;
	int x;
	int z;
	String name;
	
	int interval = 1;
	
	ArrayList<DungeonData> dungeonData;
	public DungeonData_withSettings(File file){
		try {
			if (checkBeforeReadfile(file))
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				String str;
				while((str = br.readLine()) != null) {  // 1行ずつ読み込む
					//System.out.println(str);
					String[] type = str.split(",");
					switch (type[0]) {
						case "NAME":{
							name = type[1];
							break;
						}
						case "BiomeRestriction":{
							biomeRestrictionType = parseBoolean(type[1]);
							biomeSetting = new int[type.length-2];
							for(int id = 0;id < type.length-2;id++){
								biomeSetting[id] = parseInt(type[id+2]);
							}
							break;
						}
						case "Frequency":{
							frequency = parseFloat(type[1]);
							break;
						}
						case "FixedPosition":{
							hasFixedPosition = true;
							x = parseInt(type[1]);
							z = parseInt(type[2]);
							break;
						}
						case "ONStrongHold":{
							onStrongHold = true;
							break;
						}
						case "Interval":{
							interval = parseInt(type[1]);
							break;
						}
					}
					
				}
				br.close();  // ファイルを閉じる
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean checkBeforeReadfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
				return true;
			}
		}
		
		return false;
	}
}
