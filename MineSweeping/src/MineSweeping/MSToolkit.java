package MineSweeping;

import java.awt.Image;

import javax.swing.ImageIcon;

public class MSToolkit {

	//һЩ�õ���ͼƬ
	Image snow =new ImageIcon(getClass().getResource("/res/xuedi.gif")).getImage();
	Image flag =new ImageIcon(getClass().getResource("/res/qizi.gif")).getImage();
	Image space =new ImageIcon(getClass().getResource("/res/kongdi.gif")).getImage();
	Image query =new ImageIcon(getClass().getResource("/res/wenhao.gif")).getImage();
	Image mine1 =new ImageIcon(getClass().getResource("/res/dilei1.gif")).getImage();
	Image mine2 =new ImageIcon(getClass().getResource("/res/dilei2.gif")).getImage();
	Image mine3 =new ImageIcon(getClass().getResource("/res/dilei3.gif")).getImage();
	Image one =new ImageIcon(getClass().getResource("/res/1.gif")).getImage();
	Image two =new ImageIcon(getClass().getResource("/res/2.gif")).getImage();
	Image three =new ImageIcon(getClass().getResource("/res/3.gif")).getImage();
	Image four =new ImageIcon(getClass().getResource("/res/4.gif")).getImage();
	Image five =new ImageIcon(getClass().getResource("/res/5.gif")).getImage();
	Image six =new ImageIcon(getClass().getResource("/res/6.gif")).getImage();
	Image seven =new ImageIcon(getClass().getResource("/res/7.gif")).getImage();
	Image eight =new ImageIcon(getClass().getResource("/res/8.gif")).getImage();
	
	private int[][] rear ;                  //������¼��ͼ�ѷ�����δ������λ��
											//0: �ѷ���
											//1: δ����
											//2: �ѱ���
											//3: ���ʺ�
	private int[][] front ;                 //������¼��ͼ�ϵ�λ����Ϣ
											//0: �յ�
											//1~8: ����
											//9: ��
	
	private int xlength;
	private int ylength;
	
	public void start(int mineamount){
		
		if(mineamount==10){
			rear=new int[10][10];
			front =new int[10][10];
			xlength=10;
			ylength=10;
		}
		if(mineamount==30){
			rear=new int[16][15];
			front =new int[16][15];
			xlength=16;
			ylength=15;
			
		}
		if(mineamount==60){
			rear=new int[20][18];
			front =new int[20][18];
			xlength=20;
			ylength=18;
		}
		for(int i=0;i<xlength;i++){
			for(int j=0;j<ylength;j++){
				rear[i][j]=1;
				front[i][j]=0;
			}
		}
		for(int a=0;a<mineamount;a++){
			//�����һ��λ��������һ����
			int i=(int) (Math.random()*xlength);
			int j=(int) (Math.random()*ylength);
			//����˴������ף��Ž�ȥһ����
			if(front[i][j]!=9){
				front[i][j]=9;
				//����Χ�����ּ�һ
				setValue(i,j);
			}
			//����˴��Ѿ������ˣ��ͰѸո����ɵ���ȡ����
			else
				a--;
		}
	}
	private void setValue(int i,int j){
		if(i-1>=0){
			if(front[i-1][j]<8)
				front[i-1][j]++;
			if(j+1<ylength){
				if(front[i-1][j+1]<8)
					front[i-1][j+1]++;
			}
		}
		if(j+1<ylength){
			if(front[i][j+1]<8)
				front[i][j+1]++;
			if(i+1<xlength){
				if(front[i+1][j+1]<8)
					front[i+1][j+1]++;
			}
		}
		if(i+1<xlength){
			if(front[i+1][j]<8)
				front[i+1][j]++;
			if(j-1>=0){
				if(front[i+1][j-1]<8)
					front[i+1][j-1]++;
			}
		}
		if(j-1>=0){
			if(front[i][j-1]<8)
				front[i][j-1]++;
			if(i-1>=0){
				if(front[i-1][j-1]<8)
					front[i-1][j-1]++;
			}
		}
	}
	
	public void rightClick(int i,int j){
		if(rear[i][j]==0)
			return;
		else if(rear[i][j]==3){
			rear[i][j]=1;
			return;
		}
		rear[i][j]++;
	}
	public void leftClick(int i,int j){
		if(rear[i][j]!=1)
			return;
		search(i,j);
	}
	
	/**
	 * ��8����������������0��λ�÷���
	 * @param i
	 * @param j
	 */
	private void search(int i,int j){
		//����˴��Ѿ�������������
		if(rear[i][j]==0)
			return;
		//����˴�û�б�����Ҳû�б����ף�����
		if(rear[i][j]!=2)
			rear[i][j]=0;
		//���������0���ݹ鷭����Χ8������
		if(front[i][j]==0){
			if(!outOfBoard(i, j-1))
				search(i,j-1);
			if(!outOfBoard(i, j+1))
				search(i,j+1);
			if(!outOfBoard(i-1, j))
				search(i-1,j);
			if(!outOfBoard(i+1, j))
				search(i+1,j);
			if(!outOfBoard(i+1, j+1))
				search(i+1,j+1);
			if(!outOfBoard(i+1, j-1))
				search(i+1,j-1);
			if(!outOfBoard(i-1, j-1))
				search(i-1,j-1);
			if(!outOfBoard(i-1, j+1))
				search(i-1,j+1);
		}
		else
			return;
	}
	
	private boolean outOfBoard(int x, int y){
		if(x < 0 || y < 0 || x >= xlength || y >= ylength)
			return true;
		return false;
	}
	public boolean isRight(int i,int j){
		if(rear[i][j]==2&&front[i][j]==9)
			return true;
		else
			return false;
	}
	public int getRearValue(int i,int j){
		return rear[i][j];
	}
	public int getFrontValue(int i,int j){
		return front[i][j];
	}
	public int getXLength(){
		return xlength;
	}
	public int getYLength(){
		return ylength;
	}
}
