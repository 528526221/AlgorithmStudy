package com.example;

/**
 * Date：2017/11/17
 * Desc：比较一个对象
 * Created by xuliangchun.
 */

public class Student implements Comparable{
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }


    @Override
    public int compareTo(Object o) {
        if (score<((Student)o).score){
            return -1;
        }else if (score>((Student)o).score){
            return 1;
        }else if (score==((Student)o).score){
            return name.compareTo(((Student) o).name);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Student: "+"name="+name+" score="+score;
    }
}
