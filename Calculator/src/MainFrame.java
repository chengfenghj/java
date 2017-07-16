
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

		setTitle("基于算符优先分析方法的表达式语法分析器");         
		setLocation(400,200);      
		setSize(400,200);           
		setResizable(false);        //设置窗口不可调节大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		calculator =new Calculator();
		
		inputLabel =new JLabel("　输　入　:");
		outputLabel =new JLabel("　输　出　:");
		inputField =new JTextField(25);
		outputField =new JTextField(25);
		calculate =new JButton("　计　算　");
		
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
		setVisible(true);           //设置窗口可见
	}
	private void error(){
		JOptionPane.showMessageDialog(null,getErrorMessage(),"错误",ERROR_MESSAGE);
	}
	private String getErrorMessage(){
		if(error == 11)
			return "小数点后面不能加小数点";
		if(error == 12)
			return "数字后面不能加 (";
		if(error == 13)
			return "运算符后面不能加运算符";
		if(error == 14)
			return "运算符后面不能加)";
		if(error == 15)
			return "输入表达式不完整";
		if(error == 16)
			return "(后面不能加运算符";
		if(error == 18)
			return ")后面不能加数字";
		if(error == 19)
			return ")后面不能加(";
		if(error == 2)
			return "输入含有非法字符";
		if(error == 3)
			return "不匹配括号";
		if(error == 4)
			return "缺少结束符#";
		return "未知错误";
	}
	
	public static void main(String[] srgs){
		new MainFrame();
	}

}
