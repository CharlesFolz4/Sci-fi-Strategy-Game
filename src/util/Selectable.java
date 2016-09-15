package util;

public abstract class Selectable{
	//pure utility, only exists to make selection on the map easier
	private int x;
	private int y;
	
	public Selectable(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setCoordinates(int[] coordinates){
		this.x = coordinates[0];
		this.y = coordinates[1];
	}
	
	public int[] getCoordinates(){
		int[] temp = {x,y};
		return temp;
	}
}
