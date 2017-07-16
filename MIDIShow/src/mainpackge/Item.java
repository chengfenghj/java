package mainpackge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Item {

	public static final int NON_PITCH_ON =0;           //未选中状态
	public static final int PITCH_ON =1;               //选中状态
	public static final int PLAY =2;                  //播放状态
	
	private int textY;                 //文本的Y值
	private int backgroundY;            //背景的Y值
	private int state;                 //状态
	private String str;                 //item名字
	
	public Item(int textY,int backgroundY){
		this.textY =textY;
		this.backgroundY =backgroundY;
		state =NON_PITCH_ON;
	}
	public void setName(String str){
		this.str =str;
	}
	public int getState(){
		return state;
	}
	public void setState(int state){
		this.state =state;
	}
	public void changeState(){
		if(state<2)
			state++;
		else 
			return;
	}
	private Color getColor(){
		if(state==NON_PITCH_ON)
			return Color.WHITE;
		if(state==PITCH_ON)
			return Color.BLUE;
		else 
			return Color.GREEN;
	}
	public void draw(Graphics g,int width,int height){

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, backgroundY, width, height);                    
		g.setColor(getColor());
		g.setFont(new Font("宋体",Font.BOLD,20));
		g.drawString(str,10,textY);     

	}
}
