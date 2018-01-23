package MineSweeping;

public class Point {
	
	public int x;
	public int y;
	public int type;
	
	public Point(int x, int y, int type){
		this.x =x;
		this.y =y;
		this.type =type;
	}
	public Point(int x, int y){
		this.x =x;
		this.y =y;
		this.type =-1;
	}

}
