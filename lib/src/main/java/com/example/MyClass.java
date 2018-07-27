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

        TreeParent<String> queue = new TreeParent<>();
        queue.addNode("root",null);
        System.out.print(queue+"\n");
        TreeParent.Node root = queue.getRootNode();
        queue.addNode("节点1",root);
        System.out.print("深度为："+queue.deep()+"\n");
        queue.addNode("节点2",root);
        System.out.print("深度为："+queue.deep()+"\n");
        java.util.List<TreeParent.Node> nodes = queue.getNodeChild(root);
        System.out.print("第一个子节点："+nodes.get(0)+"\n");
        queue.addNode("节点3",nodes.get(0));
        System.out.print("深度为："+queue.deep()+"\n");




    }


}

