package jdk5_newFeature_zxx.aopframework;
import java.io.InputStream;
import java.util.Collection;

public class AopFrameworkTest {

	public static void main(String[] args) throws Exception {
		InputStream ips = AopFrameworkTest.class.getResourceAsStream("config.properties");
		Object bean = new BeanFactory(ips).getBean("xxx");
		System.out.println(bean.getClass().getName());
		((Collection<?>)bean).clear();
	}

}
