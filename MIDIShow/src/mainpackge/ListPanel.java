package mainpackge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

//������ʾ�����б�����
public class ListPanel extends JPanel implements MouseListener{

	private final int itemHeight =40;       //�б�߶�
	private final int WIDTH =190;            //�����
	private final int HEIGHT =640;           //�����
	private final int MAGIN =27;            //���붥���ľ���
	
	private List<Item> itemList =new ArrayList<Item>();
	private int pitchOn =0;
	private int play =0;
	private String[] list ={"����ѭ��","Butterfly","�ú���","ǧ��ǧѰ","Сƻ��","�²�����","����A��","��������"};
	int x=0,y=0;
	
	public ListPanel(){
		addMouseListener(this);            //������
		int y1 =MAGIN;
		int y2 =0;
		for(int i=0;i<list.length;i++){
			Item item =new Item(y1,y2);
			item.setName(list[i]);
			itemList.add(item);
			y1 +=itemHeight+5;
			y2 +=itemHeight+5;
		}
		itemList.get(0).setState(Item.PLAY);
	}
	public void next(){
		itemList.get(pitchOn).setState(Item.NON_PITCH_ON);
		itemList.get(play).setState(Item.NON_PITCH_ON);
		if(play<itemList.size()-1)
			play++;
		else
			play =0;
		itemList.get(play).setState(Item.PLAY);
		pitchOn =play;
		repaint();
	}
	public void last(){
		itemList.get(pitchOn).setState(Item.NON_PITCH_ON);
		itemList.get(play).setState(Item.NON_PITCH_ON);
		if(play>0)
			play--;
		else
			play =itemList.size()-1;
		itemList.get(play).setState(Item.PLAY);
		pitchOn =play;
		repaint();
	}
	public int getPlay(){
		return play;
	}
	public void setString(String str){
		list[0]=str;
	}
	public String getString(int i){
			return list[i];
	}
	public void paint(Graphics g){
		
		g.setColor(Color.GRAY);
		g.fillRect(0,0,WIDTH,HEIGHT);                           //������
		
		for(int i=0;i<itemList.size();i++){
			itemList.get(i).draw(g, WIDTH, itemHeight);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.y =e.getY();
		int index =y/(itemHeight+5);
		if(itemList.get(index).getState()==Item.NON_PITCH_ON){
			if(itemList.get(pitchOn).getState()!=Item.PLAY)
				itemList.get(pitchOn).setState(Item.NON_PITCH_ON);
			itemList.get(index).changeState();
			pitchOn =index;
		}
		else if(itemList.get(index).getState()==Item.PITCH_ON){
			itemList.get(play).setState(Item.NON_PITCH_ON);
			itemList.get(index).changeState();
			pitchOn =index;
			play =index;
		}
		else{
			if(pitchOn!=play)
				itemList.get(pitchOn).setState(Item.NON_PITCH_ON);
			pitchOn =index;
		}
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
//		this.x =e.getX();
//		this.y =e.getY();
//		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
