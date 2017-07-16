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
		
		setTitle("MIDI音乐可视化展示");         
		setLocation(400,200);      
		setSize(400,300);           
		setResizable(false);        //设置窗口不可调节大小
		setVisible(true);           //设置窗口可见
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentpanel =new JPanel(new GridLayout(2,2));
		//定义用于打印文字的panel
		JPanel paintpanel =new JPanel(){
			
			public void paint(Graphics g){
				String str1 ="音乐可视化展示";                   //文字1
				g.setFont(new Font("黑体",Font.BOLD,40));             //设置字体
				g.setColor(Color.GREEN);                   //设置颜色
				g.drawString(str1,50,60);                 //将文字画出
				
				String str2 ="黄海军作";                          //文字2
				g.setFont(new Font("楷体",Font.BOLD,30));
				g.setColor(Color.BLUE);
				g.drawString(str2,120,120);
			}
		};
		JPanel buttonpanel =new JPanel();            //定义一个panel存放按钮
		JButton start =new JButton("点击开始");            //按钮
		start.addActionListener(new ActionListener(){        //对按钮进行实践监听
			
			public void actionPerformed(ActionEvent e){
				new ShowFrame();                      //进入游戏
				dispose();                    //释放frame上所有组件
			}
		});
		
		buttonpanel.add(start);
		contentpanel.add(paintpanel);
		contentpanel.add(buttonpanel);
		setContentPane(contentpanel);
	}
	/**
	 * 主函数
	 * */
	public static void main(String[] str){
		
		new MainFrame();
	}
}
