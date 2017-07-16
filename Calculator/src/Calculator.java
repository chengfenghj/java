import java.util.Stack;


public class Calculator {

	private Stack<Character> optr;
	private Stack<Float> opnd;
	private float result;
	private boolean bracket =false;
	
	public Calculator(){
		
		optr =new Stack<Character>();
		opnd =new Stack<Float>();
	}
	
	public int analyze(String input){
		String num ="";
		char index;
		char stackTop;
		int error =analyzer(input);
		
		if(error != 0)
			return error;
		
		for(int i =0;i < input.length();i++){
			index =input.charAt(i);
			if(index == '#'){
				if(!bracket)
					opnd.push(Float.valueOf(num));
				calculateAll();
			}
			else if(isNumbers(index)){
				num +=index;
			}
			else if(isBracket(index)){
				if(index == ')'){
					opnd.push(Float.valueOf(num));
					num ="";
					bracket =true;
					calculateBracket();
				}
				else{
					optr.push(index);
				}
			}
			else if(isOperators(index)){
				if(!bracket)
					opnd.push(Float.valueOf(num));
				else 
					bracket =false;
				num ="";
				if(!optr.empty()){
					stackTop =optr.peek();
					while(OperatorTable.compare(index,stackTop) == OperatorTable.LESS){
						calculate();
						if(optr.empty())
							break;
						stackTop =optr.peek();
					}
				}
				optr.push(index);
			}
		}
		return 0;
	}
	public float getResult(){
		return result;
	}
	private int analyzer(String input){
		char now =' ';
		char next =' ';
		int bracketAmount =0;
		for(int i =0;i < input.length() -1;i++){
			now =input.charAt(i);
			next =input.charAt(i+1);
			if(isNumbers(now)){
				if(now == '.'){
					//С������治�ܼ�С����
					if(next == '.')
						return 11;
				}
				else{
					//���ֺ��治�ܼ�����
					if(next == '(')
						return 12;
				}
			}
			else if(isOperators(now)){
				//��������治�ܼ������
				if(isOperators(next))
					return 13;
				//��������治�ܼӷ�����
				if(next == ')')
					return 14;
				//��������治�ܼ�#
				if(next == '#')
					return 15;
			}
			else if(isBracket(now)){
				if(now == '('){
					bracketAmount ++;
					//���ź��治�ܼ������
					if(isOperators(next))
						return 16;
					//���ź��治�ܼ�#
					if(next == '#')
						return 15;
				}
				else {
					bracketAmount --;
					//�����ź��治�ܼ�����
					if(isNumbers(next))
						return 18;
					//�����ź��治�ܼ�����
					if(next == '(')
						return 19;
				}
			}
			else{
				//�Ƿ�����
				return 2;
			}
			if(now == '#')
				return 0;
		}
		if(next == '(')
			bracketAmount ++;
		if(next == ')')
			bracketAmount --;
		if(bracketAmount != 0)
			//����������ƥ��
			return 3;
		if(next != '#')
			//û��#
			return 4;
		return 0;
	}
	private boolean isNumbers(char op){
		switch(op){
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '.':
			return true;
		}
		return false;
	}
	private boolean isOperators(char op){
		switch(op){
		case '+':
		case '-':
		case '*':
		case '/':
			return true;
		}
		return false;
	}
	private boolean isBracket(char op){
		switch(op){
		case '(':
		case ')':
			return true;
		}
		return false;
	}
	private void calculate(){
		float num2 =opnd.pop();
		float num1 =opnd.pop();
		char operator =optr.pop();
		switch(operator){
		case '+':
			num1 =num1 +num2;
			break;
		case '-':;
			num1 =num1 -num2;
			break;
		case '*':
			num1 =num1 *num2;
			break;
		case '/':
			num1 =num1 /num2;
			break;
		}
		opnd.push(num1);
		System.out.println("������Ϊ��" +num1);
	}
	private void calculateAll(){
		while(!optr.empty()){
			calculate();
		}
		result =opnd.pop();
	}
	private void calculateBracket(){
		if(optr.empty())
			return;
		char operator =optr.peek();
		while(operator != '('){
			calculate();
			operator =optr.peek();
		}
		operator =optr.pop();
	}
}
