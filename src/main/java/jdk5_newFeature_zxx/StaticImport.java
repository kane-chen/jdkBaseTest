package jdk5_newFeature_zxx;

//import static java.lang.Math.max;
import static java.lang.Math.*;

public class StaticImport {

	public static void main(String[] args){
		
		//AnnotationTest.sayHello();
		int x = 1;
		try {
			x++;
		} finally {
			System.out.println("template");
		}
		System.out.println(x);
		
		
		System.out.println(max(3, 6));//Point:  max(3,6) >>> java.lang.Math.max(3,6) ::: max() is static-method
		System.out.println(abs(3 - 6));
		
	}
}
