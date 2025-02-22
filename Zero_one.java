package Algorithm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Zero_one {
	public int packageweight;//背包的总容量
	public int productnum;//物品总数
	public ArrayList<Integer> weights;//每个物品的重量
	public ArrayList<Integer> values;//每个物品的价值
	
	public static void main(String[] args) throws Exception {
		Zero_one zero_one = new Zero_one();
		while(true){
			zero_one.readdata();// 读取测试文件中数据
			int[][] m = zero_one.initpkdata();//
			int[][] res = zero_one.result(m);
			System.out.print("背包总容量:");
			System.out.println(zero_one.packageweight);
			System.out.println("物品总数："+zero_one.productnum);
			System.out.println("物品重量及价值对应关系：");
			for(int i = 0; i < zero_one.weights.size(); i++){
				System.out.print(zero_one.weights.get(i)+" ");
			}
			System.out.println();
			for(int i = 0; i < zero_one.values.size(); i++){
				System.out.print(zero_one.values.get(i)+" ");
			}
			System.out.println();
			System.out.println("           ");
			System.out.println("求解过程:");
			for (int i = 0; i < res.length; i++) {
				for (int j = 0; j < res[i].length; j++) {
					System.out.print(res[i][j]+"    ");
				}
				System.out.println();
			}
			System.out.println("           ");
			System.out.println("此时背包中最大价值总和为："+res[zero_one.productnum][zero_one.packageweight]);
			System.out.print("装入背包中物品序号为：");
			zero_one.findproducts(res);
			System.out.println();
			System.out.println("           ");
		}
	}
	 
	public void readdata() throws Exception{//读取测试数据
		System.out.println("请输入数据编号（1~5）：");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		BufferedReader br = new BufferedReader(new FileReader("D:/Program Files (x86)/MyStudy/src/Zero_one_Package/input/input_assign02_0" + num + ".dat"));
		String string = br.readLine();
		this.packageweight = Integer.parseInt(string.split(" ")[0]);
		this.productnum = Integer.parseInt(string.split(" ")[1]);
		String weight = br.readLine();
		this.weights = new ArrayList <Integer>();
		for (int i = 0; i < this.productnum; i++) {
			this.weights.add(Integer.parseInt(weight.split(" ")[i]));
		}
		
		String value = br.readLine();
		this.values = new ArrayList <Integer>();
		for (int i = 0; i < this.productnum; i++) {
			this.values.add(Integer.parseInt(value.split(" ")[i]));
		}
	}
	
	/**
	 * 初始化背包
	 * m[i][0] = 0 :表示背包重量为0，不能装东西，因此价值全为0
	 * m[0][j] = 0 :表示没有可以装的物品，因此价值为0
	 */
	public int[][] initpkdata(){
		int[][] m = new int[this.productnum+1][this.packageweight+1];
		for(int i = 0; i <= this.productnum; i++){
			m[i][0] = 0;
		}
		for(int j = 0; j <= this.packageweight; j++){
			m[0][j] = 0;
		}
		return m;
	}
	
	public int[][] result(int[][] arr){
		for(int i = 1; i <= this.productnum; i++){
			for(int j = 1; j <= this.packageweight; j++){
				// 当第i件物品重量大于当前包的容量 则放不进去
				// 所以当前背包所含价值等于前i-1件商品的价值
				if(this.weights.get(i-1) > j){
					arr[i][j] = arr[i-1][j];
				}
				/*当第i件物品能放进去时
				1 放入物品，价值为：arr[i-1][j-(int)this.weights.get(i-1)] + (int)this.values.get(i-1)
				2不放入物品，价值为前i-1件物品价值和：arr[i][j] = arr[i-1][j];
				此时最大价值为上述两种方案中最大的一个
				*/
				else{
					if(arr[i-1][j] < arr[i-1][j-this.weights.get(i-1)] + this.values.get(i-1)){
						arr[i][j] = arr[i-1][j-this.weights.get(i-1)] + this.values.get(i-1);
					}
					else{
						arr[i][j] = arr[i-1][j];
					}
				}
			}
		}
		return arr;
	}
	
	public void findproducts(int[][] arr){
		int j = this.packageweight;  
		for(int i = this.productnum; i > 0; i--){
		    if(arr[i][j] > arr[i-1][j]){
		        System.out.print(i+"  ");//输出选中的物品的编号
		        j = j - this.weights.get(i-1);
		        if(j < 0){
		        	break;
		        }
		    }
		}
	}
}