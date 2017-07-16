package mainpackge;

//用于定义模拟音轨柱体的类
public class Simulation {

	private int speed =5;       //下落的速度
	private int height;          //高度
	
	public Simulation(){
		height =0;
	}
	public void setHeight(int height){
		this.height =height;
	}
	public int getHeight(){
		return height;
	}
	//当音轨的值没有发生变化时就会下落
	public void update(){
		if(height>=speed)
			height -=speed;
		else
			height =0;
	}
}
