package com.mhurd.scratch.jdk8;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultMethods {

    private interface Parent {
        default String welcome() {
            return "Parent";
        }
    }

    private interface Guardian {
        default String welcome() {
            return "Guardian";
        }
    }


    private interface Child extends Parent {
        default String welcome() {
            return "Child";
        }
    }

    private static class ParentImpl implements Parent {
        public String welcome() {
            return "ParentImpl";
        }
    }

    private static class ParentDefault implements Parent {
        // will inherit default welcome from Parent
    }

    private static class ChildDefault implements Child {
        // will inherit default welcome from Child
    }

    private static class ChildImpl implements Child {
        public String welcome() {
            return "ChildImpl";
        }
    }

    private static class OverridingParent extends ParentImpl {
        public String welcome() {
            return "OverridingParent";
        }
    }

    private static class OverridingChild extends OverridingParent implements Child {
        // will inherit welcome from OverridingParent (Class implementation wins vs. Interface default)
    }

    @Test
    public void testParentImpl() {
        Parent parent = new ParentImpl();
        assertEquals("ParentImpl", parent.welcome());
    }

    @Test
    public void testChildImpl() {
        Child child = new ChildImpl();
        assertEquals("ChildImpl", child.welcome());
    }

    @Test
    public void testOverridingParent() {
        Parent parent = new OverridingParent();
        assertEquals("OverridingParent", parent.welcome());
    }

    @Test
    public void testOverridingChild() {
        Child child = new OverridingChild();
        assertEquals("OverridingParent", child.welcome()); // remember - class wins!
    }

    @Test
    public void testChildDefault() {
        Child child = new ChildDefault();
        assertEquals("Child", child.welcome());
    }

    @Test
    public void testParentDefault() {
        Parent parent = new ParentDefault();
        assertEquals("Parent", parent.welcome());
    }

    @Test
    public void testMultipleInheritance() {
        class Carer implements Parent, Guardian {
            // without this override the compile would fail as which 'welcome' to use?
            @Override
            public String welcome() {
                return Parent.super.welcome(); // call to the desired implementation using the super syntax
            }
        }
        assertEquals("Parent", new Carer().welcome());
    }


}
