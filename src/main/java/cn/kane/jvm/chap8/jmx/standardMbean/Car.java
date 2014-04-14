package cn.kane.jvm.chap8.jmx.standardMbean;

public class Car implements CarMBean {

	private String color ;
	
	public void setColor(String color) {
		System.out.println("COLOR-SETTER");
		this.color = color ;
	}

	public String getColor() {
		System.out.println("COLOR-GETTER");
		return this.color ;
	}

	public void drive() {
		System.out.println("DRIVE-CAR");
	}

}
