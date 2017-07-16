package FiveInARow;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener{

	FCBrush fcb =new FCBrush();
	public GamePanel(){
		
		addMouseListener(this);           //设置鼠标事件监听
	}
	public void paint(Graphics g){
		
		fcb.draw(g);             //画出来
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x =(e.getX()-20)/40;             //得到棋盘的坐标
		int y =(e.getY()-20)/40;              
		repaint();                           //重画
		if(x>=0&&x<15&&y>=0&&y<15)             //如果鼠标事件在棋盘内，调用函数
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
	 * 对菜单事件的响应 
	 * */
	void menuResponse(String order){
		
		fcb.menuResponse(order);
		repaint();
	}
}
