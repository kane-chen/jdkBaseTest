package cn.kane.annotation;

public enum NewEnum{
	TYPE1("TYPE1"),
	TYPE2("TYPE2"),
	TYPE3("TYPE3");
	
	private String value;
	NewEnum(String value){
		this.value = value ;
	}
	
	public String valueOf(){
		return this.value;
	}
}