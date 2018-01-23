package MineSweeping;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

	public MainFrame(){
		
		setTitle("����ɨ��");
		setLocation(400,200);
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		JPanel contentpane =new JPanel();
		contentpane.setLayout(new GridLayout(2,2));
		JPanel wordpanel =new JPanel(){
			public void paint(Graphics g){
				Graphics2D g2 =(Graphics2D) g;
				g2.setFont(new Font("����",Font.BOLD|Font.ITALIC,40));
				g2.setColor(Color.BLUE);
				String str1 ="��ӭ����";
				for(int i=0;i<str1.length();i++){
					g2.drawString(str1.charAt(i)+"",90+i*50,50);
				}
				g2.setFont(new Font("����",Font.BOLD|Font.ITALIC,60));
				g2.setColor(Color.MAGENTA);
				String str2 ="����ɨ��";
				for(int i=0;i<str2.length();i++){
					g2.drawString(str2.charAt(i)+"",40+i*80,120);
				}
			}
		};
		JPanel buttonpanel =new JPanel();
		JButton startbutton =new JButton("�� Ϸ �� ʼ");
		startbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GameFrame gameframe =new GameFrame();
				dispose();
			}
		});
		
		buttonpanel.add(startbutton);
		contentpane.add(wordpanel);
		contentpane.add(buttonpanel);
		setContentPane(contentpane);
		
		
	}
//	public static void main(String[] str){
//		
//		new MainFrame();
//	}
}
