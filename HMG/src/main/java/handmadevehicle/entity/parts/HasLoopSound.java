package handmadevehicle.entity.parts;

public interface HasLoopSound {
	default void yourSoundIsremain(){
		((HasBaseLogic)this).getBaseLogic().yourSoundIsremain();
	}
	default float getsoundPitch(){
		return ((HasBaseLogic)this).getBaseLogic().getsoundPitch();
	}
	default String getsound(){
		return ((HasBaseLogic)this).getBaseLogic().getsound();
	}
}
