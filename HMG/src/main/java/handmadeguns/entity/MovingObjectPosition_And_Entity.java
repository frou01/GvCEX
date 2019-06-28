package handmadeguns.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;

public class MovingObjectPosition_And_Entity {
	public MovingObjectPosition movingObjectPosition;
	public Entity entity;
	public MovingObjectPosition_And_Entity(Entity entity){
		this.entity = entity;
	}
	public MovingObjectPosition_And_Entity(Entity entity,MovingObjectPosition movingObjectPosition){
		this.entity = entity;
		this.movingObjectPosition = movingObjectPosition;
	}
}
