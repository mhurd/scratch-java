package com.mhurd.scratch;

public class InnerClasses {

    interface Foo {

    }

    public static class StaticInnerClass {

    }

    class RegularInnerClass {
    }

    public void regularInnerFromTheInside() {
        RegularInnerClass ric = new RegularInnerClass();
    }

    public void staticInnerFromTheInside() {
        StaticInnerClass sic = new StaticInnerClass();
    }

    public void methodLocalInnerClass() {
        class MethodLocalInnerClass {
        }
        MethodLocalInnerClass mlic = new MethodLocalInnerClass();
    }

    public void annonymousInnerClass() {
        Foo aic = new Foo() {
        };
    }

    public static void main(String[] args) {
        InnerClasses ic = new InnerClasses();
        // RegularInnerClass from the outside
        InnerClasses.RegularInnerClass ric = ic.new RegularInnerClass();
        // StaticInnerClass from the outside
        StaticInnerClass sic = new StaticInnerClass();
    }

}
