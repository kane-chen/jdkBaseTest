package cn.kane;

import java.math.BigDecimal;

import junit.framework.TestCase;

public class FinallyTest extends TestCase{

	private final int INIT = -1 ;
	private final int SUCCESS = 0 ;
	private final int EXCP = 1 ;
	private final int FINAL = 2 ;
	
	private void divide(BigDecimal dividend,BigDecimal divisor) throws Exception{
		int result = INIT ;
		try{
			dividend.divide(divisor);
		}catch(Exception e){
			System.out.println("CATCH-EXCEPTION");
			result = EXCP ;
			throw e ;//finally-block could not execute
		}finally{
			System.out.println("FINAL-BLOCK");
			result = FINAL ;
		}
		result = SUCCESS ;
		System.out.println(result);
	}

	private int divideAndReturn(BigDecimal dividend,BigDecimal divisor){
		int result = INIT ;
		try{
			dividend.divide(divisor);
			result = SUCCESS ;
			System.out.println("SUCCESS");
			return result ;
		}catch(Exception e){
			result = EXCP ;
			System.out.println("CATCH-EXCEPTION");
			return result ; //1.put result in TEMP-memory,2.run finally-block;3.return result in TEMP-memory[1]
		}finally{
			System.out.println("FINAL-BLOCK");
			result = FINAL ;
		}
	}
	
	private BigDecimal divideAndReturnBigDecimal(BigDecimal dividend,BigDecimal divisor){
		BigDecimal result = new BigDecimal(INIT) ;
		try{
			dividend.divide(divisor);
			result = new BigDecimal(SUCCESS) ;
			System.out.println("SUCCESS");
			return result ;
		}catch(Exception e){
			result = new BigDecimal(EXCP) ;
			System.out.println("CATCH-EXCEPTION");
			return result ;
		}finally{
			System.out.println("FINAL-BLOCK");
			result = new BigDecimal(FINAL) ;
		}
	}
	
	@SuppressWarnings("finally")
	private BigDecimal divideAndFinalReturnBigDecimal(BigDecimal dividend,BigDecimal divisor){
		BigDecimal result = new BigDecimal(INIT) ;
		try{
			dividend.divide(divisor);
			result = new BigDecimal(SUCCESS) ;
			System.out.println("SUCCESS");
			return result ;
		}catch(Exception e){
			result = new BigDecimal(EXCP) ;
			System.out.println("CATCH-EXCEPTION");
			return result ;
		}finally{
			System.out.println("FINAL-BLOCK");
			result = new BigDecimal(FINAL) ;
			return result;    //return-value in finally will replace return-value in TEMP
		}
	}
	
	@SuppressWarnings("finally")
	private void eatException() throws Exception{
		try{
			throw new Exception("TRY-EXCEPTION");
		}finally{
			throw new Exception("FINAL-EXCEPTION");//final-exception will replace try-exception
		}
	}
	
	public void testEatException(){
		try {
			eatException();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testFinally(){
		try {
			divide(new BigDecimal(10),new BigDecimal(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(divideAndReturn(new BigDecimal(10),new BigDecimal(0)));//result is EXCP
		System.out.println(divideAndReturn(new BigDecimal(10),new BigDecimal(2)));//result is SUCCESS
		System.out.println(divideAndReturnBigDecimal(new BigDecimal(10),new BigDecimal(0)));//result is EXCP
		System.out.println(divideAndReturnBigDecimal(new BigDecimal(10),new BigDecimal(2)));//result is SUCCESS
		System.out.println(divideAndFinalReturnBigDecimal(new BigDecimal(10),new BigDecimal(0)));//result is FINAL
		System.out.println(divideAndFinalReturnBigDecimal(new BigDecimal(10),new BigDecimal(2)));//result is FINAL
	}
	
	public void testFinalAfterExit(){
		try{
			throw new Exception("any-exception");
		}catch(Exception e){
			System.out.println(e);
			System.out.println("CATCH-EXCEPTION");
			System.exit(0);//final-block can not execute
		}finally{
			System.out.println("FINAL-BLOCK");
		}
	}
	
	
}
