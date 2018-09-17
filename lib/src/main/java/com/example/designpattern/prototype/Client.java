package com.example.designpattern.prototype;

import java.util.List;
import java.util.Vector;

/**
 * Date：2018/9/11
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        List<Integer> list = new Vector<>();
        while (true){
            list.add(1);
            if (list.size()==10){
                break;
            }
        }

        list.add(1);

        System.out.print(list.toArray());
    }
}
