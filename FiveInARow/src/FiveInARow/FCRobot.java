package FiveInARow;

import java.util.ArrayList;
import java.util.List;

public class FCRobot {

	private final int lenght=15;         //���̿��
	
	private final int BLEND=5;         //��ϵ����
	private final int NONE=0;            //û������
	private final int MINE=200;          //����һ����
	private final int DOUBLEMINE=1000;            //����������
	private final int TRIPLEMINE=10000;             //����������
	private final int QUADRUMINE=4000000;           //�����ĸ���
	private final int ENEMY=100;                  //�з�һ����
	private final int DOUBLEENEMY=400;             //�з�������
	private final int TRIPLEENEMY=6000;            //�з�������
	private final int QUADRUENEMY=500000;           //�з��ĸ���
	
	private int x=7,y=7;
	private int chesscolor=0;              //������ɫ
	private int lastcolor=0;               //��һ����ɫ�����ڻ���
	private int rboard[][]=new int[lenght][lenght];           //���ڴ�����̵�����
	private int score[][]=new int[lenght][lenght];             //���ڴ������λ�õķ�ֵ
	
	List<Bound> bounds =new ArrayList<Bound>();            //���ڴ����ͬ��ֵ�����꼯��
	
	public FCRobot(){
		
	}
	/**
	 * ��ʼ������
	 * */
	public void init(){
		for(int i=0;i<lenght;i++){
			for(int j=0;j<lenght;j++)
				rboard[i][j]=0;             //�ʼ�����϶�û����
		}
		for(int i=0;i<lenght;i++){
			for(int j=0;j<lenght;j++)
				score[i][j]=10;             //�ʼ����λ�õ����ȼ�Ϊ10
		}
	}
	/**
	 *�����ֵ 
	 */
	private int getScore(int amount1,int amount2){
		
		int score=0;
			if(amount2==0)
				score=NONE;             //û������
			else if(amount2==amount1){    //���Ǽ�������
				if(amount2==1)
					score=MINE;
				if(amount2==2)
					score=DOUBLEMINE;
				if(amount2==3)
					score=TRIPLEMINE;
				if(amount2==4)
					score=QUADRUMINE;
			}
			else if(amount2==-amount1){     //���ǵз�����
				if(amount2==1)
					score=ENEMY;
				if(amount2==2)
					score=DOUBLEENEMY;
				if(amount2==3)
					score=TRIPLEENEMY;
				if(amount2==4)
					score=QUADRUENEMY;
			}
			else                          //˫�����Ӷ���
				score=BLEND;
			return score;
	}
	/**
	 * �������ϵ�λ�ý�������
	 * */
	private void valuation(int x,int y){
		
		int sx=x;
		int sy=y;
		int formerscore=0;
		int newscore=0;
		int amount1=0;
		int amount2=0;
		int color=rboard[x][y];            //���ԭ�е�����
		
		for(int i=4;i>0;i--){
			//�µķ���
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy];            //��Ԫ������ӷ�ֵ�ĺ�
				if(rboard[sx-i+j][sy]!=0)
					amount2++;             //��ס���ӵ�����
			}
			newscore=getScore(amount1,amount2);
			//�ɵķ���
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy];            //��Ԫ������ӷֺ�
				if(rboard[sx-i+j][sy]!=0)
					amount2++;             //��ס���ӵ�����
			}
			formerscore=getScore(amount1,amount2);
			//����Ԫ�鸳ֵ
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght)
					break;
				if(score[sx-i+j][sy]!=-1)
					score[sx-i+j][sy]=score[sx-i+j][sy]-formerscore+newscore;
			}
			rboard[x][y]=color;
			amount1=0;
			amount2=0;
			
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght||sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy-i+j];            //��Ԫ������ӷ�ֵ�ĺ�
				if(rboard[sx-i+j][sy-i+j]!=0)
					amount2++;             //��ס���ӵ�����
			}
			newscore=getScore(amount1,amount2);
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght||sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy-i+j];            //��Ԫ������ӷֺ�
				if(rboard[sx-i+j][sy-i+j]!=0)
					amount2++;             //��ס���ӵ�����
			}
			formerscore=getScore(amount1,amount2);
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght||sy-i<0||sy-i+j>=lenght)
					break;
				if(score[sx-i+j][sy-i+j]!=-1)
					score[sx-i+j][sy-i+j]=score[sx-i+j][sy-i+j]-formerscore+newscore;
			}
			rboard[x][y]=color;
			amount1=0;
			amount2=0;

			for(int j=0;j<5;j++){
				if(sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx][sy-i+j];            //��Ԫ������ӷ�ֵ�ĺ�
				if(rboard[sx][sy-i+j]!=0)
					amount2++;             //��ס���ӵ�����
			}
			newscore=getScore(amount1,amount2);
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx][sy-i+j];            //��Ԫ������ӷֺ�
				if(rboard[sx][sy-i+j]!=0)
					amount2++;             //��ס���ӵ�����
			}
			formerscore=getScore(amount1,amount2);
			for(int j=0;j<5;j++){
				if(sy-i<0||sy-i+j>=lenght)
					break;
				if(score[sx][sy-i+j]!=-1)
					score[sx][sy-i+j]=score[sx][sy-i+j]-formerscore+newscore;
			}
			rboard[x][y]=color;
			amount1=0;
			amount2=0;

			for(int j=0;j<5;j++){
				if(sx+i>=lenght||sx+i-j<0||sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx+i-j][sy-i+j];            //��Ԫ������ӷ�ֵ�ĺ�
				if(rboard[sx+i-j][sy-i+j]!=0)
					amount2++;             //��ס���ӵ�����
			}
			newscore=getScore(amount1,amount2);
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sx+i>=lenght||sx+i-j<0||sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx+i-j][sy-i+j];            //��Ԫ������ӷֺ�
				if(rboard[sx+i-j][sy-i+j]!=0)
					amount2++;             //��ס���ӵ�����
			}
			formerscore=getScore(amount1,amount2);
			for(int j=0;j<5;j++){
				if(sx+i>=lenght||sx+i-j<0||sy-i<0||sy-i+j>=lenght)
					break;
				if(score[sx+i-j][sy-i+j]!=-1)
					score[sx+i-j][sy-i+j]=score[sx+i-j][sy-i+j]-formerscore+newscore;
			}
			rboard[x][y]=color;
			amount1=0;
			amount2=0;
		}
		search();
	}
	/**
	 * �������̵ķ�ֵ
	 * */
	private void search(){
		
		int maxscore=0;
		int n;
		for(int i=0;i<lenght;i++){
			for(int j=0;j<lenght;j++){
				if(score[i][j]==maxscore){
					Bound bound =new Bound();
					bound.x=i;
					bound.y=j;
					bounds.add(bound);
				}
				if(score[i][j]>maxscore){
					maxscore=score[i][j];
					Bound bound =new Bound();
					bound.x=i;
					bound.y=j;
					bounds.clear();
					bounds.add(bound);
				}
			}
		}
		n=(int) (Math.random()*bounds.size());
		x=bounds.get(n).x;
		y=bounds.get(n).y;
	}
	/**
	 * �鿴����
	 * */
	public void readBoard(int x,int y,int chesscolor){
		lastcolor=rboard[x][y];
		if(chesscolor==9){
			rboard[x][y]=0;
			score[x][y]=10;
		}
		else if(this.chesscolor==chesscolor){
			rboard[x][y]=1;
			score[x][y]=-1;
		}
		else{
			rboard[x][y]=-1;
			score[x][y]=-1;
		}
		valuation(x,y);
	}
	/**
	 * ����Xֵ
	 * */
	public int getX(){
		return x;
	}
	/**
	 * ����Yֵ
	 * */
	public int getY(){
		return y;
	}
	/**
	 * ������ɫ
	 * */
	public void setChessColor(int color){
		if(color==0||color==1)
			chesscolor=color;
	}
	/**
	 * ������ɫ
	 * */
	public int getColor(){
		return chesscolor;
	}
}
