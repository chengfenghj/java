package mainpackge;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.ImageIcon;

public class MIDIFile{

	private Sequence midi1 =null;
	private Sequence midi2 =null;
	private Sequence midi3 =null;
	private Sequence midi4 =null;
	private Sequence midi5 =null;
	private Sequence midi6 =null;
	private Sequence midi7 =null;
	private Sequence midi8 =null;
	
	private Image img1 =null;
	private Image img2 =null;
	private Image img3 =null;
	private Image img4 =null;
	private Image img5 =null;
	private Image img6 =null;
	private Image img7 =null;
	private Image img8 =null;
	
	List<Sequence> midis =new ArrayList<Sequence>();
	List<Image> images =new ArrayList<Image>();
	
	public MIDIFile(){
		try{
			midi1 =MidiSystem.getSequence(new File("src/res/mid/l2.mid"));
			midi2 =MidiSystem.getSequence(new File("src/res/mid/b2.mid"));
			midi3 =MidiSystem.getSequence(new File("src/res/mid/h3.mid"));
			midi4 =MidiSystem.getSequence(new File("src/res/mid/q0.mid"));
			midi5 =MidiSystem.getSequence(new File("src/res/mid/x5.mid"));
			midi6 =MidiSystem.getSequence(new File("src/res/mid/x6.mid"));
			midi7 =MidiSystem.getSequence(new File("src/res/mid/d7.mid"));
			midi8 =MidiSystem.getSequence(new File("src/res/mid/w8.mid"));
		}catch(Exception e){
			System.out.println(e);
		}
		midis.add(midi1);
		midis.add(midi2);
		midis.add(midi3);
		midis.add(midi4);
		midis.add(midi5);
		midis.add(midi6);
		midis.add(midi7);
		midis.add(midi8);
		
		img1 =new ImageIcon(getClass().getResource("/res/img/img1.jpg")).getImage();
		img2 =new ImageIcon(getClass().getResource("/res/img/img2.jpg")).getImage();
		img3 =new ImageIcon(getClass().getResource("/res/img/img3.jpg")).getImage();
		img4 =new ImageIcon(getClass().getResource("/res/img/img4.jpg")).getImage();
		img5 =new ImageIcon(getClass().getResource("/res/img/img5.jpg")).getImage();
		img6 =new ImageIcon(getClass().getResource("/res/img/img6.jpg")).getImage();
		img7 =new ImageIcon(getClass().getResource("/res/img/img7.jpg")).getImage();
		img8 =new ImageIcon(getClass().getResource("/res/img/img8.jpg")).getImage();
		
		images.add(img1);
		images.add(img2);
		images.add(img3);
		images.add(img4);
		images.add(img5);
		images.add(img6);
		images.add(img7);
		images.add(img8);
	}
	public Sequence getSequence(int i){
		if(midis.get(i)==null)
			System.out.println("ÎÄ¼þÎª¿Õ£¡");
		return midis.get(i);
	}
	public Image getImage(int i){
		return images.get(i);
	}
	public int size(){
		return midis.size();
	}
}
