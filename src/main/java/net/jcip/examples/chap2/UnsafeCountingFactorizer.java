//package net.jcip.examples.chap2;
//
//import java.math.BigInteger;
//import javax.servlet.*;
//
//import net.jcip.annotations.*;
//
///**
// * UnsafeCountingFactorizer
// *
// * Servlet that counts requests without the necessary synchronization
// *
// * @author Brian Goetz and Tim Peierls
// */
//@NotThreadSafe
//public class UnsafeCountingFactorizer extends GenericServlet implements Servlet {
//    private long count = 0;
//
//    public long getCount() {
//        return count;
//    }
//
//    public void service(ServletRequest req, ServletResponse resp) {
//        BigInteger i = extractFromRequest(req);
//        BigInteger[] factors = factor(i);
//        ++count;//non-atomic operator.[Read-Modify-Write]  ++count >>> 1.get(tmp=count) 2.calc(tmp+1) 3.return(count = tmp+1) 
//        encodeIntoResponse(resp, factors);
//    }
//
//    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
//    }
//
//    BigInteger extractFromRequest(ServletRequest req) {
//        return new BigInteger("7");
//    }
//
//    BigInteger[] factor(BigInteger i) {
//        // Doesn't really factor
//        return new BigInteger[] { i };
//    }
//}
