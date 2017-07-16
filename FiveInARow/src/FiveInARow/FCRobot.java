package FiveInARow;

import java.util.ArrayList;
import java.util.List;

public class FCRobot {

	private final int lenght=15;         //棋盘宽度
	
	private final int BLEND=5;         //混合的情况
	private final int NONE=0;            //没有棋子
	private final int MINE=200;          //己方一个子
	private final int DOUBLEMINE=1000;            //己方两个子
	private final int TRIPLEMINE=10000;             //己方三个子
	private final int QUADRUMINE=4000000;           //己方四个子
	private final int ENEMY=100;                  //敌方一个子
	private final int DOUBLEENEMY=400;             //敌方两个子
	private final int TRIPLEENEMY=6000;            //敌方三个子
	private final int QUADRUENEMY=500000;           //敌方四个子
	
	private int x=7,y=7;
	private int chesscolor=0;              //己方颜色
	private int lastcolor=0;               //上一步颜色，便于悔棋
	private int rboard[][]=new int[lenght][lenght];           //用于存放棋盘的内容
	private int score[][]=new int[lenght][lenght];             //用于存放棋盘位置的分值
	
	List<Bound> bounds =new ArrayList<Bound>();            //用于存放相同分值的坐标集合
	
	public FCRobot(){
		
	}
	/**
	 * 初始化棋盘
	 * */
	public void init(){
		for(int i=0;i<lenght;i++){
			for(int j=0;j<lenght;j++)
				rboard[i][j]=0;             //最开始棋盘上都没有子
		}
		for(int i=0;i<lenght;i++){
			for(int j=0;j<lenght;j++)
				score[i][j]=10;             //最开始所有位置的优先级为10
		}
	}
	/**
	 *计算分值 
	 */
	private int getScore(int amount1,int amount2){
		
		int score=0;
			if(amount2==0)
				score=NONE;             //没有棋子
			else if(amount2==amount1){    //都是己方棋子
				if(amount2==1)
					score=MINE;
				if(amount2==2)
					score=DOUBLEMINE;
				if(amount2==3)
					score=TRIPLEMINE;
				if(amount2==4)
					score=QUADRUMINE;
			}
			else if(amount2==-amount1){     //都是敌方棋子
				if(amount2==1)
					score=ENEMY;
				if(amount2==2)
					score=DOUBLEENEMY;
				if(amount2==3)
					score=TRIPLEENEMY;
				if(amount2==4)
					score=QUADRUENEMY;
			}
			else                          //双方棋子都有
				score=BLEND;
			return score;
	}
	/**
	 * 对棋盘上的位置进行评估
	 * */
	private void valuation(int x,int y){
		
		int sx=x;
		int sy=y;
		int formerscore=0;
		int newscore=0;
		int amount1=0;
		int amount2=0;
		int color=rboard[x][y];            //存放原有的棋子
		
		for(int i=4;i>0;i--){
			//新的分数
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy];            //五元组的棋子分值的和
				if(rboard[sx-i+j][sy]!=0)
					amount2++;             //记住棋子的数量
			}
			newscore=getScore(amount1,amount2);
			//旧的分数
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy];            //五元组的棋子分和
				if(rboard[sx-i+j][sy]!=0)
					amount2++;             //记住棋子的数量
			}
			formerscore=getScore(amount1,amount2);
			//对五元组赋值
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
				amount1=amount1+rboard[sx-i+j][sy-i+j];            //五元组的棋子分值的和
				if(rboard[sx-i+j][sy-i+j]!=0)
					amount2++;             //记住棋子的数量
			}
			newscore=getScore(amount1,amount2);
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sx-i<0||sx-i+j>=lenght||sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx-i+j][sy-i+j];            //五元组的棋子分和
				if(rboard[sx-i+j][sy-i+j]!=0)
					amount2++;             //记住棋子的数量
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
				amount1=amount1+rboard[sx][sy-i+j];            //五元组的棋子分值的和
				if(rboard[sx][sy-i+j]!=0)
					amount2++;             //记住棋子的数量
			}
			newscore=getScore(amount1,amount2);
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx][sy-i+j];            //五元组的棋子分和
				if(rboard[sx][sy-i+j]!=0)
					amount2++;             //记住棋子的数量
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
				amount1=amount1+rboard[sx+i-j][sy-i+j];            //五元组的棋子分值的和
				if(rboard[sx+i-j][sy-i+j]!=0)
					amount2++;             //记住棋子的数量
			}
			newscore=getScore(amount1,amount2);
			rboard[x][y]=lastcolor;
			amount1=0;
			amount2=0;
			for(int j=0;j<5;j++){
				if(sx+i>=lenght||sx+i-j<0||sy-i<0||sy-i+j>=lenght)
					break;
				amount1=amount1+rboard[sx+i-j][sy-i+j];            //五元组的棋子分和
				if(rboard[sx+i-j][sy-i+j]!=0)
					amount2++;             //记住棋子的数量
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
	 * 搜索棋盘的分值
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
	 * 查看棋盘
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
	 * 返回X值
	 * */
	public int getX(){
		return x;
	}
	/**
	 * 返回Y值
	 * */
	public int getY(){
		return y;
	}
	/**
	 * 设置颜色
	 * */
	public void setChessColor(int color){
		if(color==0||color==1)
			chesscolor=color;
	}
	/**
	 * 返回颜色
	 * */
	public int getColor(){
		return chesscolor;
	}
}
