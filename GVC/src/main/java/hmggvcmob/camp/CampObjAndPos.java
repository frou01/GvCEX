package hmggvcmob.camp;

public class CampObjAndPos {
	public int[] flagPos;
	public CampObj campObj;
	public boolean attacked;
	public CampObjAndPos(int[] flagPos , CampObj value){
		this.flagPos = flagPos;
		this.campObj = value;
	}
}
