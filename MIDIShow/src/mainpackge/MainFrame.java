package mainpackge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame{

public MainFrame(){
		
		setTitle("MIDI���ֿ��ӻ�չʾ");         
		setLocation(400,200);      
		setSize(400,300);           
		setResizable(false);        //���ô��ڲ��ɵ��ڴ�С
		setVisible(true);           //���ô��ڿɼ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentpanel =new JPanel(new GridLayout(2,2));
		//�������ڴ�ӡ���ֵ�panel
		JPanel paintpanel =new JPanel(){
			
			public void paint(Graphics g){
				String str1 ="���ֿ��ӻ�չʾ";                   //����1
				g.setFont(new Font("����",Font.BOLD,40));             //��������
				g.setColor(Color.GREEN);                   //������ɫ
				g.drawString(str1,50,60);                 //�����ֻ���
				
				String str2 ="�ƺ�����";                          //����2
				g.setFont(new Font("����",Font.BOLD,30));
				g.setColor(Color.BLUE);
				g.drawString(str2,120,120);
			}
		};
		JPanel buttonpanel =new JPanel();            //����һ��panel��Ű�ť
		JButton start =new JButton("�����ʼ");            //��ť
		start.addActionListener(new ActionListener(){        //�԰�ť����ʵ������
			
			public void actionPerformed(ActionEvent e){
				new ShowFrame();                      //������Ϸ
				dispose();                    //�ͷ�frame���������
			}
		});
		
		buttonpanel.add(start);
		contentpanel.add(paintpanel);
		contentpanel.add(buttonpanel);
		setContentPane(contentpanel);
	}
	/**
	 * ������
	 * */
	public static void main(String[] str){
		
		new MainFrame();
	}
}
