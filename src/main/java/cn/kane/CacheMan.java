package cn.kane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;  
import java.util.concurrent.ConcurrentHashMap;

public class CacheMan {  
  
	 static TreeSet<UserBean> tree = null ;
	 static ConcurrentHashMap<String, UserBean> userMap = null;
	 
	 static int canotRemoved = 0 ;
	 
	 static{
		userMap = new ConcurrentHashMap<String, UserBean>() ;
		tree = new TreeSet<UserBean>(new Comparator<UserBean>(){
			public int compare(UserBean o1, UserBean o2) {
				int result = o1.getEndTime().compareTo(o2.getEndTime()) ;
				if(result==0){//为TreeSet可插入createTime相同的不同的UserBean
					if(o1.hashCode() == o2.hashCode()){
						result = 0 ;
					}else{
						result = 1 ;
					}
				}
				return result ;//按endTime顺序排列
			}
		});
		
		initDatasInTreeSet(100000);
	}
	
    public static void main(String[] args) {     
        new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				timingTask("tttttttt");
			}
        }, 5000, 5000);
     }  
    public static void initDatasInTreeSet(int counts){
    	String userName = null ;
    	String userType = "1";
    	Date currentTime = new Date();
    	Random random = new Random();
    	for(int i=0;i<counts;i++){
    		if(i<10)
    			userName ="00000"+i;
    		else if(i<100)
    			userName ="0000"+i;
    		else if(i<1000)
    			userName ="000"+i;
    		else if(i<10000)
    			userName ="00"+i;
    		else if(i<100000)
    			userName ="0"+i;
    		
    		if(random.nextBoolean()){
    			userType = "2";
    		}
    		
    		int added = random.nextInt(100);
    		int end = random.nextInt(100);
    		
    		UserBean user = new UserBean(userName,userType,new Date(currentTime.getTime()+added),end);
    		userMap.put(userName, user);
    		tree.add(user);
    	}
    	System.out.println("init done ,userMap's size is "+userMap.size()+" ; tree's size is "+tree.size());
    }
    public static void timingTask(String callerName) {
		System.out.println(callerName+" call checkValidTimeInCacheManager");
		
		Date startDate = new Date();
		
		List<String> invalidTimeUsers=new ArrayList<String>();
		//定时任务为删除过期的用户名单
		if(null!=tree && tree.size()>0){
			Date currentTime = new Date();
			synchronized(tree){
				for(UserBean user : tree){
					Date endTime = user.getEndTime();
					if(currentTime.before(endTime)){//未过期
						break;
					}else{//过期，将userName加入过期队列
						String userName = user.getUserName();
						invalidTimeUsers.add(userName);
					}
				}
			}
		}
		
		System.out.println("invalid Timed users' size is "+invalidTimeUsers.size());
		//遍历删除过期用户名单
		for(String userName : invalidTimeUsers){
//			System.out.println("timingTask remove invalidTimeUser "+userName);
			removeInvalidTimeUser(userName,true);//删除缓存中记录，并同步至数据库
		}
		
		Date endDate = new Date();
		
		System.out.println("&&&&&&&&&&& time task cost &&&&&&&&&&&" + (endDate.getTime()-startDate.getTime()));
		
		System.out.println("After removing invalidTimeUser by timingTask, validTimedUserMap's size is "+ (null==tree?"0" : tree.size()));
		System.out.println("After removing invalidTimeUser by timingTask, userMap's size is "+ userMap.size());
		if(tree.size()==0){
			System.out.println("CAN NOT REMOVE SIZE IS "+canotRemoved);
		}
	}

  //删除检测队列中的userName记录
  	public static void removeInvalidTimeUser(String userName,boolean syncDB){
  		if(null!=userName){
  			UserBean user = userMap.get(userName);//此处通过userMap获取userBean
  			if(null!=user){
//  				System.out.println("sortedValidTimeUsers remove user:: "+user.getUserName());
  				//删除有EndTime且未过期用户名单列单中的记录
  				if(null!=tree){
//  					if(!tree.remove(user)){
//  						canotRemoved++;
//  						Iterator<UserBean> iterator = tree.iterator();
//  						while(iterator.hasNext()){
//  							System.out.println("-------- iterator running -------");
//  							UserBean user1=iterator.next();
//  							if(user1.getUserName().equals(userName)){
//  								iterator.remove();
//  								System.out.println("========= iterator removed ==========");
//  								break;
//  								}else{
//  	  								System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
//  	  							}
//  							}
//  					}
  					Iterator<UserBean> iterator = tree.iterator();
  					while(iterator.hasNext()){
						UserBean user1=iterator.next();
						if(user1.getUserName().equals(userName)){
							iterator.remove();
							break;
							}
						}
  				}
  				userMap.remove(userName);
  			}
  			if(tree.size()!=userMap.size()){
				System.out.println(userName);
			}
  		}
  	}
    }  
      
 class UserBean implements Comparable<UserBean>{
    private String userName;
    private String userType;
    private Date createTime = null ;
	private Date endTime = null ;
  
    public UserBean(String userName, String userType , Date createTime){
        this.userName = userName;
        this.userType = userType;
        this.createTime = createTime ;
    }

    /**
	 * 初始化一个BankUserBean
	 * @param userName
	 * @param userType
	 * @param validTime  有效期，单位：秒
	 */
    public UserBean(String userName, String userType,Date createTime,int validTime){
        this.userName = userName;
        this.userType = userType;
        this.createTime = createTime ;
        this.endTime = new Date(this.createTime.getTime() + validTime*1000);
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
    public int compareTo(UserBean otherUserBean) {
		if(null==otherUserBean){
			return 1 ;
		}else{
			int i = this.createTime.compareTo(otherUserBean.getCreateTime());
			int return_code=i*(-1);//倒序排列
			return return_code;
		}
    }
	
	@Override
	public boolean equals(Object other){
		if(this == other){
			return true;
		}else{
			if(!(other instanceof UserBean)){
				return false;
			}else{
				UserBean others = (UserBean) other;
				if(others.getUserName().equals(userName)){
					return true;
				}else{
					return false;
				}
			}
			
		}
	}
	
	@Override
	public int hashCode(){
		return userName.hashCode();
	}
}