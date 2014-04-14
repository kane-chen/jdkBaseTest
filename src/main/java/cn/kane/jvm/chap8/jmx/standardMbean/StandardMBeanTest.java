package cn.kane.jvm.chap8.jmx.standardMbean;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class StandardMBeanTest{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer("cn.kane");
		try {
			String className = "cn.kane.chap8.jmx.standardMbean.Car" ;
			ObjectName objName = new ObjectName(mbeanServer.getDefaultDomain()+":type="+className);
			mbeanServer.createMBean(className , objName) ;
			mbeanServer.setAttribute(objName, new Attribute("Color","RED"));
			String color = mbeanServer.getAttribute(objName, "Color").toString();
			System.out.println(color);
			mbeanServer.invoke(objName, "drive", null, null);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
		} catch (AttributeNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		}
	}


}
