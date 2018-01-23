package MineSweeping;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
	
	private int[][] board;             //用来存游戏界面的数组
									   //-1: 未翻开
									   //0: 已翻开，空地
									   //1~8: 数字
									   //9: 已标雷
	private float[][] mine;            //用来计算有雷的概率数组
	private float[][] none;            //用来计算没有雷的概率数组
	private int hard;                  //游戏难度
	private int xLength;               //x方向的界面长度
	private int yLength;               //y方向的界面长度
	
	private List<Point> mines;         //存放所有计算出来的雷的位置
	private List<Point> nones;         //当没有雷标记的时候，存放所有概率最低的空格位置
	
	
	public Algorithm(int hard){
		this.hard =hard;
		mines =new ArrayList<Point>();
		nones =new ArrayList<Point>();
		initBoard();
	}
	
	/**
	 * 初始化游戏界面
	 */
	private void initBoard(){
		float prob =0;               //出现雷的概率
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
	 * 游戏开始，初始化界面
	 * @param hard
	 */
	public void start(int hard){
		this.hard =hard;
		initBoard();
	}
	
	/**
	 * 计算地雷概率
	 */
	public void valuation(){
		int space =leftSpace();
		int leftMine =leftMine();
		//没有空地了说明结束了
		if(space == 0)
			return;
		float p =(float)leftMine/space;
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				if(board[i][j] == -1){
					mine[i][j] =p;
					none[i][j] =p;
//					//不是雷的地方设为基础概率
//					if(Float.compare(p, mine[i][j]) > 0){
//						mine[i][j] =p;
//					}
//					//不是非雷的地方设为基础概率
//					if(Float.compare(p, none[i][j]) < 0){
//						none[i][j] =p;
//					}
				}
			}
		}
		computeProb();
	}

	/**
	 * 计算地图上剩余的空格数
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
	 * 计算剩余的地雷数
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
	 * 指定位置周围空格数
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
	 * 指定位置周围已经标雷数
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
	 * 把周围的未翻开的位置概率设置为p
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
	 * 把周围的未翻开的位置概率设置为p
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
	 * 计算最大概率和最小概率
	 * @param x
	 * @param y
	 */
	private void computeProb(){
		int number;          //当前位置的数字
		int space =0;        //当前位置周围的空格数
		int markMine =0;     //当前位置周围已经标雷的数量
		float p =0;          //概率
		
		for(int i =0; i < xLength; i++){
			for(int j =0; j <yLength; j++){
				//取出当前位置的数字
				number =board[i][j];
				//如果当前位置有数字（1~8）
				if(number > 0 && number < 9){
					//查找周围还未翻开的空格数量
					space =aroundSpace(i, j);
					//查找周围已经标雷的空格数量
					markMine =aroundMine(i, j);
					//当前概率 =（当前数字 - 周围已经标雷数）/周围未翻开的空格数
					p =(float)(number - markMine)/space;
					//float类型防止出现-0.0 < 0.0的情况
					p =p +0.0f;
					//更新周围未翻开位置的最大是雷的概率
					//如果此概率大于原概率，最大概率为此概率
					aroundMineProb(i, j, p);
					//更新周围未翻开位置的最小是雷概率
					//如果此概率小于原概率，最小概率为原概率
					aroundNoneProb(i, j, p);
				}
			}
		}
	}
	
	/**
	 * 标雷，将所有概率为1的位置找出来
	 */
	private void markMine(){
		Point point;
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				//如果此处是雷的概率为1，并且没有被标雷
				if(isEqualFloat(mine[i][j], 1f) && board[i][j] != 9){
					//取出并加入到一定是雷的list中
					point =new Point(i, j);
					mines.add(point);
				}
			}
		}
	}
	
	/**
	 * 查找概率最低的空格
	 */
	private void lowestNoneProb(){
		Point point;
		float lessProb =1;               //最低概率
		float p =1;                      //当前概率 
		for(int i =0; i < xLength; i++){
			for(int j =0; j < yLength; j++){
				//如果此处已经被翻开，跳过
				if(board[i][j] != -1)
					continue;
				//如果此处是雷的概率为0，
				//当前概率 =0
				if(isEqualFloat(0f, none[i][j])){
					p =0;
				}
				//如果此处是雷的概率不为0
				//当前概率 =最低概率 + 最高概率
				else{
					p =none[i][j] +mine[i][j];
				}
				//如果当前概率大于最低概率，跳过
				if(Float.compare(lessProb, p) < 0)
					continue;
				//如果当前概率小于最低概率，
				if(Float.compare(lessProb, p) > 0){
					//将最低概率设为当前概率
					lessProb =p;
					//清空原有的最低概率list
					nones.clear();
				}
				//将当前位置加入到非雷队列中
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
	 * 获取计算结果
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
	 * 判断指定位置是否超出游戏界面
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
