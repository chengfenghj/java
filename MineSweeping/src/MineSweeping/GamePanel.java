package MineSweeping;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements MouseListener{

	private int width,height;
	private int minesize;
	private boolean gameover=false;
	private boolean iswin=false;
	private boolean b =false;
	private int mineamount;
	private int flagamount=0;
	private int isright=0;
	private int time=0;
	
	MSToolkit mst;
	TimerThread timethread =new TimerThread();
	
	public GamePanel(int mineamount){
		new Thread(timethread).start();
		this.mineamount=mineamount;
		start(mineamount);
		addMouseListener(this);
	}
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		for(int i=0;i<mst.getXLength();i++){
			for(int j=0;j<mst.getYLength();j++){
				if(mst.getRearValue(i,j)==1){
					g.drawImage(mst.snow,i*minesize,j*minesize,minesize,minesize,null);
					if(gameover==true&&(mst.getFrontValue(i,j)==9))
						g.drawImage(mst.mine1,i*minesize,j*minesize,minesize,minesize,null);
				}
				else if(mst.getRearValue(i,j)==2){
					flagamount++;
					g.drawImage(mst.flag,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==9){
						isright++;
						if(gameover==true)
						g.drawImage(mst.mine3,i*minesize,j*minesize,minesize,minesize,null);
					}
				}
				else if(mst.getRearValue(i, j)==3){
					g.drawImage(mst.query,i*minesize,j*minesize,minesize,minesize,null);
					if(gameover==true&&(mst.getFrontValue(i,j)==9))
						g.drawImage(mst.mine1,i*minesize,j*minesize,minesize,minesize,null);
				}
				else{
					if(mst.getFrontValue(i, j)==0)
						g.drawImage(mst.space,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==1)
						g.drawImage(mst.one,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==2)
						g.drawImage(mst.two,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==3)
						g.drawImage(mst.three,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==4)
						g.drawImage(mst.four,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==5)
						g.drawImage(mst.five,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==6)
						g.drawImage(mst.six,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==7)
						g.drawImage(mst.seven,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==8)
						g.drawImage(mst.eight,i*minesize,j*minesize,minesize,minesize,null);
					if(mst.getFrontValue(i,j)==9){
						g.drawImage(mst.mine2,i*minesize,j*minesize,minesize,minesize,null);
					}
					
				}
			}
		}
		if(isright==mineamount&&isright==flagamount)
			iswin=true;

		g.setColor(Color.DARK_GRAY);
		g.fillRect(mst.getXLength()*minesize,0,5,height);
		
		String str1 ="剩余地雷数:";
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("黑体",Font.BOLD,25));
		g.drawString(str1,mst.getXLength()*minesize+20,50);
		
		g.setColor(Color.BLACK);
		g.drawLine(mst.getXLength()*minesize,70,width,70);
		
		String amount =String.valueOf(mineamount-flagamount);
		g.setColor(Color.BLUE);
		g.setFont(new Font("黑体",Font.BOLD,50));
		g.drawString(amount,mst.getXLength()*minesize+60,130);
		flagamount=0;
		isright=0;
		
		g.setColor(Color.black);
		g.drawLine(mst.getXLength()*minesize,(height-50)/2-100,width,(height-50)/2-100);
		g.drawLine(mst.getXLength()*minesize,(height-50)/2+100,width,(height-50)/2+100);
		
		g.setColor(Color.ORANGE);
		g.fillOval(mst.getXLength()*minesize+10,(height-50)/2-90,180,180);
		g.setColor(Color.WHITE);
		g.fillOval(mst.getXLength()*minesize+20,(height-50)/2-80,160,160);
		
		g.setColor(Color.BLACK);
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if(i==0||j==0){
					g.fillOval(mst.getXLength()*minesize+90+i*70,(height-50)/2-10+j*70,20,20);
				}
				if(i!=0&&j!=0){
					g.fillOval(mst.getXLength()*minesize+95+i*(80-20),(height-50)/2-5+j*(80-50),10,10);
					g.fillOval(mst.getXLength()*minesize+95+i*(80-50),(height-50)/2-5+j*(80-20),10,10);
				}
			}
		}
		
		String str2 ="　时　间:";
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("黑体",Font.BOLD,25));
		g.drawString(str2,mst.getXLength()*minesize+20,height-155);
		
		g.setColor(Color.BLACK);
		g.drawLine(mst.getXLength()*minesize,height-130,width,height-130);
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("黑体",Font.BOLD,50));
		g.drawString(getString(time),mst.getXLength()*minesize+50,height-80);
		
		if(iswin==true)
			isWin();
	}
	public void start(int mineamount){
		this.mineamount=mineamount;
		if(mineamount==10){
			width=700;
			height=550;
			minesize=50;
		}
		if(mineamount==30){
			width=840;
			height=650;
			minesize=40;
		}
		if(mineamount==60){
			width=900;
			height=680;
			minesize=35;
		}
		setSize(width,height);
		mst=new MSToolkit();
		mst.start(mineamount);
		repaint();
		iswin=false;
		gameover=false;
		time=0;
		b =false;
	}
	public String getString(int integer){
		String str=String.valueOf(integer);
		if(integer<10)
			str="00"+str;
		if(integer>=10&&integer<100)
			str="0"+str;
		return str;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x =e.getX()/minesize;
		int y =e.getY()/minesize;
		repaint();
		if(gameover==true || iswin==true)
			return;
		if(x>=0&&x<mst.getXLength()&&y>=0&&y<mst.getYLength()){
			if(e.getButton()==MouseEvent.BUTTON3){
				mst.rightClick(x,y);
			}
			if(e.getButton()==MouseEvent.BUTTON1){
				mst.leftClick(x, y);
				if(mst.getFrontValue(x,y)==9&&mst.getRearValue(x,y)!=2){
					gameover=true;
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void isWin(){
		if(b)
			return;
		b =true;
		JOptionPane.showMessageDialog(this,"你胜利了！","",JOptionPane.WARNING_MESSAGE);
	}
	
	private class TimerThread implements Runnable{
		
		public void run(){
			while(true){
				time++;
				if(gameover==false&&iswin==false){
					repaint();
					try{
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				else 
					time = 0;
			}
		}
	}
}
