package MineSweeping;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
	
	private int[][] board;             //��������Ϸ���������
									   //-1: δ����
									   //0: �ѷ������յ�
									   //1~8: ����
									   //9: �ѱ���
	private float[][] mine;            //�����������׵ĸ�������
	private float[][] none;            //��������û���׵ĸ�������
	private int hard;                  //��Ϸ�Ѷ�
	private int xLength;               //x����Ľ��泤��
	private int yLength;               //y����Ľ��泤��
	
	private List<Point> mines;         //������м���������׵�λ��
	private List<Point> nones;         //��û���ױ�ǵ�ʱ�򣬴�����и�����͵Ŀո�λ��
	
	
	public Algorithm(int hard){
		this.hard =hard;
		mines =new ArrayList<Point>();
		nones =new ArrayList<Point>();
		initBoard();
	}
	
	/**
	 * ��ʼ����Ϸ����
	 */
	private void initBoard(){
		float prob =0;               //�����׵ĸ���
		switch(hard){
		case 10:
			xLength =10;
			yLength =10;
			prob =1/10;
			break;
		case 30:
			xLength =16;
			yLength =15;
			prob =1/8;
			break;
		case 60:
			xLength =20;
			yLength =18;
			prob =1/6;
			break;
		}
		board =new int[xLength][yLength];
		mine =new float[xLength][yLength];
		none =new float[xLength][yLength];
		
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				board[i][j] =-1;
				mine[i][j] =prob;
				none[i][j] =prob;
			}
		}
	}
	
	/**
	 * ��Ϸ��ʼ����ʼ������
	 * @param hard
	 */
	public void start(int hard){
		this.hard =hard;
		initBoard();
	}
	
	/**
	 * ������׸���
	 */
	public void valuation(){
		int space =leftSpace();
		int leftMine =leftMine();
		//û�пյ���˵��������
		if(space == 0)
			return;
		float p =(float)leftMine/space;
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				if(board[i][j] == -1){
					mine[i][j] =p;
					none[i][j] =p;
//					//�����׵ĵط���Ϊ��������
//					if(Float.compare(p, mine[i][j]) > 0){
//						mine[i][j] =p;
//					}
//					//���Ƿ��׵ĵط���Ϊ��������
//					if(Float.compare(p, none[i][j]) < 0){
//						none[i][j] =p;
//					}
				}
			}
		}
		computeProb();
	}

	/**
	 * �����ͼ��ʣ��Ŀո���
	 * @return
	 */
	private int leftSpace(){
		int space =0;
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				if(board[i][j] == -1)
					space++;
			}
		}
		return space;
	}
	
	/**
	 * ����ʣ��ĵ�����
	 * @return
	 */
	private int leftMine(){
		int leftMine =hard;
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				if(board[i][j] == 9)
					leftMine--;
			}
		}
		return leftMine;
	}
	
	/**
	 * ָ��λ����Χ�ո���
	 * @param x
	 * @param y
	 * @return
	 */
	private int aroundSpace(int x, int y){
		int space =0;
		for(int i =-1; i <= 1; i++){
			for(int j =-1; j <= 1; j++){
				if(!outOfBoard(x+i, y+j) && board[x+i][y+j] == -1)
					space++;
			}
		}
		return space;
	}
	
	/**
	 * ָ��λ����Χ�Ѿ�������
	 * @param x
	 * @param y
	 * @return
	 */
	private int aroundMine(int x, int y){
		int pMine =0;
		for(int i =-1; i <= 1; i++){
			for(int j =-1; j <= 1; j++){
				if(!outOfBoard(x+i, y+j) && board[x+i][y+j] == 9)
					pMine++;
			}
		}
		return pMine;
	}
	
	/**
	 * ����Χ��δ������λ�ø�������Ϊp
	 * @param x
	 * @param y
	 * @param p
	 */
	private void aroundMineProb(int x, int y, float p){
		for(int i =-1; i <= 1; i++){
			for(int j =-1; j <= 1; j++){
				if(!outOfBoard(x+i, y+j) && board[x+i][y+j] == -1 
						&& Float.compare(p, mine[x+i][y+j]) > 0)
					mine[x+i][y+j] =p;
			}
		}
	}
	
	/**
	 * ����Χ��δ������λ�ø�������Ϊp
	 * @param x
	 * @param y
	 * @param p
	 */
	private void aroundNoneProb(int x, int y, float p){
		for(int i =-1; i <= 1; i++){
			for(int j =-1; j <= 1; j++){
				if(!outOfBoard(x+i, y+j) && board[x+i][y+j] == -1 
						&& Float.compare(p, none[x+i][y+j]) < 0)
					none[x+i][y+j] =p;
			}
		}
	}
	
	/**
	 * ���������ʺ���С����
	 * @param x
	 * @param y
	 */
	private void computeProb(){
		int number;          //��ǰλ�õ�����
		int space =0;        //��ǰλ����Χ�Ŀո���
		int markMine =0;     //��ǰλ����Χ�Ѿ����׵�����
		float p =0;          //����
		
		for(int i =0; i < xLength; i++){
			for(int j =0; j <yLength; j++){
				//ȡ����ǰλ�õ�����
				number =board[i][j];
				//�����ǰλ�������֣�1~8��
				if(number > 0 && number < 9){
					//������Χ��δ�����Ŀո�����
					space =aroundSpace(i, j);
					//������Χ�Ѿ����׵Ŀո�����
					markMine =aroundMine(i, j);
					//��ǰ���� =����ǰ���� - ��Χ�Ѿ���������/��Χδ�����Ŀո���
					p =(float)(number - markMine)/space;
					//float���ͷ�ֹ����-0.0 < 0.0�����
					p =p +0.0f;
					//������Χδ����λ�õ�������׵ĸ���
					//����˸��ʴ���ԭ���ʣ�������Ϊ�˸���
					aroundMineProb(i, j, p);
					//������Χδ����λ�õ���С���׸���
					//����˸���С��ԭ���ʣ���С����Ϊԭ����
					aroundNoneProb(i, j, p);
				}
			}
		}
	}
	
	/**
	 * ���ף������и���Ϊ1��λ���ҳ���
	 */
	private void markMine(){
		Point point;
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				//����˴����׵ĸ���Ϊ1������û�б�����
				if(isEqualFloat(mine[i][j], 1f) && board[i][j] != 9){
					//ȡ�������뵽һ�����׵�list��
					point =new Point(i, j);
					mines.add(point);
				}
			}
		}
	}
	
	/**
	 * ���Ҹ�����͵Ŀո�
	 */
	private void lowestNoneProb(){
		Point point;
		float lessProb =1;               //��͸���
		float p =1;                      //��ǰ���� 
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				//����˴��Ѿ�������������
				if(board[i][j] != -1)
					continue;
				//����˴����׵ĸ���Ϊ0��
				//��ǰ���� =0
				if(isEqualFloat(0f, none[i][j])){
					p =0;
				}
				//����˴����׵ĸ��ʲ�Ϊ0
				//��ǰ���� =��͸��� + ��߸���
				else{
					p =none[i][j] +mine[i][j];
				}
				//�����ǰ���ʴ�����͸��ʣ�����
				if(Float.compare(lessProb, p) < 0)
					continue;
				//�����ǰ����С����͸��ʣ�
				if(Float.compare(lessProb, p) > 0){
					//����͸�����Ϊ��ǰ����
					lessProb =p;
					//���ԭ�е���͸���list
					nones.clear();
				}
				//����ǰλ�ü��뵽���׶�����
				point =new Point(i, j);
				nones.add(point);
			}
		}
	}
	
	public void clearList(){
		mines.clear();
		nones.clear();
	}
	
	/**
	 * ��ȡ������
	 * @return
	 */
	public Point getResult(){
		Point result =null;
		int loc;
		markMine();
		if(mines.size() > 0){
			loc =(int)(Math.random()*mines.size());
			result =mines.get(loc);
			result.type =1;
			return result;
		}
		lowestNoneProb();
		if(nones.size() > 0){
			loc =(int)(Math.random()*nones.size());
			result =nones.get(loc);
			result.type =2;
			return result;
		}
		return result;
	}
	
	public int getBoard(int x, int y){
		return board[x][y];
	}
	public void setBoard(int x, int y, int value){
		board[x][y] =value;
	}
	
	public float getMineProb(int x, int y){
		return mine[x][y];
	}
	public void setMineProb(int x, int y, float value){
		mine[x][y] =value;
	}
	
	public float getNoneProb(int x, int y){
		return none[x][y];
	}
	public void setNoneProb(int x, int y, int value){
		none[x][y] =value;
	}
	
	/**
	 * �ж�ָ��λ���Ƿ񳬳���Ϸ����
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean outOfBoard(int x, int y){
		if(x < 0 || y < 0 || x >= xLength || y >= yLength)
			return true;
		return false;
	}
	
	private boolean isEqualFloat(Float a, Float b){
//		return a.equals(b);
		if(Math.abs(a - b) < 0.000001)
			return true;
		return false;
	}

}
