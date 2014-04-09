package mhurd.scratch;

import sun.plugin2.applet.Applet2ClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: mhurd
 * Date: 20/08/12
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class InheritanceTest {

    private static class SuperSuper {

        protected void overriddenMethod() {
            System.out.println(getClass().getCanonicalName() + ": SuperSuper.overriddenMethod");
        }

    }

    private static class Super extends SuperSuper {

        protected void overriddenMethod() {
            super.overriddenMethod();
            System.out.println(getClass().getCanonicalName() + ": Super.overriddenMethod");
        }

    }

    private static class AClass extends Super {

        protected void overriddenMethod() {
            super.overriddenMethod();
            System.out.println(getClass().getCanonicalName() + ": AClass.overriddenMethod");
        }

    }

    public static void main(String[] args) {
        AClass aClass = new AClass();
        aClass.overriddenMethod();
    }

}
