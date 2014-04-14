/*
 * @(#)CloneTest.java	2013年11月7日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年11月7日>
 * @description 
 */
public class CloneTest {

    public static void main(String[] args) {
        BaseBean orginal = new BaseBean();
        orginal.setMessgae("orginal");
    }

    public static void changeMessage(BaseBean orginal){
        BaseBean subBean = orginal ;
    }
    
}
class BaseBean {
    private String messgae = null ;
    public String getMessgae() {
        return messgae;
    }
    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
}