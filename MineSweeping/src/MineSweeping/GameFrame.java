package MineSweeping;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame{
	GamePanel gamepanel;
	int mineamount=10;
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	int x=toolkit.getScreenSize().width-20;
	int y=toolkit.getScreenSize().height-20;
	
	public GameFrame(){
		
		setTitle("军哥扫雷");
		setLocation(300,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		gamepanel=new GamePanel(mineamount);
		setSize(gamepanel.getWidth(),gamepanel.getHeight());
		
		
		JMenuBar menu =new JMenuBar(); 
		JMenu startmenu =new JMenu("     开     始     ");
		JMenu hardmenu =new JMenu("     难     度     ");
		
		JMenuItem restart =new JMenuItem("重 新 游 戏");
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gamepanel.start(mineamount);
			}
		});
		startmenu.add(restart);
		
		JMenuItem exit =new JMenuItem("退出游戏");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		startmenu.add(exit);
		
		JMenuItem tenmine =new JMenuItem("10x雷");
		tenmine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mineamount=10;
				gamepanel.start(mineamount);
				setSize(gamepanel.getWidth(),gamepanel.getHeight());
				setLocation((x-gamepanel.getWidth())/2,(y-gamepanel.getHeight())/2);
			}
		});
		hardmenu.add(tenmine);
		
		JMenuItem thirtymine =new JMenuItem("30x雷");
		thirtymine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mineamount=30;
				gamepanel.start(mineamount);
				setSize(gamepanel.getWidth(),gamepanel.getHeight());
				setLocation((x-gamepanel.getWidth())/2,(y-gamepanel.getHeight())/2);
			}
		});
		hardmenu.add(thirtymine);
		
		JMenuItem sixtymine =new JMenuItem("60x雷");
		sixtymine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mineamount=60;
				gamepanel.start(mineamount);
				setSize(gamepanel.getWidth(),gamepanel.getHeight());
				setLocation((x-gamepanel.getWidth())/2,(y-gamepanel.getHeight())/2);
			}
		});
		hardmenu.add(sixtymine);
		
		menu.add(startmenu);
		menu.add(hardmenu);
		setJMenuBar(menu);
		
		setVisible(true);
		
		Container contentpane =getContentPane();
		contentpane.add(gamepanel);
	}
}
