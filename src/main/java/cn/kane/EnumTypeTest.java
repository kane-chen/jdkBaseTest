/*
 * @(#)EnumTypeTest.java	2013年10月18日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年10月18日>
 * @description 
 */
public class EnumTypeTest {

    public enum SexEnum{
        MALE(1,"MALE","MAN","男") ,
        FEMALE(0,"FEMALE","WOMAN","女"),
        OTHER(10,"XXX","XXX","未知")
        ;
        
        private Integer type ;
        private String enDesc ;
        private String enType ;
        private String cnDesc ;
        SexEnum(int type,String enDesc,String enType ,String cnDesc){
            this.type = type ;
            this.enDesc = enDesc ;
            this.enType = enType ;
            this.cnDesc = cnDesc ;
        }
        public Integer getType() {
            return type;
        }
        public String getEnDesc() {
            return enDesc;
        }
        public String getCnDesc() {
            return cnDesc;
        }
        public String getEnType() {
            return enType;
        }
//        @Override// can not override
//        public static valueOf(String desc){
//        }
        
        
        public static SexEnum valueOf(int type){
            switch(type){
                case 0 : return SexEnum.FEMALE ;
                case 1 : return SexEnum.MALE ;
                default : return null ;
            }
        }
        
        @Override
        public String toString(){
            return this.enType ;
        }
    }
    
    public static void main(String[] args) {
        for(SexEnum sex :SexEnum.values()){
            System.out.println(sex);//System.out.println(sex.toString());
        }
        System.out.println(SexEnum.MALE.ordinal());
        System.out.println(SexEnum.FEMALE.ordinal());
        System.out.println(SexEnum.OTHER.ordinal());
        System.out.println(SexEnum.MALE.compareTo(SexEnum.FEMALE));
        
        System.out.println(SexEnum.valueOf("OTHER"));//Enum.valueOf(String name);//name  = Defined-name
        System.out.println(SexEnum.valueOf(1));
    }

}
