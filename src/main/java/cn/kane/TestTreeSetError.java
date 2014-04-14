package cn.kane;
import java.util.TreeSet;

class R implements Comparable<Object> {
	int count;

	public R(int count) {
		this.count = count;

	}

	public String toString() {
		return "R(count:" + count + ")";

	}

	public boolean equals(Object o) {
		if (o instanceof R) {
			R r = (R) o;
			if (this.count == r.count)
				return true;
		}
		return false;
	}

	public int compareTo(Object o) {
		R r = (R) o;
		if (this.count > r.count)
			return 1;
		else if (this.count == r.count)
			return 0;
		else
			return -1;
	}
}

public class TestTreeSetError {

	public static void main(String[] args) {

		TreeSet<R> ts = new TreeSet<R>();
		ts.add(new R(5));
		ts.add(new R(-3));
		ts.add(new R(9));
		ts.add(new R(-2));
		System.out.println("1 = " + ts);
		R first = (R) ts.first();
		first.count = 20;
		System.out.println("2 = " + ts);
		R last = (R) ts.last();
		last.count = -2;
		System.out.println("3 = " + ts);
		System.out.println(ts.remove(new R(-2)));
		System.out.println(ts);
		System.out.println(ts.remove(new R(5)));
		System.out.println(ts);
		System.out.println(ts.remove(new R(-2)));
		System.out.println(ts);

	}
}