package cn.kane.set;

import java.util.ArrayList;
import java.util.List;

public class ObServerTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class ObServer{
	private List<ObClient> onLineClientLst = new ArrayList<ObClient>() ;
	
	private boolean addIntoOnLineLst(ObClient client){
		synchronized(onLineClientLst){
			return onLineClientLst.add(client);
		}
	}
	
	@SuppressWarnings("unused")
	private boolean removeFromOnLineLst(ObClient client){
		synchronized(onLineClientLst){
			return onLineClientLst.remove(client);
		}
	}
	
	public void login(ObClient client){
		boolean result = addIntoOnLineLst(client);
		if(result){
			notifyAllOnlineLst(client);
		}
	}

	@SuppressWarnings("unused")
	private void notifyAllOnlineLst(ObClient newClient) {
		for(ObClient client : onLineClientLst){
//			client.notice(newClient);
		}
	}
}

class ObClient{
	
}
