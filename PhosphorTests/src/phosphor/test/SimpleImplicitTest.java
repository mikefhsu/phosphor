package phosphor.test;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.SimpleMultiTaintHandler;
import edu.columbia.cs.psl.phosphor.runtime.Tainter;

public class SimpleImplicitTest {
	public SimpleImplicitTest() {

	}

	public SimpleImplicitTest(String s) {

	}

	public SimpleImplicitTest(int i) {

	}

	static native int foo(int in, double in2);

	public static void main(String[] args) {
		int i = MultiTainter.taintedInt(1, "a");
		int k;

		if (i > 0)//control-tag-add-i
			k = 5;
		else
			//control-tag-add-i
			k = 6;
		int f = MultiTainter.taintedInt(4, "Foo");
		//control-tag-remove-i
		int r = 54;
		switch (f) {
		case 0:
			r = 5;
			break;
		case 1:
			r = 6;
			break;
		case 2:
			r = 7;
			break;
		default:
			foo(r);
			r = 111;
		}
		System.out.println(i + " i - taint is " + Tainter.getTaint(i));
		System.out.println(f + " f - taint is " + Tainter.getTaint(f));
		System.out.println(k + " k - taint is " + Tainter.getTaint(k));
		System.out.println(r + " r - taint is " + Tainter.getTaint(r));
	}

	static int foo(int in) {
		int k = 5;
		System.out.println("In foo, taint is "  + Tainter.getTaint(k));
		if (in > 5)
			return 10;
		return 12;
	}
}
