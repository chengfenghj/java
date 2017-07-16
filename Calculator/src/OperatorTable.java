
public class OperatorTable {
	
	public static final int LESS =-1;
	public static final int EQUAL =0;
	public static final int MORE =1;
	
	public OperatorTable(){
		
	}
	
	public static int compare(char operator1, char operator2){
		if(operatorGrade(operator1) > operatorGrade(operator2))
			return MORE;
		if(operatorGrade(operator1) < operatorGrade(operator2))
			return LESS;
		return EQUAL;
	}
	private static int operatorGrade(char operator){
		if(operator == '+')
			return 1;
		if(operator == '-')
			return 2;
		if(operator == '*')
			return 3;
		if(operator == '/')
			return 4;
		return 0;
	}

}
