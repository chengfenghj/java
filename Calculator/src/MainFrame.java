
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame extends JFrame{
	
	private static final int ERROR_MESSAGE = 0;
	private Calculator calculator;
	private String input;
	private float result;
	private int error;
	
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JTextField inputField;
	private JTextField outputField;
	private JButton calculate;
	
	public MainFrame(){

		setTitle("����������ȷ��������ı��ʽ�﷨������");         
		setLocation(400,200);      
		setSize(400,200);           
		setResizable(false);        //���ô��ڲ��ɵ��ڴ�С
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		calculator =new Calculator();
		
		inputLabel =new JLabel("���䡡�롡:");
		outputLabel =new JLabel("���䡡����:");
		inputField =new JTextField(25);
		outputField =new JTextField(25);
		calculate =new JButton("���ơ��㡡");
		
//		outputField.setEditable(false);
		calculate.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				input =inputField.getText();
				error =calculator.analyze(input);
				if(error == 0){
					result =calculator.getResult();
					outputField.setText(String.valueOf(result));
				}
				else 
					error();
			}
		});
		
		JPanel contentPanel =new JPanel(new FlowLayout(20));
		contentPanel.add(inputLabel);
		contentPanel.add(inputField);
		contentPanel.add(outputLabel);
		contentPanel.add(outputField);
		contentPanel.add(calculate);
		
		setContentPane(contentPanel);
		setVisible(true);           //���ô��ڿɼ�
	}
	private void error(){
		JOptionPane.showMessageDialog(null,getErrorMessage(),"����",ERROR_MESSAGE);
	}
	private String getErrorMessage(){
		if(error == 11)
			return "С������治�ܼ�С����";
		if(error == 12)
			return "���ֺ��治�ܼ� (";
		if(error == 13)
			return "��������治�ܼ������";
		if(error == 14)
			return "��������治�ܼ�)";
		if(error == 15)
			return "������ʽ������";
		if(error == 16)
			return "(���治�ܼ������";
		if(error == 18)
			return ")���治�ܼ�����";
		if(error == 19)
			return ")���治�ܼ�(";
		if(error == 2)
			return "���뺬�зǷ��ַ�";
		if(error == 3)
			return "��ƥ������";
		if(error == 4)
			return "ȱ�ٽ�����#";
		return "δ֪����";
	}
	
	public static void main(String[] srgs){
		new MainFrame();
	}

}
