package handmadevehicle.entity.parts;

import java.util.Random;

public interface IVehicle extends HasBaseLogic{
	Random getRand();
	boolean getinWater();
	void setinWater(boolean value);
}
