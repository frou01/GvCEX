package hmgww2.blocks.tile;

public class NationEntityList {
	public Class[] Barrack;
	public Class[] Factory;
	public Class[] AirBase;
	public Class[] Port;
	public Class[] Fort;

	public NationEntityList(Class[] Barrack,
	                        Class[] Factory,
	                        Class[] AirBase,
	                        Class[] Port,
	                        Class[] Fort) {
		this.Barrack = Barrack;
		this.Factory = Factory;
		this.AirBase = AirBase;
		this.Port = Port;
		this.Fort = Fort;
	}
}
