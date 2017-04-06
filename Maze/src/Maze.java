import java.awt.Point;
import java.util.Stack;

class Maze{
	
	//�����Թ��������Գ���
	private static final int NONE =0;              //û����
	private static final int UP =1;                //�Ϸ�����
	private static final int RIGHT =4;             //�ұ�����
	private static final int DOWN =9;              //�·�����
	private static final int LEFT =16;             //�������
	private static final int UP_RIGHT =5;          //�Ϻ�������
	private static final int UP_DOWN =10;          //�Ϻ�������
	private static final int UP_LEFT =17;          //�Ϻ�������
	private static final int RIGHT_DOWN =13;       //�Һ�������
	private static final int RIGHT_LEFT =20;       //�Һ�������
	private static final int DOWN_LEFT =25;        //�º�������
	private static final int NO_UP =29;            //����������
	private static final int NO_RIGHT =26;         //����������
	private static final int NO_DOWN =21;          //����������
	private static final int NO_LEFT =14;          //�����¶�����
	private static final int ALL =30;              //�ĸ���������
	
	//�����־λ
	private static final int NOT =0;            //δ����
	private static final int DONE =1;           //�ѱ���
	
	private static final int LENGHT =9;           //�Թ�����
	
	private int[][] maze;         //�Թ�����
	private int[][] mark;         //��־����
	
	Stack<Point> bifurcateStack;
	
	public Maze(){
		bifurcateStack =new Stack<Point>();
		init();
		ganerateMaze();
		modifyMaze();
		printMaze();
	}
	/**
	 * ��ʼ������
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
	 * �����Թ�
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
			//������һ��δ��־�ķ���
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
			//����ߵ���ͷȡ����һ���ֲ��
			if(direction == NONE){
//				System.out.println("stack size is " + bifurcateStack.size());
				//����ֲ�ջΪ�գ��˳�ѭ��
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
				//ȡ���ֲ��
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
			//�жϵõ��Ĳ����Ƿ��뵱ǰ�������ͻ
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
			//�Ը��ڷ��䲼�ָ���
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
	 * ���������Թ�����ͨ��������ȥ��
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
		//��㲻�ܱ�
		maze[(LENGHT -1)/2][LENGHT -1] =ALL;
	}
	/**
	 * ��ӡ�Թ�
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
					System.out.print("��");
					break;
				case RIGHT:
					System.out.print("��");
					break;
				case DOWN:
					System.out.print("��");
					break;
				case LEFT:
					System.out.print("��");
					break;
				case UP_RIGHT:
					System.out.print("��");
					break;
				case UP_DOWN:
					System.out.print("��");
					break;
				case UP_LEFT:
					System.out.print("��");
					break;
				case RIGHT_DOWN:
					System.out.print("��");
					break;
				case RIGHT_LEFT:
					System.out.print("��");
					break;
				case DOWN_LEFT:
					System.out.print("��");
					break;
				case NO_UP:
					System.out.print("��");
					break;
				case NO_RIGHT:
					System.out.print("��");
					break;
				case NO_DOWN:
					System.out.print("��");
					break;
				case NO_LEFT:
					System.out.print("��");
					break;
				case ALL:
					System.out.print("��");
					break;
				default:				
				}
			}
			System.out.println(" ");
		}
	}
	/**
	 * �ж�ָ��λ���Ƿ����Թ�֮��
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
	 * �ж��Ƿ����
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
	 * �õ�������һ�η���ķ���
	 * @param mark   ��ǰ����ı�־
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
	 * ��������ѱ������ķ��䣬������
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
	 * �ж��Ƿ����зֲ�ķ���
	 * @param mark
	 * @return
	 */
	private boolean isBifurcate(int mark){
		if(mark == UP || mark == DOWN || mark == RIGHT || mark == LEFT || mark == NONE)
			return false;
		return true;
	}
	/**
	 * �ж��Ƿ����Ϸ�����
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
	 * �ж��Ƿ����ҷ�����
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
	 * �ж��Ƿ����·�����
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
	 * �ж��Ƿ����󷽵���
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
	 * ������һ������
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
	 *        ������
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println("start...");
		new Maze();
	}
}