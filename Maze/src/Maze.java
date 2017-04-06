import java.awt.Point;
import java.util.Stack;

class Maze{
	
	//定义迷宫房间属性常量
	private static final int NONE =0;              //没有门
	private static final int UP =1;                //上方有门
	private static final int RIGHT =4;             //右边有门
	private static final int DOWN =9;              //下方有门
	private static final int LEFT =16;             //左边有门
	private static final int UP_RIGHT =5;          //上和右有门
	private static final int UP_DOWN =10;          //上和下有门
	private static final int UP_LEFT =17;          //上和左有门
	private static final int RIGHT_DOWN =13;       //右和下有门
	private static final int RIGHT_LEFT =20;       //右和左有门
	private static final int DOWN_LEFT =25;        //下和左有门
	private static final int NO_UP =29;            //右下左都有门
	private static final int NO_RIGHT =26;         //上下左都有门
	private static final int NO_DOWN =21;          //上右左都有门
	private static final int NO_LEFT =14;          //上右下都有门
	private static final int ALL =30;              //四个方向都有门
	
	//定义标志位
	private static final int NOT =0;            //未遍历
	private static final int DONE =1;           //已遍历
	
	private static final int LENGHT =9;           //迷宫长度
	
	private int[][] maze;         //迷宫数组
	private int[][] mark;         //标志数组
	
	Stack<Point> bifurcateStack;
	
	public Maze(){
		bifurcateStack =new Stack<Point>();
		init();
		ganerateMaze();
		modifyMaze();
		printMaze();
	}
	/**
	 * 初始化数组
	 */
	private void init(){
		System.out.println("initializing...");
		maze =new int[LENGHT][LENGHT];
		mark =new int[LENGHT][LENGHT];
		
		for(int i = 0;i < LENGHT;i ++){
			for(int j =0;j < LENGHT;j ++){
				maze[i][j] =NONE;
				mark[i][j] =NOT;
			}
		}
	}
	/**
	 * 生成迷宫
	 */
	private void ganerateMaze(){
		System.out.println("ganerating...");
		int sx =(LENGHT - 1)/2;
		int sy =LENGHT -1;
		int count =0;
		int num;
		int direction;
		int oldLayout;
		boolean choose =false;
		Point bifurcate =new Point(sx,sy);
		
		maze[sx][sy] =ALL;
		maze[sx - 1][sy] =RIGHT;
		maze[sx][sy - 1] =DOWN;
		maze[sx + 1][sy] =LEFT;
		mark[sx][sy] =DONE;
		bifurcateStack.push(bifurcate);
		
		while(true){
			direction =getDirection(maze[sx][sy]);
			//进入下一个未标志的房间
			while(true){
				if(direction == NONE)
					break;
				else if(direction == UP && !out(sx,sy - 1)){
					if(mark[sx][sy - 1] == NOT){
						sy --;
						break;
					}
					else{
						direction =nextDirection(direction,maze[sx][sy]);
						continue;
					}
				}
				else if(direction == RIGHT && !out(sx + 1,sy)){
					if(mark[sx + 1][sy] == NOT){
						sx ++;
						break;
					}
					else{
						direction =nextDirection(direction,maze[sx][sy]);
						continue;
					}
				}
				else if(direction == DOWN && !out(sx,sy + 1)){
					if(mark[sx][sy + 1] == NOT){
						sy ++;
						break;
					}
					else{
						direction =nextDirection(direction,maze[sx][sy]);
						continue;
					}
				}
				else if(direction == LEFT && !out(sx - 1,sy)){
					if(mark[sx - 1][sy] == NOT){
						sx --;
						break;
					}
					else{
						direction =nextDirection(direction,maze[sx][sy]);
						continue;
					}
				}
				else{
					direction =nextDirection(direction,maze[sx][sy]);
					continue;
				}
			}
			//如果走到尽头取出上一个分叉点
			if(direction == NONE){
//				System.out.println("stack size is " + bifurcateStack.size());
				//如果分叉栈为空，退出循环
				if(bifurcateStack.empty()){
					if(isFinish())
						break;
					else{
//						System.out.println("researching...");
						choose=false;
						for(int i = 0;i < LENGHT;i ++){
							if(choose)
								break;
							for(int j =0;j < LENGHT;j ++){
								if(maze[i][j] == NONE){
									sx =i;
									sy =j;
									choose =true;
									break;
								}
							}
						}
					}
				}
				//取出分叉点
				else{
					bifurcate =bifurcateStack.pop();
					sx =bifurcate.x;
					sy =bifurcate.y;
					continue;
				}
			}
			num =getNum();
			if(isBifurcate(maze[sx][sy])){
//				System.out.println("push in stack!");
				bifurcate.x =sx;
				bifurcate.y =sy;
				bifurcateStack.push(bifurcate);
			}
			oldLayout =maze[sx][sy];
			//判断得到的布局是否与当前房间起冲突
			switch(oldLayout){
			case UP:
				if(isUp(num))
					maze[sx][sy] =num;
				else
					maze[sx][sy] +=num;
				break;
			case RIGHT:
				if(isRight(num))
					maze[sx][sy] =num;
				else
					maze[sx][sy] +=num;
				break;
			case DOWN:
				if(isDown(num))
					maze[sx][sy] =num;
				else
					maze[sx][sy] +=num;
				break;
			case LEFT:
				if(isLeft(num))
					maze[sx][sy] =num;
				else
					maze[sx][sy] +=num;
				break;
			case NONE:
				maze[sx][sy] =num;
				break;
			}
			//对隔壁房间布局更新
			if(isUp(maze[sx][sy]) && !out(sx,sy - 1)){
				if(oldLayout != UP && !isDown(maze[sx][sy - 1]))
					maze[sx][sy - 1] += DOWN;
			}
			if(isRight(maze[sx][sy]) && !out(sx + 1,sy)){
				if(oldLayout != RIGHT && !isLeft(maze[sx + 1][sy]))
					maze[sx + 1][sy] += LEFT;
			}
			if(isDown(maze[sx][sy]) && !out(sx,sy + 1)){
				if(oldLayout != DOWN && !isUp(maze[sx][sy + 1]))
					maze[sx][sy + 1] += UP;
			}
			if(isLeft(maze[sx][sy]) && !out(sx - 1,sy)){
				if(oldLayout != LEFT && !isRight(maze[sx - 1][sy]))
					maze[sx - 1][sy] += RIGHT;
			}
			mark[sx][sy] =DONE;
//			count ++;
//			if(count > 500)
//				break;
		}
//		System.out.println("count =" + count);
	}
	/**
	 * 重新修饰迷宫，把通向外界的门去掉
	 */
	private void modifyMaze(){
		for(int i = 0;i < LENGHT;i ++){
			if(isLeft(maze[0][i]) && maze[0][i] !=LEFT)
				maze[0][i] -=LEFT;
			if(isRight(maze[LENGHT - 1][i]) && maze[LENGHT - 1][i] !=RIGHT)
				maze[LENGHT - 1][i] -=RIGHT;
			if(isUp(maze[i][0]) && maze[i][0] !=UP)
				maze[i][0] -=UP;
			if(isDown(maze[i][LENGHT - 1]) && maze[i][LENGHT - 1] !=DOWN)
				maze[i][LENGHT - 1] -=DOWN;
		}
		//起点不能变
		maze[(LENGHT -1)/2][LENGHT -1] =ALL;
	}
	/**
	 * 打印迷宫
	 */
	private void printMaze(){
		System.out.println("printing...");
		for(int i = 0;i < LENGHT;i ++){
			for(int j = 0;j < LENGHT;j ++){
				switch(maze[j][i]){
				case NONE:
					System.out.print(" ");
					break;
				case UP:
					System.out.print("┃");
					break;
				case RIGHT:
					System.out.print("━");
					break;
				case DOWN:
					System.out.print("┃");
					break;
				case LEFT:
					System.out.print("━");
					break;
				case UP_RIGHT:
					System.out.print("┗");
					break;
				case UP_DOWN:
					System.out.print("┃");
					break;
				case UP_LEFT:
					System.out.print("┛");
					break;
				case RIGHT_DOWN:
					System.out.print("┏");
					break;
				case RIGHT_LEFT:
					System.out.print("━");
					break;
				case DOWN_LEFT:
					System.out.print("┓");
					break;
				case NO_UP:
					System.out.print("┳");
					break;
				case NO_RIGHT:
					System.out.print("┫");
					break;
				case NO_DOWN:
					System.out.print("┻");
					break;
				case NO_LEFT:
					System.out.print("┣");
					break;
				case ALL:
					System.out.print("╋");
					break;
				default:				
				}
			}
			System.out.println(" ");
		}
	}
	/**
	 * 判断指定位置是否在迷宫之外
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean out(int x,int y){
		if(x < 0 || y < 0 || x >= LENGHT || y >= LENGHT)
			return true;
		return false;
	}
	/**
	 * 判断是否结束
	 * @return
	 */
	private boolean isFinish(){
		for(int i = 0;i < LENGHT;i ++){
			for(int j =0;j < LENGHT;j ++){
				if(maze[i][j] == NONE)
					return false;
			}
		}
		return true;
	}
	/**
	 * 得到进入下一次房间的方向
	 * @param mark   当前房间的标志
	 * @return
	 */
	private int getDirection(int mark){
		switch(mark){
		case UP:
		case UP_RIGHT:
		case UP_DOWN:
		case UP_LEFT:
		case NO_RIGHT:
		case NO_DOWN:
		case NO_LEFT:
		case ALL:
			return UP;
		case RIGHT:
		case RIGHT_DOWN:
		case RIGHT_LEFT:
		case NO_UP:
			return RIGHT;
		case DOWN:
		case DOWN_LEFT:
			return DOWN;
		case LEFT:
			return LEFT;
		default:
			return NONE;
		}
	}
	/**
	 * 如果遇到已遍历过的房间，换方向
	 * @param mark
	 * @return
	 */
	private int nextDirection(int direction,int mark){
		switch(direction){
		case UP:
			if(isRight(mark))
				return RIGHT;
			else if(isDown(mark))
				return DOWN;
			else if(isLeft(mark))
				return LEFT;
			else
				return NONE;
		case RIGHT:
			if(isDown(mark))
				return DOWN;
			else if(isLeft(mark))
				return LEFT;
			else
				return NONE;
		case DOWN:
			if(isLeft(mark))
				return LEFT;
			else
				return NONE;
		case LEFT:
			return NONE;
		default:
			return NONE;
		}
	}
	/**
	 * 判断是否是有分叉的房间
	 * @param mark
	 * @return
	 */
	private boolean isBifurcate(int mark){
		if(mark == UP || mark == DOWN || mark == RIGHT || mark == LEFT || mark == NONE)
			return false;
		return true;
	}
	/**
	 * 判断是否有上方的门
	 * @param mark
	 * @return
	 */
	private boolean isUp(int mark){
		switch(mark){
		case UP:
		case UP_RIGHT:
		case UP_DOWN:
		case UP_LEFT:
		case NO_RIGHT:
		case NO_DOWN:
		case NO_LEFT:
		case ALL:
			return true;
		default:
			return false;
		}
	}
	/**
	 * 判断是否有右方的门
	 * @param mark
	 * @return
	 */
	private boolean isRight(int mark){
		switch(mark){
		case RIGHT:
		case UP_RIGHT:
		case RIGHT_DOWN:
		case RIGHT_LEFT:
		case NO_UP:
		case NO_DOWN:
		case NO_LEFT:
		case ALL:
			return true;
		default:
			return false;
		}
	}
	/**
	 * 判断是否有下方的门
	 * @param mark
	 * @return
	 */
	private boolean isDown(int mark){
		switch(mark){
		case DOWN:
		case UP_DOWN:
		case RIGHT_DOWN:
		case DOWN_LEFT:
		case NO_UP:
		case NO_RIGHT:
		case NO_LEFT:
		case ALL:
			return true;
		default:
			return false;
		}
	}
	/**
	 * 判断是否有左方的门
	 * @param mark
	 * @return
	 */
	private boolean isLeft(int mark){
		switch(mark){
		case LEFT:
		case UP_LEFT:
		case RIGHT_LEFT:
		case DOWN_LEFT:
		case NO_UP:
		case NO_RIGHT:
		case NO_DOWN:
		case ALL:
			return true;
		default:
			return false;
		}
	}
	/**
	 * 随机获得一个数字
	 * @return  
	 */
	private int getNum(){
		int num = (int)(Math.random()*14);
		switch(num){
		case 0:
			return UP;
		case 1:
			return RIGHT;
		case 2:
			return DOWN;
		case 3:
			return LEFT;
		case 4:
			return UP_RIGHT;
		case 5:
			return UP_DOWN;
		case 6:
			return UP_LEFT;
		case 7:
			return RIGHT_DOWN;
		case 8:
			return RIGHT_LEFT;
		case 9:
			return DOWN_LEFT;
		case 10:
			return NO_UP;
		case 11:
			return NO_RIGHT;
		case 12:
			return NO_DOWN;
		case 13:
			return NO_LEFT;
		default:
			return NONE;
		}
	}
	/**
	 *        主函数
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println("start...");
		new Maze();
	}
}