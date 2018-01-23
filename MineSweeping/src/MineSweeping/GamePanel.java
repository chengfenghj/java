package MineSweeping;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

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
	private boolean start =false;
	private boolean isBetaCome =false;
	private boolean betaComeRemind =false;
	
	private MSToolkit mst;
	
	private Algorithm betaCome;
	private Point point;
	
	TimerThread timethread =new TimerThread();
	private Thread betaComeThread;
	
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
					if(betaComeRemind){
						DecimalFormat format=new DecimalFormat("0.000");                //精确到小数点后3位
						g.setColor(Color.red);
						g.drawString(format.format(betaCome.getMineProb(i, j)), i*minesize +5, j*minesize +16);
						g.setColor(Color.black);
						g.drawString(format.format(betaCome.getNoneProb(i, j)), i*minesize +5, j*minesize +30);
					}
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
		
		if(betaComeRemind){
			g.setColor(Color.black);
			g.setFont(new Font("黑体",Font.BOLD,40));
			g.drawString("提示：", mst.getXLength()*minesize +20, (height-50)/2 -minesize);
	
			g.setColor(Color.red);
			g.setFont(new Font("宋体",Font.BOLD,20));
			g.drawString("红色字体为", mst.getXLength()*minesize +30, (height-50)/2 -minesize +40);
			g.drawString("最大是雷概率！", mst.getXLength()*minesize +30, (height-50)/2 -minesize +60);
	
			g.setColor(Color.black);
			g.setFont(new Font("宋体",Font.BOLD,20));
			g.drawString("黑色字体为", mst.getXLength()*minesize +30, (height-50)/2 -minesize +90);
			g.drawString("最小是雷概率！", mst.getXLength()*minesize +30, (height-50)/2 -minesize +110);
		}
//		g.setColor(Color.ORANGE);
//		g.fillOval(mst.getXLength()*minesize+10,(height-50)/2-90,180,180);
//		g.setColor(Color.WHITE);
//		g.fillOval(mst.getXLength()*minesize+20,(height-50)/2-80,160,160);
//		
//		g.setColor(Color.BLACK);
//		for(int i=-1;i<=1;i++){
//			for(int j=-1;j<=1;j++){
//				if(i==0||j==0){
//					g.fillOval(mst.getXLength()*minesize+90+i*70,(height-50)/2-10+j*70,20,20);
//				}
//				if(i!=0&&j!=0){
//					g.fillOval(mst.getXLength()*minesize+95+i*(80-20),(height-50)/2-5+j*(80-50),10,10);
//					g.fillOval(mst.getXLength()*minesize+95+i*(80-50),(height-50)/2-5+j*(80-20),10,10);
//				}
//			}
//		}
		
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
		if(isBetaCome){
			isBetaCome =false;
			betaCome.start(mineamount);
			betaComeThread.stop();
			betaComeThread =null;
		}
		if(betaComeRemind){
			betaComeRemind =false;
		}
		repaint();
		iswin=false;
		gameover=false;
		time=0;
		b =false;
		start =false;
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
		
		if(isBetaCome)
			return;
		if(betaComeRemind){
			betaComeRemind =false;
		}
		
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
		start =true;
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
	
	public boolean isBetaCome() {
		return isBetaCome;
	}
	public void setBetaCome(boolean betaCome) {
		this.isBetaCome = betaCome;
	}
	
	/**
	 * 开启自动扫雷
	 */
	public void betaComeDo(){
		if(!isBetaCome)
			return;
		if(betaCome == null){
			betaCome =new Algorithm(mineamount);
			betaCome.start(mineamount);
		}
		else{
			betaCome.start(mineamount);
		}
		betaComeThread =new Thread(new BetaComeThread());
		betaComeThread.start();
	}
	
	/**
	 * 向betaCome更新地图
	 */
	private void updateBoard(){
		for(int i =0; i < mst.getXLength(); i++){
			for(int j =0; j < mst.getYLength(); j++){
				if(mst.getRearValue(i, j) == 0)
					betaCome.setBoard(i, j, mst.getFrontValue(i, j));
				else if(mst.getRearValue(i, j) == 1)
					betaCome.setBoard(i, j, -1);
				else if(mst.getRearValue(i, j) == 2)
					betaCome.setBoard(i, j, 9);
			}
		}
	}
	
	/**
	 * betaCome的计算结果
	 */
	private void betaComeGo(){
		if(point == null)
			return;
		int x =point.x;
		int y =point.y;
		int type =point.type;
		if(type == 1 && mst.getRearValue(x, y) != 2){
			mst.rightClick(x, y);
		}
		if(type == 2){
			mst.leftClick(x, y);
			if(mst.getFrontValue(x, y) == 9){
				gameover =true;
			}
		}
	}
	
	public void betaComeRemind(){
		if(isBetaCome)
			return;
		betaComeRemind =true;
		if(betaCome == null){
			betaCome =new Algorithm(mineamount);
		}
		else{
			betaCome.start(mineamount);
		}
		updateBoard();
		betaCome.valuation();
		repaint();
	}

	private class TimerThread implements Runnable{
		
		public void run(){
			int a =0;
			while(true){
				a++;
				if(!gameover && !iswin && start){
					time++;
					repaint();
					try{
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				else 
					time = 0;
				a=0;
			}
		}
	}
	
	private class BetaComeThread implements Runnable{

		@Override
		public void run() {
			int a =0;
			while(true){
				a++;
				if(!gameover && isBetaCome){
					try{
						Thread.sleep(200);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					repaint();
					updateBoard();
					betaCome.clearList();
					betaCome.valuation();
					point =betaCome.getResult();
					betaComeGo();
				}
				a =0;
			}
			
		}
	}
}
