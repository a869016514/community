package community.test;

import java.util.Arrays;
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class Test {
 
	/**
	 * 输入一个int型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。
	 * */
	public    void q1  (String[] args) {
		Scanner s=new Scanner(System.in);
		int a=s.nextInt();
		int [] arr=new int[10];
		StringBuilder sb=new StringBuilder();
		int t=0;
		while(a!=0) {
			t=a%10; //--获得最后一个数
			a=a/10; //去掉最后一个数
			if(arr[t]!=1) {
				sb.append(t);
				arr[t]=1;
			}
		}
		System.out.println(sb.toString());
	}

	
	
 
	/*
	 * 有n个学生站成一排，每个学生有一个能力值，牛牛想从这n个学生中按照顺序选取k名学生，要求相邻两个学生的位置编号的差不超过d，
	 * 使得这k个学生的能力值的乘积最大，你能返回最大的乘积吗？
	 * 
	 * 
	 * 输入描述: 每个输入包含1个测试用例。每个测试数据的第一行包含一个整数n (1 <= n <=
	 * 50)，表示学生的个数，接下来的一行，包含n个整数，按顺序表示每个学生的能力值ai（-50 <= ai <= 50）。接下来的一行包含两个整数，k和d
	 * (1 <= k <= 10, 1 <= d <= 50)。
	 * 
	 * 
	 * 输出描述: 输出一行表示最大的乘积
	 */
		 
	public void  q2  (String[] args) {
		
		Scanner s=new Scanner(System.in);
		int n=s.nextInt(); //学生个数
		int ai[]=new int[n];
		for(int i=0;i<n;i++) {
			ai[i]=s.nextInt();
		}
		int k=s.nextInt();       //要取几个
		int d=s.nextInt(); 		//相差位置
		int len=ai.length; 
		for(int q=0;q<ai.length;q++) {
			for(int p=q;p<len-1;p++) {
				if(ai[p+1]<ai[p]) {
					int temp=ai[p];
					ai[p]=ai[p+1];
					ai[p+1]=temp;
	 
				}
			} 
		}
		int sum=1;
		System.out.println(Arrays.toString(ai)+"-" +k +"-"+ai.length);
		while(k!=0) {
			int l=ai.length-k;
			sum=sum*ai[l]; 
			k--;
		}
		System.out.println(sum);
	 
	}
	
	/*
	 * 为了不断优化推荐效果，今日头条每天要存储和处理海量数据。假设有这样一种场景：我们对用户按照它们的注册时间先后来标号，对于一类文章，每个用户都有不同的喜好值
	 * ，我们会想知道某一段时间内注册的用户（标号相连的一批用户）中，有多少用户对这类文章喜好值为k。因为一些特殊的原因，
	 * 不会出现一个查询的用户区间完全覆盖另一个查询的用户区间(不存在L1<=L2<=R2<=R1)。
	 * 
	 * 输入描述: 输入： 第1行为n代表用户的个数 第2行为n个整数，第i个代表用户标号为i的用户对某类文章的喜好度 第3行为一个正整数q代表查询的组数
	 * 第4行到第（3+q）行，每行包含3个整数l,r,k代表一组查询，即标号为l<=i<=r的用户中对这类文章喜好值为k的用户的个数。 数据范围n <=
	 * 300000,q<=300000 k是整型
	 * 
	 * 输出描述: 输出：一共q行，每行一个整数代表喜好值为k的用户的个数
	 * 
	 * 输入例子1: 
	 * 	  5 
	 * 	  1 2 3 3 5
	 *    3
	 *    1 2 1
	 *    2 4 5 
	 *    3 5 3
	 * 
	 * 输出例子1: 1 0 2
	 * 
	 * 例子说明1: 样例解释: 有5个用户，喜好值为分别为1、2、3、3、5， 第一组询问对于标号[1,2]的用户喜好值为1的用户的个数是1
	 * 第二组询问对于标号[2,4]的用户喜好值为5的用户的个数是0 第三组询问对于标号[3,5]的用户喜好值为3的用户的个数是2
	 */
	
	public static void main(String[] args) {
		
	}
	
}
