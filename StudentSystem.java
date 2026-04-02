package com.itheima.studentsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    public static void startStudentsystem() {
        ArrayList<Student> list = new ArrayList<>();
        loop:
        while (true) {
            System.out.println("-------------------欢迎来到黑马学生管理系统-------------------");
            System.out.println("1.添加学生");
            System.out.println("2.删除学生");
            System.out.println("3.修改学生");
            System.out.println("4.查询学生");
            System.out.println("5.退出系统");
            System.out.println("请输入你的选择：");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent(list);
                case "2" -> deleteStudent(list);
                case "3" -> updateStudent(list);
                case "4" -> queryStudent(list);
                case "5" -> {
                    System.out.println("退出系统");
                    //break  loop;
                    System.exit(0);//停止虚拟机运行
                }
                default -> System.out.println("没有这个选项");
            }
        }
    }

    //添加学生
    public static void addStudent(ArrayList<Student> list) {
        //利用空参构造方法创建Student对象
        Student s = new Student();

        Scanner sc = new Scanner(System.in);
        String id = null;
        while (true) {
            System.out.println("请输入学生id：");
            id = sc.next();
            boolean flag = contains(list, id);
            if (flag) {
                //表示id已经存在，需要重新录入
                System.out.println("id已经存在，请重新输入");
            } else {
                s.setId(id);
                break;
            }
        }

        System.out.println("请输入学生姓名：");
        String name = sc.next();
        s.setName(name);

        System.out.println("请输入学生年龄：");
        int age = sc.nextInt();
        s.setAge(age);

        System.out.println("请输入学生家庭地址：");
        String address = sc.next();
        s.setAddress(address);

        //将学生对象添加到集合中
        list.add(s);
        //提示添加成功
        System.out.println("添加学生信息成功");
    }

    //删除学生
    public static void deleteStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除的学生id：");
        String id = sc.next();
        //查询id在集合中的索引
        int index = getIndex(list, id);
        //对index进行判断
        //如果大于等于0的，表示存在，删除
        //如果-1，表示不存在，结束方法，回到初始菜单
        if (index >= 0) {
            list.remove(index);
            //提示删除成功
            System.out.println("删除成功，id为：" + id + "的学生信息");
        } else {
            System.out.println("删除失败，id不存在");
        }
    }

    //修改学生
    public static void updateStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改的学生id：");
        String id = sc.next();

        int index = getIndex(list, id);

        if(index == -1){
            System.out.println("要修改的id"+id+"不存在,请重新输入");
            return;
        }

        //获取要修改的学生对象
        Student stu = list.get(index);

        //修改学生信息
        System.out.println("请输入新的姓名：");
        String NewName = sc.next();
        stu.setName(NewName);

        System.out.println("请输入新的年龄：");
        int NewAge = sc.nextInt();
        stu.setAge(NewAge);

        System.out.println("请输入新的家庭地址：");
        String NewAddress = sc.next();
        stu.setAddress(NewAddress);

        //提示修改成功
        System.out.println("学生信息修改成功");
    }

    //查询学生
    public static void queryStudent(ArrayList<Student> list) {
        if (list.size() == 0) {
            System.out.println("当前无学生信息，请添加后再查询");
            //结束方法
            return;
        }
        //打印表头信息
        System.out.println("id\t\t姓名\t年龄\t家庭地址");
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            System.out.println(stu.getId() + "\t" + stu.getName() + "\t" + stu.getAge() + "\t" + stu.getAddress());
        }
    }

    //判断id是否存在
    public static boolean contains(ArrayList<Student> list, String id) {
      /*  //遍历循环集合得到里面每一个学生对象
        for (int i = 0; i < list.size(); i++) {
            //拿到学生对象后，获取id并进行判断
            Student stu = list.get(i);
            String sid = stu.getId();
            if (sid.equals(id)) {
                //存在，true
                return true;
            }
        }
        //不存在，false
        return false;*/
        return getIndex(list, id) >= 0;
    }


    public static int getIndex(ArrayList<Student> list, String id) {
        //遍历集合
        for (int i = 0; i < list.size(); i++) {
            //获取每一个学生对象
            Student stu = list.get(i);
            //获取学生对象的id
            String sid = stu.getId();
            if (sid.equals(id)) {
                //索引存在,返回索引值
                return i;
            }
        }
        //索引不存在,返回-1
        return -1;
    }
}
