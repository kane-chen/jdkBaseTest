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

import junit.framework.TestCase;


public class CacheManWithJUnit extends TestCase{  
  
	 TreeSet<UserBean> tree = null ;
	 ConcurrentHashMap<String, UserBean> userMap = null;
	 {
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
		
		initDatasInTreeSet(100);
	}
	
    public  void testCacheMan() {     
        new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				timingTask("tttttttt");
			}
        }, 5000, 5000);
     }  
    public  void initDatasInTreeSet(int counts){
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
    		
    		int added = random.nextInt(1000);
    		int end = random.nextInt(1000);
    		
    		UserBean user = new UserBean(userName,userType,new Date(currentTime.getTime()+added),end);
    		userMap.put(userName, user);
    		tree.add(user);
    	}
    	System.out.println("init done ,userMap's size is "+userMap.size()+" ; tree's size is "+tree.size());
    }
    public  void timingTask(String callerName) {
		System.out.println(callerName+" call checkValidTimeInCacheManager");
		
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
			System.out.println("timingTask remove invalidTimeUser "+userName);
			removeInvalidTimeUser(userName,true);//删除缓存中记录，并同步至数据库
		}
		System.out.println("After removing invalidTimeUser by timingTask, validTimedUserMap's size is "+ (null==tree?"0" : tree.size()));
		System.out.println("After removing invalidTimeUser by timingTask, userMap's size is "+ userMap.size());
	}

  //删除检测队列中的userName记录
  	public  void removeInvalidTimeUser(String userName,boolean syncDB){
  		if(null!=userName){
  			UserBean user = userMap.get(userName);//此处通过userMap获取userBean
  			if(null!=user){
  				System.out.println("sortedValidTimeUsers remove user:: "+user.getUserName());
  				//删除有EndTime且未过期用户名单列单中的记录
  				if(null!=tree){
  					tree.remove(user);
  				}
  				userMap.remove(userName);
  			}
  			if(tree.size()!=userMap.size()){
				System.out.println(userName);
				Iterator<UserBean> iterator = tree.iterator();
				while(iterator.hasNext()){
					System.out.println("-------- iterator running -------");
					UserBean user1=iterator.next();
					if(user1.getUserName().equals(userName)){
						iterator.remove();
						System.out.println("========= iterator removed ==========");
						break;
						}
					}
			}
  		}
  	}
    }  
