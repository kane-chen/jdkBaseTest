package cn.kane;

public class VariableParamsTest {

	//variableParams must at the end of the params
	public static int variableParams(int initParam,int ... intArrays){
		int minVar = initParam ;
		for(int var : intArrays){
			if(var < minVar){
				minVar = var ;
			}
		}
		return minVar ;
	}
	
	public static void main(String[] args){
		int minVar = variableParams(999,new int[]{1,2,-3,5});
		System.out.println(minVar);
	}
}
