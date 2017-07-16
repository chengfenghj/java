package mainpackge;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ShowPanel extends JPanel {

	private Image showImage =null;
	private int HEIGHT =400;                  //定义底座的高度
	private int trackAmount =7;     //音轨数 ，默认有7个音轨
	private int trackWidth =60;      //音轨显示宽度 ，默认为60 。
	List<Simulation> trackList =new ArrayList<Simulation>();              //音轨模拟的集合
 	
	public ShowPanel(){
		
	}
	//用于画图的方法
	public void paint(Graphics g){
		
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, 420, 560);
		if(showImage!=null)
			g.drawImage(showImage,0,0,420,400,null);
		g.setColor(Color.RED);
		g.fillRect(0,400,420,50);
		drawTrack(g);
	}
	public void setShowImage(Image showImage){
		this.showImage =showImage;
	}
	//设置ShowPanel中要显示的音轨数
	public void setTrackAmount(int amount){
		if(amount>7)
			trackAmount =amount;
		else
			trackAmount =7;
		trackWidth =410/trackAmount;
		//定义指定数量的音轨对象
		for(int i=0;i<amount;i++){
			Simulation track =new Simulation();
			trackList.add(track);
		}
	}
	//指定音轨值改变时调用
	public void changeHeight(int index,int height){
//		System.out.println("已改变");
		trackList.get(index).setHeight(height*40);
	}
	//用来将所有音轨画出来
	private void drawTrack(Graphics g){
		Graphics2D g2 =(Graphics2D)g;
		Composite composite =g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
		for(int i=0;i<trackList.size();i++){
			g2.setColor(Color.MAGENTA);
			g2.fillRect(i*trackWidth,HEIGHT-trackList.get(i).getHeight(),trackWidth, trackList.get(i).getHeight());        //画柱体
			g2.setColor(Color.BLACK);
			g2.drawRect(i*trackWidth,HEIGHT-trackList.get(i).getHeight(),trackWidth, trackList.get(i).getHeight());        //画边框
			trackList.get(i).update();         
		}
		g2.setComposite(composite);
	}
}
