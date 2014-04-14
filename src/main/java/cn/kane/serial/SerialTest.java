package cn.kane.serial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class SerialTest  extends TestCase{


	public void testStringSerial() throws IOException, ClassNotFoundException, ParseException{
		Object target = null ;

		File storedFile = new File("storedFile.txt");
//		if(storedFile.exists()){
//			storedFile.createNewFile();
//		}
		storedFile.deleteOnExit();
		storedFile.createNewFile();

		String targetStr = null ;
		targetStr = "hello-serial" ;
		target = targetStr ;
		
		NumberFormat numFormat = new DecimalFormat("0000.##") ;
		BigDecimal account = new BigDecimal(numFormat.format(12345.1234)) ;
		Map<String,String> mapParam = new HashMap<String,String>();
		mapParam.put("1", "t-mac");
		mapParam.put("2", "j-kidd");
		mapParam.put("3", "ai");
		List<BasicBean> beans = new ArrayList<BasicBean>();
		beans.add(new BasicBean(1,"mac"));
		beans.add(new BasicBean(2,"kidd"));
		beans.add(new BasicBean(3,"iven"));
		beans.add(new BasicBean(4,"web"));
		SerialClass serialClass = new SerialClass("serail1", account,mapParam,beans);
		target = serialClass ;
		
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storedFile)) ;
		out.writeObject(target);
		out.close();
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(storedFile));
		Object result = in.readObject();
		System.out.println(result);
	}
}

class SerialClass implements Serializable{
	
	private static final long serialVersionUID = -5401746195565452499L;
	
	private String className = null ;
	private BigDecimal account = null;
	private Date writeTime = null ;
	private static int version = 1 ;          //will not write static & transient parameter
	private transient Date readTime = null ;  //will not write static & transient parameter
	private Map<String,String> mapParam = null ;
	private List<BasicBean> basicBeans = null ;
	
	public SerialClass(String className,BigDecimal account,Map<String,String> mapParam){
		this.className = className ;
		this.account = account ;
		this.mapParam = mapParam ;
	}

	public SerialClass(String className,BigDecimal account,Map<String,String> mapParam,List<BasicBean> basicBeans){
		this.className = className ;
		this.account = account ;
		this.mapParam = mapParam ;
		this.basicBeans = basicBeans ;
	}

	public String getClassName() {
		return className;
	}

	public BigDecimal getAccount() {
		return account;
	}
	
	private void writeObject(ObjectOutputStream outStream) throws IOException{ //callback,must be private
		version = 2 ;
		writeTime = new Date();
		outStream.defaultWriteObject();
		System.out.println("serailizable"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(writeTime));
	}
	
	private void readObject(ObjectInputStream inStream) throws IOException, ClassNotFoundException{//callback,must be private
		inStream.defaultReadObject() ;
		readTime = new Date();
		System.out.println("de-serailizable"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(readTime));
	}
	
	@Override
	public String toString(){
		StringBuffer bufferStr = new StringBuffer("");
		if(null!=basicBeans){
			for(BasicBean bean : basicBeans){
				bufferStr.append(bean.toString()) ;
			}
		}
		return "SerialClass[className="+className+
				          ",version="+version+
				          ",account="+account+
				          ",mapParam="+mapParam+
				          ",basicBeans="+bufferStr+
				          ",writeTime="+ (null == writeTime ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(writeTime))+
				          ",readTime="+( null == readTime ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(readTime))+
	                      "]";
	}
}

class BasicBean implements Serializable{

	private static final long serialVersionUID = 6157094766422536096L;
	
	private int id = 0 ;
	private String name = null ;
	
	public BasicBean(int id ,String name){
		this.id = id ;
		this.name = name ;
	}
	
	@Override
	public String toString(){
		return "Bean[id="+id+
				",name="+name+
				"]";
	}
}
