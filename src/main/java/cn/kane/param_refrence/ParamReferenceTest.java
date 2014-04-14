package cn.kane.param_refrence;

import junit.framework.TestCase;

public class ParamReferenceTest extends TestCase {

	private int intParam = 234 ;
	private Integer intObj = new Integer(555) ;
	private String strParam = "initStr" ;
	private Person person = new Person("kidds",5);
	
	private char[] charArrayParam = new char[]{'i','n','i','t'};
	
	public void changeIntParam(int intParam){
		System.out.println(this.intParam);
		intParam = 333 ;//intParam is temp-param in method[changeIntParam];this.intParam is field in instance
		System.out.println(this.intParam);
	}
	
	public void changeIntegerParam(Integer intObj){
		System.out.println(this.intObj);
		intObj = new Integer(333);
		System.out.println(this.intObj);
	}
	
	public void changeStrParam(String strParam){
		System.out.println(this.strParam);
		strParam = "changedStr";
		System.out.println(this.strParam);// not change,string is final,if str[source] >>> str[target] ,str 's memory address will be change
	}
	
	public void changeCharArrayParam(char[] charArrayParam){
		System.out.println(this.charArrayParam);
		charArrayParam[0] = 'c' ;
		System.out.println(this.charArrayParam);
		charArrayParam = new char[]{'c','h','a','n'}; //temp-param point to new memory-address ,cut off point with the filed charArrayParam
		System.out.println(this.charArrayParam);
	}
	
	public void changeObjectParam(Person person){
		System.out.println(this.person);
		person.setName("new-name");
		person.setAge(10);
		System.out.println(this.person);
	}
	public void testParamReference(){
		changeIntParam(intParam);
		changeIntegerParam(intObj);
		changeStrParam(strParam);
		changeCharArrayParam(charArrayParam);
		changeObjectParam(person);
	}
}
class Person{
	private String name = null ;
	private int age = 0 ;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public Person(){
		
	}
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	
	@Override
	public String toString(){
		return "Person["+name+","+age+"]";
	}
}
