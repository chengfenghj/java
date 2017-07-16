package FiveInARow;

import java.awt.Image;
import javax.swing.ImageIcon;

public class FCToolkit {

	private final int lenght =15;        //���̿��
	private boolean iswin=false;
	
	//��ȡͼƬ
	Image white =new ImageIcon(getClass().getResource("/res/white.gif")).getImage();
	Image black =new ImageIcon(getClass().getResource("/res/black.gif")).getImage();
	Image board =new ImageIcon(getClass().getResource("/res/board.gif")).getImage();
	
	private int fcboard[][] =new int[lenght][lenght];            //����15x15�����̣�
	
	/**
	 * ��ʼ������
	 * */
	void initBoard(){
		
		for(int i=0;i<lenght;i++){
			for(int j=0;j<lenght;j++){
				fcboard[i][j]=9;
			}
		}
	} 
	/**
	 * �����ж��Ƿ�����������
	 * */
	public void judge(int x,int y){
		
		int amount=0;
		int chesscolor=fcboard[x][y];
		for(int jx=x+1,jy=y;;jx++){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		for(int jx=x-1,jy=y;;jx--){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		if(amount>=4){
			iswin=true;
			return;
		}
		amount=0;
		for(int jx=x,jy=y+1;;jy++){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		for(int jx=x,jy=y-1;;jy--){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		if(amount>=4){
			iswin=true;
			return;
		}
		amount=0;
		for(int jx=x+1,jy=y+1;;jx++,jy++){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		for(int jx=x-1,jy=y-1;;jx--,jy--){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		if(amount>=4){
			iswin=true;
			return;
		}
		amount=0;
		for(int jx=x+1,jy=y-1;;jx++,jy--){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		for(int jx=x-1,jy=y+1;;jx--,jy++){
			if(getValue(jx,jy)!=chesscolor)
				break;
			amount++;
		}
		if(amount>=4){
			iswin=true;
			return;
		}
	}
	/**
	 * ����ָ�������������ɫ
	 * */
	int getValue(int x,int y){
		
		if(x>=0&&x<lenght&&y>=0&&y<lenght)
			return fcboard[x][y];
		else
			return 9;
	}
	/**
	 * ����ָ�������������ɫ
	 * */
	void setValue(int x,int y,int value){
		
		if(fcboard[x][y]==9||value==9)
			fcboard[x][y]=value;
	}
	/**
	 * ����iswin��ֵ
	 * */
	boolean getIsWin(){
		return iswin;
	}
	/**
	 * ����iswin��ֵ
	 * */
	void setIsWin(boolean b){
		iswin=b;
	}
	/**
	 * �������̸߶�
	 * */
	int getLenght(){
		return lenght;
	}
}
