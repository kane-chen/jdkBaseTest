package cn.kane.jvm.chap8.jmx;

import javax.management.Attribute;
import javax.management.Descriptor;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanException;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

public class ModelMBeanTest {

	/**
	 * @param args
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 * @throws MBeanException 
	 * @throws RuntimeOperationsException 
	 * @throws NotCompliantMBeanException 
	 * @throws InstanceAlreadyExistsException 
	 */
	public static void main(String[] args) {
		MBeanServer mserver = MBeanServerFactory.createMBeanServer("cn.kane");
		
		try {
			ObjectName objName = new ObjectName(mserver.getDefaultDomain()+":type=Car");
			ModelMBean mbean = createMBean();
			mbean.setManagedResource(new Car(), "ObjectReference");
			mserver.registerMBean(mbean, objName);
			
			mserver.setAttribute(objName, new Attribute("color","WHITE"));
			System.out.println(mserver.getAttribute(objName, "color"));
			mserver.invoke(objName, "start", null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ModelMBean createMBean() throws RuntimeOperationsException, MBeanException {
		ModelMBeanInfo mbi = getModelMBeanInfo() ;
		return new RequiredModelMBean(mbi);
	}

	private static ModelMBeanInfo getModelMBeanInfo() {
		ModelMBeanAttributeInfo[] attributes = new ModelMBeanAttributeInfo[1] ;
		attributes[0] = new ModelMBeanAttributeInfo("color", "java.lang.String", "ATTR-DESC", true, true, false);
		
		ModelMBeanOperationInfo[] operations = new ModelMBeanOperationInfo[3] ;
		operations[0] = new ModelMBeanOperationInfo("getColor", "COLOR-GETTER", null, "java.lang.String", MBeanOperationInfo.INFO);
		MBeanParameterInfo inputParmInfo = new MBeanParameterInfo("Color","java.lang.String","new-color");
		Descriptor setColorDesc = new DescriptorSupport(new String[] {"name=setColor", "descriptorType=operation","class=cn.kane.chap8.jmx.Car,role=operation" });
		operations[1] = new ModelMBeanOperationInfo("setColor","COLOR-SETTER",new MBeanParameterInfo[]{inputParmInfo},"void",MBeanOperationInfo.ACTION,setColorDesc);
		operations[2] = new ModelMBeanOperationInfo("start","start-desc",null,"void",MBeanOperationInfo.ACTION);
		
		ModelMBeanInfo result = new ModelMBeanInfoSupport("cn.kane.chap8.jmx.Car", "mBeanInfo-desc", attributes, null, operations, null);
		return result;
	}

}
