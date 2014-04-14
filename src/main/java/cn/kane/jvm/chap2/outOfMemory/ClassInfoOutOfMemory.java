package cn.kane.jvm.chap2.outOfMemory;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * @category java.lang.OutOfMemoryError: PermGen space
 * JVM-args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * @author chenqx
 *
 */
public class ClassInfoOutOfMemory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true){
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(Proxyee.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj, Method method, Object[] args,
						MethodProxy proxy) throws Throwable {
					return proxy.invoke(obj, args);
				}
			});
			
			enhancer.create();
		}
	}

}
class Proxyee{
	public Proxyee(){
	}
}
