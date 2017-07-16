package FiveInARow;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame{

	final int whitd =800;           //����frame�Ŀ��
	final int height =690;           //����frame�ĸ߶�

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	int x=toolkit.getScreenSize().width-20;           //��ȡ��Ļ�Ŀ��
	int y=toolkit.getScreenSize().height-20;          //��ȡ��Ļ�ĸ߶�
	
	GamePanel gamepanel;
	
	/**
	 * frame������
	 * */
	public GameFrame(){
		
		setTitle("����������");
		setSize(whitd,height);
		setLocation((x-whitd)/2,(y-height)/2);           //��������������Ļ�м�
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamepanel =new GamePanel();
		
		JMenuBar menubar =new JMenuBar();         //����һ������˵�
		JMenu start =new JMenu("������ʼ��");         //��ʼ��ť
		JMenu option =new JMenu("��ѡ���");         //ѡ�ť
		
		JMenuItem restart =new JMenuItem("���¿�ʼ");        //���尴ť
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("restart");
			}
		});
		start.add(restart);       //��Ӱ�ť
		
		JMenuItem fight1 =new JMenuItem("˫�˶�ս");
		fight1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("mans");
			}
		});
		start.add(fight1);
		
		JMenuItem fight2 =new JMenuItem("�˻�����");
		fight2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("robotb");
			}
		});
		start.add(fight2);

		JMenuItem fight3 =new JMenuItem("�˻�����");
		fight3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("robotw");
			}
		});
		start.add(fight3);
		
		JMenuItem exit =new JMenuItem("�˳���Ϸ");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		start.add(exit);
		
		JMenuItem regret =new JMenuItem("���ڡ��塡");
		regret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("regret");
			}
		});
		option.add(regret);
		
		JMenuItem giveup =new JMenuItem("���ϡ��䡡");
		giveup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("giveup");
			}
		});
		option.add(giveup);
		
		menubar.add(start);
		menubar.add(option);
		setJMenuBar(menubar);
		
		setVisible(true);
		setContentPane(gamepanel);
	}
}
