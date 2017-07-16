package FiveInARow;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame{

	final int whitd =800;           //定义frame的宽度
	final int height =690;           //定义frame的高度

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	int x=toolkit.getScreenSize().width-20;           //获取屏幕的宽度
	int y=toolkit.getScreenSize().height-20;          //获取屏幕的高度
	
	GamePanel gamepanel;
	
	/**
	 * frame的内容
	 * */
	public GameFrame(){
		
		setTitle("军哥五子棋");
		setSize(whitd,height);
		setLocation((x-whitd)/2,(y-height)/2);           //将窗口设置在屏幕中间
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamepanel =new GamePanel();
		
		JMenuBar menubar =new JMenuBar();         //定义一个横向菜单
		JMenu start =new JMenu("　开　始　");         //开始按钮
		JMenu option =new JMenu("　选　项　");         //选项按钮
		
		JMenuItem restart =new JMenuItem("重新开始");        //定义按钮
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("restart");
			}
		});
		start.add(restart);       //添加按钮
		
		JMenuItem fight1 =new JMenuItem("双人对战");
		fight1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("mans");
			}
		});
		start.add(fight1);
		
		JMenuItem fight2 =new JMenuItem("人机　黑");
		fight2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("robotb");
			}
		});
		start.add(fight2);

		JMenuItem fight3 =new JMenuItem("人机　白");
		fight3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("robotw");
			}
		});
		start.add(fight3);
		
		JMenuItem exit =new JMenuItem("退出游戏");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		start.add(exit);
		
		JMenuItem regret =new JMenuItem("　悔　棋　");
		regret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.menuResponse("regret");
			}
		});
		option.add(regret);
		
		JMenuItem giveup =new JMenuItem("　认　输　");
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
