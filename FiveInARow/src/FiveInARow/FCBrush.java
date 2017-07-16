package FiveInARow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class FCBrush {

	private int lastx,lasty;        //用于记录玩家上一步坐标
	private int rx,ry;             //用于记录电脑上一步坐标
	private int chesscolor=0;    //用于记录棋子的颜色，0 黑棋 ;1白棋
	private int lastcolor;       //用于上一步记录棋子的颜色，以便悔棋
	private boolean stop=false;         //是否结束
	private boolean robot=false;       //是否人机
	private boolean start=false;
	
	FCToolkit fct =new FCToolkit();
	FCRobot fcr;
	
	public FCBrush(){
		
		start();
	}
	/**
	 *将棋盘画出来 
	 */
	public void draw(Graphics g){
		
		g.drawImage(fct.board,0,0,640,640,null);
		for(int i=0;i<fct.getLenght();i++){
			for(int j=0;j<fct.getLenght();j++){
				if(fct.getValue(i,j)==1)
					g.drawImage(fct.white,i*40+20,j*40+20,40,40,null);
				if(fct.getValue(i,j)==0)
					g.drawImage(fct.black,i*40+20,j*40+20,40,40,null);
			}
		}
		
		if(start==true){
			g.setColor(Color.RED);
			if(robot==true)
				g.drawRect(rx*40+20,ry*40+20,40,40);
			else
				g.drawRect(lastx*40+20,lasty*40+20,40,40);
		}
		
		g.setColor(Color.GREEN);
		g.fillRect(640,0,160,120);
		
		String str ="玩家";
		g.setColor(Color.BLUE);
		g.setFont(new Font("隶书",Font.BOLD,50));
		g.drawString(str,660,80);
		
		g.setColor(Color.BLACK);
		g.fillRect(640,120,160,5);
		
		g.setColor(Color.green);
		g.fillRect(640,125,160,100);
		
		if(chesscolor==0)
			g.drawImage(fct.black,690,150,50,50,null);
		if(chesscolor==1)
			g.drawImage(fct.white,690,150,50,50,null);
		
		g.setColor(Color.BLACK);
		g.fillRect(640,225,160,5);
	}
	/**
	 * 对鼠标事件的响应
	 * */
	void clickResponse(int x,int y){
		if(stop==true)
			return;
		if(fct.getValue(x, y)!=9)
			return;
		lastx=x;
		lasty=y;
		if(robot==false){
			lastcolor=chesscolor;
			play(x,y);
		}
		else{
			if(chesscolor==fcr.getColor())
				return;
			play(x,y);
			rx=fcr.getX();
			ry=fcr.getY();
			play(rx,ry);
		}
	}
	/**
	 * 对菜单事件的响应
	 * */
	void menuResponse(String order){
		
		if(order=="restart"){
			start();
		}
		if(order=="robotb"){
			robot=true;
			fcr=new FCRobot();
			fcr.setChessColor(0);
			start();
		}
		if(order=="robotw"){
			robot=true;
			fcr=new FCRobot();
			fcr.setChessColor(1);
			start();
		}
		if(order=="mans"){
			robot=false;
			start();
		}
		if(order=="regret"){
			stop=false;
			fct.setIsWin(false);
			if(robot==true){
				fct.setValue(rx,ry,9);
				fcr.readBoard(rx,ry,9);
				fct.setValue(lastx,lasty,9);
				fcr.readBoard(lastx,lasty,9);
			}
			else{
				fct.setValue(lastx,lasty,9);
				chesscolor=lastcolor;
			}
		}
		if(order=="giveup"){
			over();
		}
	}
	/**
	 * 下棋事件
	 * */
	private void play(int x,int y){
		
		if(stop==true)
			return;
		start=true;
		if(robot==true)
			fcr.readBoard(x,y,chesscolor);
		fct.setValue(x,y,chesscolor);
		fct.judge(x, y);
		if(chesscolor==0)
			chesscolor=1;
		else
			chesscolor=0;
		if(fct.getIsWin()==true)
			over();
	}
	/**
	 * 对棋盘初始化
	 * */
	void start(){
		
		chesscolor=0;
		fct.initBoard();
		fct.setIsWin(false);
		if(robot==true){
			fcr.init();
			if(fcr.getColor()==0){
				fct.setValue(7,7,0);
				fcr.readBoard(7,7,0);
				chesscolor=1;
			}
		}
		stop=false;
		start=false;
	}
	/**
	 * 游戏结束，弹出一个窗口
	 * */
	void over(){
		fct.setIsWin(false);
		stop=true;
		if(robot==true){
			if(chesscolor==fcr.getColor())
				JOptionPane.showMessageDialog(null,"你赢了！");
			else
				JOptionPane.showMessageDialog(null,"你输了！");
		}
		else{
			if(chesscolor==0)
				JOptionPane.showMessageDialog(null,"白棋赢了！");
			if(chesscolor==1)
				JOptionPane.showMessageDialog(null,"黑棋赢了！");
		}
	}
}
