package com.mhurd.scratch;

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
