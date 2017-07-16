package mainpackge;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.VoiceStatus;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ShowFrame extends JFrame{

	private String title ="MIDI音乐可视化展示";
	private long tick =0;
	private int dit =0;
	private int playing =0;                  //记录正在播放的歌曲下标
	private int time1;                      //记录要播放的文件时间长度
	private int time2 =0;                    //记录线程时间
	private int t =0;                       //记录秒数
	private int[] mark;                      //音轨播放位置的标记
	private String minute ="0";              //显示在文本框的分钟长
	private String second ="0";              //显示在文本框的秒长
	private boolean play =false;              //是否在播放
	 
	ShowPanel showPanel;                //用于显示的画板
	ListPanel listPanel;                      //用于显示列表的面板
	JPanel controlPanel;                 //用于放控制按钮的面板
	JProgressBar progress;             //定义进度条
	JTextField timeText1;             //时间文本
	JTextField timeText2;            
	MIDIFile midiFile;               //MIDIFile 实例化
	
	private PlayThread playThread;             //线程实例化
	private Sequence midi;                      //要播放的midi文件
	private Sequencer sequencer;                //播放器
	private MidiMessage message;                  //midi消息类
	private Track[] tracks;                      //音轨数组
	private MidiEvent event;                   //midi时间
	
	public ShowFrame(){
		
		setTitle(title);
		setSize(600,600);
		setLocation(220,80);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//实例化组件
		showPanel =new ShowPanel();
		listPanel =new ListPanel();
		controlPanel =new JPanel();
		midiFile =new MIDIFile();
		
		FrameLayout();
		setVisible(true);
		
		init(playing);
		try{
			sequencer =MidiSystem.getSequencer();            //获取midi播放器
			sequencer.open();                           //打开
		}catch(Exception e){
			System.out.println(e);
		}
		
		playThread =new PlayThread();
		playThread.start();
		
	}
	
	//布局方法
	public void FrameLayout(){
		
		Container contentPanel =getContentPane();
		GridBagLayout gblayout =new GridBagLayout();                //使用网格包布局方式
		contentPanel.setLayout(gblayout);
		
		Insets insets =new Insets(0,0,0,0); 
		//显示界面的位置
		GridBagConstraints show =new GridBagConstraints(0,0,7,9,7,9
				,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		//列表界面的位置
		GridBagConstraints list =new GridBagConstraints(7,0,3,9,3,9
				,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		//控制界面的位置
		GridBagConstraints control =new GridBagConstraints(0,10,0,1,0,1
				,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		
		controlPanel.setLayout(new GridLayout(2,1));
		controlPanel.setBackground(Color.CYAN);
		
		progress =new JProgressBar(SwingConstants.HORIZONTAL);
		progress.setBackground(Color.PINK);
		progress.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				
			}
			
		});
		timeText1 =new JTextField("00:00",4);
		timeText1.setEditable(false);        //文本框不可编辑
		timeText2 =new JTextField("00:00",4);
		timeText2.setEditable(false);
		
		JButton last =new JButton("last");
		last.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				last();
			}
			
		});
		
		JButton next =new JButton("next");
		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				next();
			}
			
		});
		
		JButton start =new JButton("start");
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				play();
			}
			
		});
		
		JButton stop =new JButton("stop");
		stop.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				stop();
			}
		});
		
		JPanel progressPanel =new JPanel();
		progressPanel.setLayout(new FlowLayout());
		progressPanel.add(timeText1);
		progressPanel.add(progress);
		progressPanel.add(timeText2);
		
		JPanel buttonPanel =new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(last);
		buttonPanel.add(start);
		buttonPanel.add(stop);
		buttonPanel.add(next);
		
		controlPanel.add(progressPanel);
		controlPanel.add(buttonPanel);
		
		gblayout.setConstraints(showPanel,show);
		add(showPanel);
		gblayout.setConstraints(listPanel, list);
		add(listPanel);
		gblayout.setConstraints(controlPanel, control);
		add(controlPanel);
	}
	
	//设置时间文本
	public void setText(int time,int index){

		int minute =time/60;
		int second =time%60;
		if(minute>9)
			this.minute =String.valueOf(minute);
		else
			this.minute ="0"+String.valueOf(minute);
		if(second>9)
			this.second =String.valueOf(second);
		else
			this.second ="0"+String.valueOf(second);
		if(index==1)
			timeText1.setText(this.minute+":"+this.second);
		else if(index==2)
			timeText2.setText(this.minute+":"+this.second);
		else
			System.out.println("指针错误");
	}
	
	//由音调改到音阶
	private int getNote(int code){
		return code/12-1;
	}
	//播放
	public void init(int index){
		
		midi =midiFile.getSequence(index);                //获得可播放mid文件
		tracks =midi.getTracks();                     //获得mid文件的音轨数组
		time1 =(int)(midi.getMicrosecondLength()/1000000);           //获得mid文件的时间长度（单位：微秒）
		mark =new int[tracks.length];
		for(int i=0;i<mark.length;i++)                          //初始化mark数组
			mark[i]=0;
		showPanel.setTrackAmount(tracks.length);              //设置showpanel中的音轨数
		progress.setMinimum(0);
		progress.setMaximum(time1);
		progress.setValue(0);
		setText(time1,2);
		time2 =0;
		t =0;
		setText(time2,1);
	}
	public void play(){
		try{
			sequencer.setSequence(midi);              //向播放器添加播放的文件
			sequencer.start();                //播放
		}catch(Exception e){
			System.out.println(e);
		}
		play =true;
		showPanel.setShowImage(midiFile.getImage(playing));
		setTitle(title+" ---- "+listPanel.getString(playing));
	}
	//暂停
	public void stop(){
		sequencer.stop();
		play =false;
	}
	public void next(){
		listPanel.next();
		if(playing<midiFile.size()-1)
			playing++;
		else
			playing =0;
		tick =0;
		init(playing);
		play();
	}
	public void last(){
		listPanel.last();
		if(playing>0)
			playing--;
		else
			playing =midiFile.size()-1;
		init(playing);
		play();
	}
	class PlayThread extends Thread implements Runnable{
		
		public void run(){
			long ss =0;                  //过渡变量
			long s =0;
			boolean exist =false;           //用来判断是否存在发音音符
			byte[] states;                     //存放数据的数组
			long position;                      //存放播放进度
			while(true){                
				while(play){
					
					if(playing!=listPanel.getPlay()){
						playing =listPanel.getPlay();
						init(playing);
						play();
						continue;
					}
					
					t++;
					if(t>=20&&time2<=ShowFrame.this.time1){                   //播放结束时间不再增加
						progress.setValue(progress.getValue()+1);
						t -=20;
						time2 +=1;
						setText(time2,1);                    //改变时间进度文本的值
					}
					position =sequencer.getTickPosition();          //获取当前播放进度
					dit =(int)(position -tick);
					tick =position;
//					System.out.print(dit+"    ");
//					System.out.println(position);
					for(int i=0;i<tracks.length;i++){
						//判断是否是持续音，如果是持续因就选用最后一个音的值
						while(true){
							
							event =tracks[i].get(mark[i]);             
							ss =event.getTick();                  //获取指定MIDI事件的时间戳
							s =Math.abs(position-ss);
							
							if(s<dit+1&&(mark[i]<tracks[i].size()-1)){                    //帧数改变还需功夫
								exist =true;
								mark[i]++;
							}
							else
								break;
						}
						if(exist){
//							mark[i]--;
							event =tracks[i].get(mark[i]);             //获取指定音轨上的事件序列
							message =event.getMessage();               //获取指定MIDI事件的消息
							states =message.getMessage();
							showPanel.changeHeight(i,getNote(states[1]));
						}
						exist =false;
						
					}
					
//					int r =5;
//					for(int i=0;i<tracks[r].size();i++){
//						System.out.print(tracks[r].size()+"   ");
//						event =tracks[r].get(i);             //获取指定音轨上的事件序列
//						message =event.getMessage();               //获取指定MIDI事件的消息
//						states =message.getMessage();
//						for(int j=0;j<states.length;j++)
//							System.out.print(states[j]+"   ");
//						System.out.println(" ");
//					}
					//如果一首歌播放结束，就开始播放下一首
					if(!sequencer.isRunning()){
						next();
					}
					showPanel.repaint();            //界面更新
					try{
						sleep(50);
					}catch(Exception e){
						System.out.println(e);
					}
				}
				System.out.print(" ");
//				run =0;
			}
		}
	}
}
