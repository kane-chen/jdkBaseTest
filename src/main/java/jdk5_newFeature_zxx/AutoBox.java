package jdk5_newFeature_zxx;

public class AutoBox {

	public static void main(String[] args) {

		Integer iObj = 3;
		System.out.println(iObj + 12);
		
		String s1 = new String("abc");
		String s2 = new String("abc");	
		
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));
		
		Integer i1 = 137;
		Integer i2 = 137;
		

		System.out.println(i1 == i2);
		
		Integer i3 = Integer.valueOf(213);
		Integer i4 = Integer.valueOf(213);
		System.out.println(i3==i4);
		
	}

}
