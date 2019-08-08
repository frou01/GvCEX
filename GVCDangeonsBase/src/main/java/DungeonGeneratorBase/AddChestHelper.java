package DungeonGeneratorBase;

import cpw.mods.fml.common.registry.GameRegistry;

import static java.lang.Integer.parseInt;

public class AddChestHelper {
	String[] items;
	ItemAndNum[] itemAndNums = new ItemAndNum[27];
	public AddChestHelper(String[] items){
		this.items = items;
		for(int id = 0; id < itemAndNums.length; id++){
			if(items[id] != null) {
				String[] temp = items[id].split(";");
				if(temp.length==3) {
					itemAndNums[id] = new ItemAndNum();
					itemAndNums[id].item = GameRegistry.findItem(temp[0], temp[1]);
					if(itemAndNums[id].item == null){
						itemAndNums[id].block = GameRegistry.findBlock(temp[0], temp[1]);
						itemAndNums[id].num = parseInt(temp[2]);
					}else {
						itemAndNums[id] = null;
					}
				}
			}
		}
	}
	public ItemAndNum[] getItemObj(){
		return itemAndNums;
	}
}
