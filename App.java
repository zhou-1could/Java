package com.itheima.studentsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作：1.登录 2.注册 3.忘记密码 4.退出");
            String choose = sc.next();
            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("谢谢使用，再见");
                    System.exit(0);
                }
                default -> System.out.println("没有这个选项");
            }
        }
    }

    private static void login(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入用户名：");
            String username = sc.next();
            //判断用户名是否存在
            boolean flag = contains(list, username);
            if (!flag) {
                System.out.println("用户名" + username + "未注册，请先注册再登录");
                return;
            }

            System.out.println("请输入密码：");
            String password = sc.next();

            while (true) {
                String rightCode = getCode();
                System.out.println("当前正确验证码为：" + rightCode);
                System.out.println("请输入验证码：");
                String code = sc.next();
                if (code.equalsIgnoreCase(rightCode)) {
                    System.out.println("验证码正确");
                    break;
                } else {
                    System.out.println("验证码错误");
                    continue;
                }
            }

            //验证用户名和密码是否正确
            //集合中是否包含用户名和密码
            //定义一个方法验证用户名和密码是否正确

            //封装思想的应用：
            //我们可以把一些零散的数据，封装成一个对象
            //以后传参数的时候，只要传递一个整体就可以了，不需要管这些零散数据
            User userInfo = new User(username, password, null, null);
            boolean result = checkUserInfo(list, userInfo);
            if (result) {
                System.out.println("登录成功,可以开始使用学生管理系统了");
                //创建对象调用方法，启动学生管理系统
                StudentSystem ss = new StudentSystem();
                ss.startStudentsystem();
                break;
            } else {
                System.out.println("登录失败，用户名或密码错误");
                if (i == 2) {
                    System.out.println("当前账号" + username + "被锁定,请联系管理员黑马客服小姐姐：xxx-xxx");
                    //当前账号锁定之后，直接结束方法即可
                    return;
                } else {
                    System.out.println("用户名或者密码错误，还有" + (2 - i) + "次机会重新登录");
                }
            }
        }


    }

    private static boolean checkUserInfo(ArrayList<User> list, User userInfo) {
        //遍历集合判断用户是否存在，如果存在登录成功，否则登录失败
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(userInfo.getUsername()) && user.getPassword().equals(userInfo.getPassword())) {
                return true;
            }
        }
        return false;
    }

    private static void register(ArrayList<User> list) {

        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        String username;
        while (true) {
            System.out.println("请输入用户名：");
            username = sc.next();
            //开发细节：先验证格式是否正确，再验证是否唯一
            //         因为在以后的所有数据，都是存在数据库中，如果我们要校验，需要使用到网络资源

            boolean flag1 = checkUsername(username);
            if (!flag1) {
                System.out.println("用户名格式不满足条件，需要用户重新输入");
                continue;
            }

            //用户名唯一
            //校验用户名唯一
            //username在集合中是否存在
            boolean flag2 = contains(list, username);
            if (flag2) {
                System.out.println("用户名" + username + "已存在，请重新输入");
                continue;
            } else {
                //不存在，当前用户名可用，可以继续录入下面的其他数据
                System.out.println("用户名" + username + "可用");
                break;
            }
        }

        //2.键盘录入密码
        //开发细节：密码必须两次输入一致
        String password;
        while (true) {
            System.out.println("请输入要注册的密码：");
            password = sc.next();
            System.out.println("请再次输入要注册的密码：");
            String againPassword = sc.next();
            if (!password.equals(againPassword)) {
                System.out.println("两次输入的密码不一致，请重新输入");
                continue;
            } else {
                //两次输入的密码一致，可以继续录入下面的其他数据
                System.out.println("两次输入的密码一致，继续录入其他数据");
                break;
            }
        }

        //3.键盘录入身份证号
        String personID;
        while (true) {
            System.out.println("请输入身份证号：");
            personID = sc.next();
            boolean flag = checkPersonID(personID);
            if (flag) {
                System.out.println("身份证号码满足要求");
                break;
            } else {
                System.out.println("身份证号码格式有误，请重新输入");
                continue;
            }
        }

        //4.键盘录入手机号
        String phoneNumber;
        while (true) {
            System.out.println("请输入手机号：");
            phoneNumber = sc.next();
            boolean flag = checkPhoneNumber(phoneNumber);
            if (flag) {
                System.out.println("手机号格式正确");
                break;
            } else {
                System.out.println("手机号格式有误，请重新输入");
                continue;
            }
        }

        //用户名，密码，身份证号，手机号放到用户对象中
        User user = new User(username, password, personID, phoneNumber);
        //把用户对象添加到集合中
        list.add(user);
        System.out.println("注册成功");

        //遍历集合
        printList(list);
    }

    private static void printList(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            //i 索引
            User user = list.get(i);
            System.out.println(user.getUsername() + "," + user.getPassword() + "," + user.getPersonID() + "," + user.getPhoneNumber());
        }
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        //长度为11位
        if (phoneNumber.length() != 11) {
            return false;
        }
        //不能以0 开头
        if (phoneNumber.startsWith("0")) {
            return false;
        }
        //必须都是数字
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //当循环结束之后，就表示每一个字符都在0~9之间
        return true;
    }

    private static boolean checkPersonID(String personID) {
        //长度为18位
        if (personID.length() != 18) {
            return false;
        }
        //不能以0为开头
        if (personID.startsWith("0")) {
            //如果以0开头，则返回false
            return false;
        }
        //前17位必须是数字
        for (int i = 0; i < personID.length() - 1; i++) {
            char c = personID.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //最后一位可以是数字，也可以是大写x或者是小写x
        char endChar = personID.charAt(personID.length() - 1);
        if ((endChar >= '0' && endChar <= '9') || (endChar == 'x') || (endChar == 'X')) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean contains(ArrayList<User> list, String username) {
        //循环遍历集合，得到每一个用户对象
        //拿着用户对象中的用户名进行比较
        for (int i = 0; i < list.size(); i++) {
            //i 索引
            User user = list.get(i);
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        //当循坏结束了，表示集合里面所有的用户都比较完毕，还没有一样的，则返回false
        return false;
    }

    private static boolean checkUsername(String username) {
        //用户名长度在3~15位之间
        int len = username.length();
        if (len < 3 || len > 15) {
            return false;
        }
        //当代码执行到这里，表示用户名长度是符合要求的
        //继续校验：只能是字母加数字组合
        //循环遍历username里面的每一个字符，如果有一个字符不是字母或数字，则返回false
        for (int i = 0; i < username.length(); i++) {
            //i 索引
            char c = username.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }
        //当代码执行到这里表示什么？
        //用户名满足两个要求，：1.长度满足 2内容也满足（字母+数字）
        //但是不能是纯数字
        //统计在用户名中，有多少字母就行
        int count = 0;
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
                break;
            }
        }
        return count > 0;
    }

    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        boolean flag = contains(list, username);
        if (!flag) {
            System.out.println("当前用户"+username+"未注册，请先注册");
            return;
        }

        //键盘录入身份证号码和手机号码
        System.out.println("请输入身份证号码：");
        String personID = sc.next();
        System.out.println("请输入手机号码：");
        String phoneNumber = sc.next();

        //需要把用户对象通过索引先获取出来
        int index = findIndex(list, username);
        User user = list.get(index);
        //比较用户对象中的身份证号码和手机号码是否一致
        if (!user.getPersonID().equals(personID) || !user.getPhoneNumber().equals(phoneNumber)) {
            System.out.println("身份证号码或手机号码错误");
            return;
        }

        //当代码执行到这里，表示所有数据验证成功，直接修改即可
        String password;
        while (true) {
            System.out.println("请输入新密码：");
             password = sc.next();
            System.out.println("请再次输入新密码：");
            String againPassword = sc.next();
            if(password.equals(againPassword)){
                System.out.println("两次密码输入一致");
                break;
            }else{
                System.out.println("两次密码输入不一致,请重新输入");
                continue;
            }
        }
        //修改密码
        user.setPassword(password);
        System.out.println("密码修改成功");
    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equalsIgnoreCase(username)) {
                return i;
            }
        }
        return -1;
    }

    //生成一个验证码
    private static String getCode() {
        //1.创建一个集合添加所有的大写和小写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        StringBuilder sb = new StringBuilder();
        //2.从集合中随机获取4个字符
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            //获取随机索引
            int index = random.nextInt(list.size());
            //利用随机索引获取字符
            char c = list.get(index);
            //将字符添加到StringBuilder中
            sb.append(c);
        }
        //3.把一个随机数字添加到末尾
        int number = random.nextInt(10);
        sb.append(number);

        //4.如果我们要修改字符串中的内容
        //先把字符串变成字符数组，在数组中修改，然后创建一个新的字符串
        char[] arr = sb.toString().toCharArray();
        //拿着最后一个索引，跟随机索引进行交换
        int randomIndex = random.nextInt(arr.length);
        //最大索引指向的元素，跟随机索引指向的元素进行交换
        char temp = arr[randomIndex];
        arr[randomIndex] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;

        return new String(arr);
    }

}
