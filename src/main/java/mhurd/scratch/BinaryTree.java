package mhurd.scratch;

public class BinaryTree {



    protected static interface Ord<T> {

        public T getValue();

        public boolean isLessThan(Ord<T> value);

        public boolean isEqualTo(Ord<T> value);

        public boolean isGreaterThan(Ord<T> value);

    }


    protected static interface ITree<T extends Ord> {
        public ITree<T> treeInsert(T value);

        public boolean treeElem(T value);
    }

    protected static abstract class AbstractTree<T extends Ord> implements ITree<T> {

        protected T value;
        protected ITree left;
        protected ITree right;

        public String toString() {
            StringBuilder b = new StringBuilder(" (Node ");
            b.append(value);
            b.append(left.toString());
            b.append(right.toString());
            b.append(")");
            return b.toString();
        }

    }

    protected static class EmptyTree<T extends Ord> extends AbstractTree<T> {

        @Override
        public ITree treeInsert(T value) {
            return new Tree(value, new EmptyTree(), new EmptyTree());
        }

        @Override
        public boolean treeElem(T value) {
            return false;
        }

        public String toString() {
            return " EmptyTree ";
        }

    }

    protected static class Tree<T extends Ord> extends AbstractTree<T> {

        private Tree(T value, ITree<T> left, ITree<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public ITree treeInsert(T value) {
            if (value.isEqualTo(this.value)) {
                return this;
            } else if (value.isGreaterThan(this.value)) {
                // is greater than the current value, go in the right tree
                this.right = this.right.treeInsert(value);
                return this;
            } else if (value.isLessThan(this.value)) {
                // is less than the current value, go in the left tree
                this.left = this.left.treeInsert(value);
                return this;
            } else {
                throw new IllegalArgumentException("WTF!");
            }
        }

        @Override
        public boolean treeElem(T value) {
            if (value.isEqualTo(this.value)) {
                return true;
            } else if (value.isGreaterThan(this.value)) {
                // is greater than the current value, look in the right tree
                return this.left.treeElem(value);
            } else if (value.isLessThan(this.value)) {
                // is less than the current value, look in the left tree
                return this.right.treeElem(value);
            } else {
                throw new IllegalArgumentException("WTF!");
            }
        }

    }

    private static class MyInt implements Ord<Integer> {

        private final Integer myInt;

        protected MyInt(int integer) {
            this.myInt = integer;
        }

        public Integer getValue() {
            return myInt;
        }

        @Override
        public boolean isLessThan(Ord<Integer> value) {
            return myInt.compareTo(value.getValue()) == -1;
        }

        @Override
        public boolean isEqualTo(Ord<Integer> value) {
            return myInt.compareTo(value.getValue()) == 0;
        }

        @Override
        public boolean isGreaterThan(Ord<Integer> value) {
            return myInt.compareTo(value.getValue()) == 1;
        }

        public String toString() {
            return getValue().toString();
        }

    }

    public static void main(String[] args) {

        ITree tree = new EmptyTree<MyInt>();

        tree = tree.treeInsert(new MyInt(5))
            .treeInsert(new MyInt(3))
            .treeInsert(new MyInt(7))
            .treeInsert(new MyInt(1))
            .treeInsert(new MyInt(4))
            .treeInsert(new MyInt(6))
            .treeInsert(new MyInt(8));

        System.out.println(tree);

    }

}
