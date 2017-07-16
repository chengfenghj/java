package FiveInARow;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener{

	FCBrush fcb =new FCBrush();
	public GamePanel(){
		
		addMouseListener(this);           //��������¼�����
	}
	public void paint(Graphics g){
		
		fcb.draw(g);             //������
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x =(e.getX()-20)/40;             //�õ����̵�����
		int y =(e.getY()-20)/40;              
		repaint();                           //�ػ�
		if(x>=0&&x<15&&y>=0&&y<15)             //�������¼��������ڣ����ú���
			fcb.clickResponse(x, y);
		
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
	/**
	 * �Բ˵��¼�����Ӧ 
	 * */
	void menuResponse(String order){
		
		fcb.menuResponse(order);
		repaint();
	}
}
