package jdk5_newFeature_zxx;

public class VarableParameter {

	public static void main(String[] args) {
		System.out.println(add(2,3));
		System.out.println(add(2,3,5));		
	}
	
	
	public static int add(int x,int... args){
		int sum = x;
/*		for(int i=0;i<args.length;i++){
			sum += args[i];
		}*/
		
		for(int arg : args){
			sum += arg;
		}
		return sum;
	}

}
