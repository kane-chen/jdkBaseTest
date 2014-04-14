package cn.kane.jvm.chap8.jmx;

public class Car {

	private String color ;

	public String getColor() {
		System.out.println("GETCOLOR");
		return color;
	}
	public void setColor(String color) {
		System.out.println("SETCOLOR");
		this.color = color;
	}
	public void start(){
		System.out.println("CAR-RUNNING");
	}
	
}