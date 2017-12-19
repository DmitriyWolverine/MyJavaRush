package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root ;
    int size;
    final Queue<Entry<String>> queue = new LinkedList<>();

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public void checkChildren() {
            if (this.leftChild != null) {
                this.availableToAddLeftChildren = false;
            }
            if (this.rightChild != null) {
                this.availableToAddRightChildren = false;
            }
        }

        public boolean isAvailableToAddChildren() {
            return (this.availableToAddLeftChildren || this.availableToAddRightChildren);
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }
    }

    public CustomTree() {
        root = new Entry<>("0");
        this.root.setLineNumber(1);
        size = 1;
        this.root.parent = null;
        queue.add(this.root);

    }

    @Override
    public boolean add(String s) {
        if (root == null) {
            root = new Entry<>(s);
            return false;
        }
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        boolean isAdded = false;
        while (!isAdded && !queue.isEmpty()) {
            Entry<String> currentEntry = queue.remove();
            if (!currentEntry.availableToAddLeftChildren && currentEntry.leftChild != null) {
                queue.add(currentEntry.leftChild);
            } else {
                if (!isAdded && currentEntry.availableToAddLeftChildren) {
                    Entry<String> newEntry = new Entry<String>(s);
                    newEntry.parent = currentEntry;
                    currentEntry.leftChild = newEntry;
                    isAdded = true;
                    currentEntry.checkChildren();
                }
            }
            if (!currentEntry.availableToAddRightChildren && currentEntry.rightChild != null) {
                queue.add(currentEntry.rightChild);
            } else {
                if (!isAdded && currentEntry.availableToAddRightChildren) {
                    Entry<String> newEntry = new Entry<String>(s);
                    newEntry.parent = currentEntry;
                    currentEntry.rightChild = newEntry;
                    isAdded = true;
                    currentEntry.checkChildren();
                }
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        String s = (String) o;
        this.remove(s);
        return false;
    }

    public void remove(String s) {
        if (root == null) {
            return;
        }
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        boolean isRemoved = false;
        while (!isRemoved && !queue.isEmpty()) {
            Entry<String> currentEntry = queue.remove();
            if (currentEntry.leftChild!=null && !currentEntry.leftChild.elementName.equals(s)) {
                queue.add(currentEntry.leftChild);
            } else {
                if (!isRemoved) {
                    currentEntry.leftChild = null;
                    isRemoved = true;
                }
            }
            if ( currentEntry.rightChild!=null && !currentEntry.rightChild.elementName.equals(s)) {
                queue.add(currentEntry.rightChild);
            } else {
                if (!isRemoved) {
                    currentEntry.rightChild = null;
                    isRemoved = true;
                }
            }
        }
    }

    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        int count = 1;
        queue.add(root);
        while (!queue.isEmpty()) {
            Entry<String> currentEntry = queue.remove();
            if (!currentEntry.availableToAddRightChildren && currentEntry.rightChild != null) {
                count++;
                queue.add(currentEntry.rightChild);
            }
            if (!currentEntry.availableToAddLeftChildren && currentEntry.leftChild != null) {
                count++;
                queue.add(currentEntry.leftChild);
            }
        }
        return (count-1);
    }

    public String getParent(String s) {
        if (root == null) {
            return null;
        }
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        String parentName = null;
        while (parentName == null && !queue.isEmpty()) {
            Entry<String> currentEntry = queue.remove();
            if (currentEntry.leftChild != null) {
                if (!currentEntry.leftChild.elementName.equals(s)) {
                    queue.add(currentEntry.leftChild);
                } else {
                    parentName = currentEntry.elementName;
                }
            }
            if (currentEntry.rightChild != null) {
                if (!currentEntry.rightChild.elementName.equals(s)) {
                    queue.add(currentEntry.rightChild);
                } else {
                    parentName = currentEntry.elementName;
                }
            }
        }
        return parentName;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println(list.size());
        list.remove("15");
        System.out.println(list.size());

        System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");
        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
    }
}
