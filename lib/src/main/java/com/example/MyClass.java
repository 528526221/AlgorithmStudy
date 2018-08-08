package com.example;


public class MyClass extends Apple.SmallApple {

    interface Type1 {
        void test() throws ClassNotFoundException;
    }

    interface Type2 {
        void test() throws NoSuchMethodException;
    }

    public static void main(String[] args) {
//        String[] strings = new String[]{"我","的","名","字"};
//        String[] strings1 = new String[4];
//        System.arraycopy(strings,0,strings1,1,strings.length-1);
//
//        for (int i=0;i<strings1.length;i++){
//            System.out.print(strings1[i]+"\n");
//        }
//
//
//        int i=2;
//        i <<= 3;//左移N位，就是这个数乘以2的N次方；同理，右移N位，就是这个数除以2的N次方
//        System.out.print(i);

//        int[] a = new int[]{1,2,3,4,5,0};
//
//        System.arraycopy(a,2,a,3,5-2);
//
//        System.out.print(Arrays.toString(a));
//
//        int[] b = Arrays.copyOf(a,10);
//        System.out.print(Arrays.toString(b));


//        SequenceStack<Integer> stack = new SequenceStack<>();
//        for (int i=0;i<1;i++){
//            stack.push(i);
//        }
//        stack.pop();
//        System.out.print(stack);
//
//        ThreeLinkBinTree<String> binTree = new ThreeLinkBinTree<>("根节点");
//        ThreeLinkBinTree.TreeNode tn1 = binTree.addNode(binTree.getRoot(),"第2层左节点",true);
//        ThreeLinkBinTree.TreeNode tn2 = binTree.addNode(binTree.getRoot(),"第2层右节点",false);
//        ThreeLinkBinTree.TreeNode tn3 = binTree.addNode(tn2,"第3层左节点",true);
//        ThreeLinkBinTree.TreeNode tn4 = binTree.addNode(tn2,"第3层右节点",false);
//        ThreeLinkBinTree.TreeNode tn5 = binTree.addNode(tn3,"第4层左节点",true);
//
//        System.out.print("tn2的左子节点 ： "+binTree.leftChild(tn2)+"\n");
//        System.out.print("tn2的右子节点 ： "+binTree.rightChild(tn2)+"\n");
//        System.out.print("tn3的父节点 ： "+binTree.getParent(tn3)+"\n");
//
//        System.out.print("深度为："+binTree.deep()+"\n");

        SortedBinTree<Integer> tree = new SortedBinTree<Integer>(5);
        tree.add(20);
        tree.add(10);
        tree.add(3);
        tree.add(8);
        tree.add(15);
        tree.add(30);
        tree.add(40);

        System.out.print("深度为："+tree.breadthFirst()+"\n");

        tree.remove(20);

        System.out.print("深度为："+tree.breadthFirst()+"\n");

    }



}

