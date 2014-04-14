package cn.kane.threadLocal;

public class EntityBean {

    protected String instanceVar = "instance" ;
	
	public ThreadLocal<String> threadLocalVar = new ThreadLocal<String>();
	
	public ThreadLocal<String> otherLocalVar = new ThreadLocal<String>();

	public String getInstanceVar() {
		return instanceVar;
	}

	public void setInstanceVar(String instanceVar) {
		this.instanceVar = instanceVar;
	}

	public String getThreadLocalVar() {
		return threadLocalVar.get();
	}

	public void setThreadLocalVar(String localVar) {
		this.threadLocalVar.set(localVar);
	}
	
	
}
