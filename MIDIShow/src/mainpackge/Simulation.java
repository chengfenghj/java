package mainpackge;

//���ڶ���ģ�������������
public class Simulation {

	private int speed =5;       //������ٶ�
	private int height;          //�߶�
	
	public Simulation(){
		height =0;
	}
	public void setHeight(int height){
		this.height =height;
	}
	public int getHeight(){
		return height;
	}
	//�������ֵû�з����仯ʱ�ͻ�����
	public void update(){
		if(height>=speed)
			height -=speed;
		else
			height =0;
	}
}
