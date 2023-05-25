package utils;
import type.*;
import java.util.*;

public class ManageSystem {
    private static List<Hotel> BigBedRooms = new ArrayList<Hotel>();  // 同一类型房间用一个类来存储，具体的房间信息存储在对象里，List用来存储种类信息
    private static List<Hotel> DoubleBedsRooms = new ArrayList<Hotel>();  // 双床房
    private static List<Hotel> Suites = new ArrayList<Hotel>();  // 套房
    public static void main(String[] args) {
        //添加管理系统予3种大床房型
        BigBedRooms.add(new BigBedRoom("高级大床房", 1, 600));
        BigBedRooms.add(new BigBedRoom("豪华大床房", 2, 700));
        BigBedRooms.add(new BigBedRoom("景观大床房", 3, 850));
        //添加双床房型
        DoubleBedsRooms.add(new DoubleBedsRoom("标准双床房", 4, 600));
        DoubleBedsRooms.add(new DoubleBedsRoom("高级双床房", 5, 800));
        DoubleBedsRooms.add(new DoubleBedsRoom("豪华双床房", 6, 1000));
        //添加套房
        Suites.add(new Suite("景致小套房", 7, 1500));
        Suites.add(new Suite("婴儿小套房", 8, 2000));
        Suites.add(new Suite("亲子大套房", 9, 2500));
        show();
    }
    public static void show() {  // 显示主页面
        while(true) {
            System.out.println("*********民宿客房价格表*********");
            System.out.println("1-进入管理员系统");
            System.out.println("2-进入普通客户系统");
            System.out.println("3-退出系统");
            System.out.println("*****************************");
            System.out.println("请输入您的操作号码:");

            Scanner user = new Scanner(System.in);
            int cnt = 0;
            while(!user.hasNextInt()) {  // 处理int之外的输入
                cnt++;
                if(cnt==3){
                    System.out.println("您已连续输错三次，请注意输入要求");
                }
                System.out.println("请您输入一位有效整数：");
                user.next();
            }
            int num = user.nextInt();
            cnt = 0;
            while(num>3||num<1) {  // 判断num输入的有效性，是否在范围内
                cnt++;
                if(cnt==3){
                    System.out.println("您已连续输错三次，请注意输入要求");
                }
                System.out.println("没有找到对应的选项，请重新输入：");
                num = user.nextInt();
            }
            if(num==1) {
                    user = new Scanner(System.in);
                    CheckPassword(user);  // 检查管理员账号密码
            }
            else if(num==2) {

                UserSystem.User(BigBedRooms, DoubleBedsRooms, Suites, user);
            }// 转到用户系统，用户操作
            else{
                System.out.println("退出系统");
                System.exit(0);  // 结束系统
            }
        }
    }
    private static void CheckPassword(Scanner user) {  // 检查管理员用户账号密码
        while(true) {
            System.out.println("请输入管理员账号：");
            String id = user.next();
            System.out.println("请输入管理员密码：");
            String password = user.next();
            if(Objects.equals(id, "Administrator") && password.equals("123456")) {
                System.out.println("欢迎使用管理系统");
                Manager(user);  // 转到管理员系统
                break;
            }
            else{
                System.out.println("密码错误,请重新输入");
            }
        }
    }
    private static void Manager(Scanner user) {  // 管理员系统
        while(true) {
            System.out.println("********民宿客房管理系统********");
            System.out.println("1-查询客房");
            System.out.println("2-查询订单");
            System.out.println("3-增加客房");
            System.out.println("4-删除客房");
            System.out.println("5-修改客房价格");
            System.out.println("6-返回主页面");
            System.out.println("*****************************");
            System.out.println("请输入您的操作号码:");
            int num;
            int cnt = 0;
            while(!user.hasNextInt()) {  // 处理int之外的输入
                cnt++;
                if(cnt==3){
                    System.out.println("您已连续输错三次，请注意输入要求");
                }
                System.out.println("输入无效，请重新输入一个整数：");
                user.next(); // 清空Scanner缓存，防止无限循环
            }
            cnt = 0;
            num = user.nextInt();
            while(num>6||num<1) {
                cnt++;
                if(cnt==3){
                    System.out.println("您已连续输错三次，请注意输入要求");
                }
                System.out.println("请您输入有效的整数：");
                num = user.nextInt();
            }
            if(num==1) {
                System.out.println("查询客房");
                queryRoom();
            }
            else if(num==2) {
                System.out.println("查询订单");
                queryOrder(UserSystem.customerBigBedRooms, UserSystem.customerDoubleBedsRooms, UserSystem.customerSuites);  // 遍历所有用户订单显示信息
            }
            else if(num==3){
                System.out.println("增加客房");
                addRoom(user);
            }
            else if(num==4){
                System.out.println("删除客房");
                deleteRoom(user);
            }
            else if(num==5){
                System.out.println("修改客房价格");
                updateRoom(user);  // 更新房间信息
            }
            else{
                System.out.println("返回主页面");
                show();
            }
        }
    }
    private static void updateRoom(Scanner user) {
        int userIn = checkRoom(user);
        System.out.println("请输入修改房间的编号:");
        int roomId = user.nextInt();
        System.out.println("请输入您需要修改的金额：");
        double roomPrice = user.nextDouble();
        if(userIn==1) {
            for(int i=0;i<BigBedRooms.size();i++) {  // 遍历所有大床房
                if(BigBedRooms.get(i).getId()==roomId) {  // 遍历寻找唯一ID
                    BigBedRooms.get(i).setPrice(roomPrice);  // 修改房间的价格
                    System.out.println("修改成功。房间："+BigBedRooms.get(i).getType() + "的价格已修改为：" + BigBedRooms.get(i).getPrice());
                    return;
                }
                else if(i==BigBedRooms.size()-1){
                    System.out.println("没有找到您要修改的房间。");
                }
            }
        }else if(userIn==2) {
            for(int i=0;i<DoubleBedsRooms.size();i++) {
                if(DoubleBedsRooms.get(i).getId()==roomId) {  // 遍历寻找唯一ID
                    DoubleBedsRooms.get(i).setPrice(roomPrice);
                    System.out.println("修改成功。房间："+DoubleBedsRooms.get(i).getType() + "的价格已修改为：" + DoubleBedsRooms.get(i).getPrice());
                    return;
                }
                else if(i==DoubleBedsRooms.size()-1){
                    System.out.println("没有找到您要修改的房间。");
                }
            }
        }else if(userIn==3) {
            for(int i=0;i<Suites.size();i++) {
                if(Suites.get(i).getId()==roomId) {  // 遍历寻找唯一ID
                    Suites.get(i).setPrice(roomPrice);
                    System.out.println("修改成功。房间："+Suites.get(i).getType() + "的价格已修改为：" + Suites.get(i).getPrice());
                    return;
                }
                else if(i==Suites.size()-1){
                    System.out.println("没有找到您要修改的房间。");
                }
            }
        }
    }

    private static void deleteRoom(Scanner user) {
        System.out.println("当前房间的清单如下：");
        queryRoom();  // 打印当前系统房间
        int userIn = checkRoom(user);
        System.out.println("请输入您要删除的房间的编号:");
        int userId = 0;
        int cnt = 0;
        while(!user.hasNextInt()) {
            cnt++;
            if(cnt==3){
                System.out.println("您已连续输错三次，请注意输入要求");
            }
            System.out.println("您的输入不合法。请重新输入有效的整数：");
            user.next();  // 清空缓存
        }
        cnt = 0;
        userId = user.nextInt();
        while(userId>10||userId<1) {
            cnt++;
            if(cnt==3){
                System.out.println("您已连续输错三次，请注意输入要求");
            }
            System.out.println("您的输入不合法。请重新输入有效的整数：");
            userId = user.nextInt();
        }
        if(userIn==1) {
            for(int i=0;i<BigBedRooms.size();i++) {
                if(BigBedRooms.get(i).getId()==userId) {
                    System.out.println(BigBedRooms.get(i).getType() + "删除成功。");
                    BigBedRooms.remove(i);
                    return;
                }else if(i==BigBedRooms.size()-1){
                    System.out.println("没有找到您要删除的房间。");
                }
            }
        }
        else if(userIn==2) {
            for(int i=0;i<DoubleBedsRooms.size();i++) {
                if(DoubleBedsRooms.get(i).getId()==userId) {
                    System.out.println(DoubleBedsRooms.get(i).getType() + "删除成功。");
                    DoubleBedsRooms.remove(i);
                    return;
                }else if(i==DoubleBedsRooms.size()-1){
                    System.out.println("没有找到您要删除的房间。");
                }
            }
        }
        else if(userIn==3) {
            for(int i=0;i<Suites.size();i++) {
                if(Suites.get(i).getId()==userId) {
                    System.out.println(Suites.get(i).getType() + "删除成功。");
                    Suites.remove(i);
                    return;
                }else if(i==Suites.size()-1){
                    System.out.println("没有找到您要删除的房间。");
                }
            }
        }
    }
    private static void addRoom(Scanner user) {
        int userIn = checkRoom(user);
        System.out.println("请输入您要添加的房间的具体类型类型:");
        String type = user.next();
        System.out.println("请输入您要添加的房间的价格：");
        double price = user.nextDouble();
        System.out.println("请输入您要添加的房间的编号：");
        int id = 0;
        int cnt = 0;
        while(!user.hasNextInt()) {
            cnt++;
            if(cnt==3){
                System.out.println("您已连续输错三次，请注意输入要求");
            }
            System.out.println("您的输入不合法，请重新输入有效的整数：");
            user.next();
        }
        cnt = 0;
        id = user.nextInt();
        while(id<=9){
            cnt++;
            if(cnt==3){
                System.out.println("您已连续输错三次，请注意输入要求");
            }
            System.out.println("输入编号不合法，请重新输入：");
            id = user.nextInt();
        }
        switch(userIn) {
            case 1:
                BigBedRooms.add(new BigBedRoom(type, id, price));
                break;
            case 2:
                DoubleBedsRooms.add(new DoubleBedsRoom(type, id, price));
                break;
            case 3:
                Suites.add(new Suite(type, id, price));
        }
        System.out.println(type + "-" + id + "添加成功。");
    }

    public static int checkRoom(Scanner user) {
        boolean flag = true;
        int sc = 0;
        while(flag) {
            System.out.println("请输入房间的类型，1-大床房，2-双床房，3-套房:");
            int userIn = user.nextInt();
            if(userIn==1||userIn==2||userIn==3) {
                flag = false;
                sc = userIn;
            }else{
                System.out.println("输入类型有误。");
            }
        }
        return sc;
    }
    public static void queryRoom() {  // 查询房间模块
        System.out.println("*********大床房（3日以上8折）*********");
        System.out.println("具体类型\t\t\t" + "编号\t\t" + "价格");
        for(Hotel htl: BigBedRooms) {  // 查询大床房
            BigBedRoom htl1 = (BigBedRoom)htl;
            System.out.println(htl1.getType() + "\t\t" + htl1.getId() + "\t\t" + htl1.getPrice());
        }
        System.out.println("*********双床房（3日以上8折）*********");
        System.out.println("具体类型\t\t\t" + "编号\t\t" + "价格");
        for(Hotel htl: DoubleBedsRooms) {  // 查询双床房
            DoubleBedsRoom htl1 = (DoubleBedsRoom)htl;
            System.out.println(htl1.getType() + "\t\t" + htl1.getId() + "\t\t" + htl1.getPrice());
        }
        System.out.println("*********套房（3日以上8折）*********");
        System.out.println("具体类型\t\t\t" + "编号\t\t" + "价格");
        for(Hotel htl: Suites) {  // 查询套房
            Suite htl1 = (Suite)htl;
            System.out.println(htl1.getType() + "\t\t" + htl1.getId() + "\t\t" + htl1.getPrice());
        }
    }
    public static void queryOrder(List<Hotel>customerBigBedRoom, List<Hotel>customerDoubleBedsRoom, List<Hotel>customerSuite) {
        // 查询当前用户订单
        System.out.println("*********当前用户订单*********");
        System.out.println("具体类型\t\t\t" + "编号\t\t" + "预订天数\t\t" + "价格");
        for(Hotel htl: customerBigBedRoom) {
            BigBedRoom htl1 = (BigBedRoom)htl;
            System.out.println(htl1.getType() + "\t\t" + htl1.getId() + "\t\t" + htl1.getDays() +"\t\t" + htl1.getPayMoney(htl1.getDays()));
        }
        for(Hotel htl: customerDoubleBedsRoom) {
            DoubleBedsRoom htl1 = (DoubleBedsRoom)htl;
            System.out.println(htl1.getType() + "\t\t" + htl1.getId() + "\t\t" + htl1.getDays() +"\t\t" + htl1.getPayMoney(htl1.getDays()));
        }
        for(Hotel htl: customerSuite) {
            Suite htl1 = (Suite)htl;
            System.out.println(htl1.getType() + "\t\t" + htl1.getId() + "\t\t" + htl1.getDays() +"\t\t" + htl1.getPayMoney(htl1.getDays()));
        }
    }
    public static void removeBigBedRoom(Hotel BigBedRoom) {
        for (int i = 0; i < BigBedRooms.size(); i++) {
            if (BigBedRooms.get(i) == BigBedRoom) {
                BigBedRooms.remove(i);
                break;
            }
        }
    }
    public static void removeDoubleBedsRoom(Hotel BigBedRoom) {
        for (int i = 0; i < DoubleBedsRooms.size(); i++) {
            if (DoubleBedsRooms.get(i) == BigBedRoom) {
                DoubleBedsRooms.remove(i);
                break;
            }
        }
    }
    public static void removeSuite(Hotel BigBedRoom) {
        for(int i=0;i<Suites.size();i++) {
            if(Suites.get(i)==BigBedRoom) {
                Suites.remove(i);
                break;
            }
        }
    }
}
