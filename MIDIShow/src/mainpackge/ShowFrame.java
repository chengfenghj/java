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

	private String title ="MIDI���ֿ��ӻ�չʾ";
	private long tick =0;
	private int dit =0;
	private int playing =0;                  //��¼���ڲ��ŵĸ����±�
	private int time1;                      //��¼Ҫ���ŵ��ļ�ʱ�䳤��
	private int time2 =0;                    //��¼�߳�ʱ��
	private int t =0;                       //��¼����
	private int[] mark;                      //���첥��λ�õı��
	private String minute ="0";              //��ʾ���ı���ķ��ӳ�
	private String second ="0";              //��ʾ���ı�����볤
	private boolean play =false;              //�Ƿ��ڲ���
	 
	ShowPanel showPanel;                //������ʾ�Ļ���
	ListPanel listPanel;                      //������ʾ�б�����
	JPanel controlPanel;                 //���ڷſ��ư�ť�����
	JProgressBar progress;             //���������
	JTextField timeText1;             //ʱ���ı�
	JTextField timeText2;            
	MIDIFile midiFile;               //MIDIFile ʵ����
	
	private PlayThread playThread;             //�߳�ʵ����
	private Sequence midi;                      //Ҫ���ŵ�midi�ļ�
	private Sequencer sequencer;                //������
	private MidiMessage message;                  //midi��Ϣ��
	private Track[] tracks;                      //��������
	private MidiEvent event;                   //midiʱ��
	
	public ShowFrame(){
		
		setTitle(title);
		setSize(600,600);
		setLocation(220,80);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ʵ�������
		showPanel =new ShowPanel();
		listPanel =new ListPanel();
		controlPanel =new JPanel();
		midiFile =new MIDIFile();
		
		FrameLayout();
		setVisible(true);
		
		init(playing);
		try{
			sequencer =MidiSystem.getSequencer();            //��ȡmidi������
			sequencer.open();                           //��
		}catch(Exception e){
			System.out.println(e);
		}
		
		playThread =new PlayThread();
		playThread.start();
		
	}
	
	//���ַ���
	public void FrameLayout(){
		
		Container contentPanel =getContentPane();
		GridBagLayout gblayout =new GridBagLayout();                //ʹ����������ַ�ʽ
		contentPanel.setLayout(gblayout);
		
		Insets insets =new Insets(0,0,0,0); 
		//��ʾ�����λ��
		GridBagConstraints show =new GridBagConstraints(0,0,7,9,7,9
				,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		//�б�����λ��
		GridBagConstraints list =new GridBagConstraints(7,0,3,9,3,9
				,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		//���ƽ����λ��
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
		timeText1.setEditable(false);        //�ı��򲻿ɱ༭
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
	
	//����ʱ���ı�
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
			System.out.println("ָ�����");
	}
	
	//�������ĵ�����
	private int getNote(int code){
		return code/12-1;
	}
	//����
	public void init(int index){
		
		midi =midiFile.getSequence(index);                //��ÿɲ���mid�ļ�
		tracks =midi.getTracks();                     //���mid�ļ�����������
		time1 =(int)(midi.getMicrosecondLength()/1000000);           //���mid�ļ���ʱ�䳤�ȣ���λ��΢�룩
		mark =new int[tracks.length];
		for(int i=0;i<mark.length;i++)                          //��ʼ��mark����
			mark[i]=0;
		showPanel.setTrackAmount(tracks.length);              //����showpanel�е�������
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
			sequencer.setSequence(midi);              //�򲥷�����Ӳ��ŵ��ļ�
			sequencer.start();                //����
		}catch(Exception e){
			System.out.println(e);
		}
		play =true;
		showPanel.setShowImage(midiFile.getImage(playing));
		setTitle(title+" ---- "+listPanel.getString(playing));
	}
	//��ͣ
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
			long ss =0;                  //���ɱ���
			long s =0;
			boolean exist =false;           //�����ж��Ƿ���ڷ�������
			byte[] states;                     //������ݵ�����
			long position;                      //��Ų��Ž���
			while(true){                
				while(play){
					
					if(playing!=listPanel.getPlay()){
						playing =listPanel.getPlay();
						init(playing);
						play();
						continue;
					}
					
					t++;
					if(t>=20&&time2<=ShowFrame.this.time1){                   //���Ž���ʱ�䲻������
						progress.setValue(progress.getValue()+1);
						t -=20;
						time2 +=1;
						setText(time2,1);                    //�ı�ʱ������ı���ֵ
					}
					position =sequencer.getTickPosition();          //��ȡ��ǰ���Ž���
					dit =(int)(position -tick);
					tick =position;
//					System.out.print(dit+"    ");
//					System.out.println(position);
					for(int i=0;i<tracks.length;i++){
						//�ж��Ƿ��ǳ�����������ǳ������ѡ�����һ������ֵ
						while(true){
							
							event =tracks[i].get(mark[i]);             
							ss =event.getTick();                  //��ȡָ��MIDI�¼���ʱ���
							s =Math.abs(position-ss);
							
							if(s<dit+1&&(mark[i]<tracks[i].size()-1)){                    //֡���ı仹�蹦��
								exist =true;
								mark[i]++;
							}
							else
								break;
						}
						if(exist){
//							mark[i]--;
							event =tracks[i].get(mark[i]);             //��ȡָ�������ϵ��¼�����
							message =event.getMessage();               //��ȡָ��MIDI�¼�����Ϣ
							states =message.getMessage();
							showPanel.changeHeight(i,getNote(states[1]));
						}
						exist =false;
						
					}
					
//					int r =5;
//					for(int i=0;i<tracks[r].size();i++){
//						System.out.print(tracks[r].size()+"   ");
//						event =tracks[r].get(i);             //��ȡָ�������ϵ��¼�����
//						message =event.getMessage();               //��ȡָ��MIDI�¼�����Ϣ
//						states =message.getMessage();
//						for(int j=0;j<states.length;j++)
//							System.out.print(states[j]+"   ");
//						System.out.println(" ");
//					}
					//���һ�׸貥�Ž������Ϳ�ʼ������һ��
					if(!sequencer.isRunning()){
						next();
					}
					showPanel.repaint();            //�������
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
