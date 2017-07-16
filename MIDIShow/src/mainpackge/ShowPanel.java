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
	private int HEIGHT =400;                  //��������ĸ߶�
	private int trackAmount =7;     //������ ��Ĭ����7������
	private int trackWidth =60;      //������ʾ��� ��Ĭ��Ϊ60 ��
	List<Simulation> trackList =new ArrayList<Simulation>();              //����ģ��ļ���
 	
	public ShowPanel(){
		
	}
	//���ڻ�ͼ�ķ���
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
	//����ShowPanel��Ҫ��ʾ��������
	public void setTrackAmount(int amount){
		if(amount>7)
			trackAmount =amount;
		else
			trackAmount =7;
		trackWidth =410/trackAmount;
		//����ָ���������������
		for(int i=0;i<amount;i++){
			Simulation track =new Simulation();
			trackList.add(track);
		}
	}
	//ָ������ֵ�ı�ʱ����
	public void changeHeight(int index,int height){
//		System.out.println("�Ѹı�");
		trackList.get(index).setHeight(height*40);
	}
	//�������������컭����
	private void drawTrack(Graphics g){
		Graphics2D g2 =(Graphics2D)g;
		Composite composite =g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
		for(int i=0;i<trackList.size();i++){
			g2.setColor(Color.MAGENTA);
			g2.fillRect(i*trackWidth,HEIGHT-trackList.get(i).getHeight(),trackWidth, trackList.get(i).getHeight());        //������
			g2.setColor(Color.BLACK);
			g2.drawRect(i*trackWidth,HEIGHT-trackList.get(i).getHeight(),trackWidth, trackList.get(i).getHeight());        //���߿�
			trackList.get(i).update();         
		}
		g2.setComposite(composite);
	}
}
