package utils;
import type.*;
import java.util.*;
import java.time.LocalDate;

public class UserSystem {
    public static List<Hotel> customerBigBedRooms = new ArrayList<>();  // 用户列表
    public static List<Hotel> customerDoubleBedsRooms = new ArrayList<>();
    public static List<Hotel> customerSuites = new ArrayList<>();
    public static void User(List<Hotel> BigBedRooms, List<Hotel> DoubleBedsRooms, List<Hotel> Suites, Scanner user) {  // 用户交互方法
        while(true) {
            System.out.println("*********民宿客房价格表*********");
            System.out.println("1-预订房间");
            System.out.println("2-查询当前房间信息");
            System.out.println("3-查看订单");
            System.out.println("4-返回主页面");
            System.out.println("******************************");
            System.out.println("请输入您的操作号码:");
            Scanner scanner = new Scanner(System.in);
            int num = 0;
            int cnt = 0;
            while(!scanner.hasNextInt()) {
                cnt++;
                if(cnt==3){
                    System.out.println("您已连续输错三次，请注意输入要求");
                }
                System.out.println("输入无效，请重新输入一个整数：");
                scanner.next(); // 清空Scanner缓存，防止无限循环
            }
            cnt = 0;
            num = scanner.nextInt();
            while(num>4||num<1) {
                cnt++;
                if(cnt==3){
                    System.out.println("您已连续输错三次，请注意输入要求");
                }
                System.out.println("请输入有效范围内的数字：");
                num = scanner.nextInt();
            }
            if(num==1) {
                bookRoom(BigBedRooms, DoubleBedsRooms, Suites, user);
            }
            else if(num==2) {
                ManageSystem.queryRoom();
            }
            else if(num==3){
                ManageSystem.queryOrder(customerBigBedRooms, customerDoubleBedsRooms, customerSuites);
            }
            else {
                ManageSystem.show();  // 返回主界面
                System.exit(0);
            }
        }
    }

    private static void bookRoom(List<Hotel> BigBedRooms, List<Hotel> DoubleBedsRooms, List<Hotel> Suites, Scanner user) {
        ManageSystem.queryRoom();  // 打印管理系统所有现存房间
        int userIn = 0;
        userIn= ManageSystem.checkRoom(user);  // 提示用户输入房间类型并检测合法
        String userType = "";
        int id = 0;
        boolean breakfast = false;
        if(userIn==1) {
            for (Hotel htl : BigBedRooms) {
                BigBedRoom bigBedRoom = (BigBedRoom) htl;
                System.out.println("房间类型:" + bigBedRoom.getName() + "-" + bigBedRoom.getId());
            }
            System.out.println("请输入房型编号：");
            while(!user.hasNextInt()) {
                System.out.println("输入无效，请重新输入一个整数：");
                user.next(); // 清空Scanner缓存，防止无限循环
            }
            id = user.nextInt();
            while(id>3||id<1) {
                System.out.println("没有符合条件的大床房！请重新输入：");
                id = user.nextInt();
            }
            double price = 0.0;
            if(id==1) {
                userType = "高级大床房";
                price = 600;
            }
            else if(id==2) {
                userType = "豪华大床房";
                price = 700;
            }
            else if(id==3) {
                userType = "景观大床房";
                price = 850;
            }
            int cnt = 0;
            System.out.println("请输入是否需要早餐（需要请输入true，否则请输入false）:");
            while (true) {
                String input = user.next();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    breakfast = Boolean.parseBoolean(input);
                    break;
                } else {
                    cnt++;
                    if(cnt==3){
                        System.out.println("您已连续输错三次，请注意输入要求");
                    }
                    System.out.println("输入不合法，请重新输入（true/false）：");
                }
            }
            Hotel bigBedRoom = new BigBedRoom(userType, id, price);
            bigBedRoom.setBreakfast(breakfast);
            if(bigBedRoom.getId()==0) {
                System.out.println("找不到该房型。");
            }
            else {
                System.out.println("请输入入住日期在几天之后（仅可预订7天以内办理入住）:");
                int dateAfter = 0;
                while(!user.hasNextInt()) {
                    System.out.println("请输入有效的整数：");
                    user.next();
                }
                //输入入住和退房日期等相关信息
                dateAfter = user.nextInt();
                LocalDate currentDate = LocalDate.now();
                LocalDate checkInDate = currentDate.plusDays(dateAfter); // 入住日期为当前日期的后一天
                // 验证入住日期是否在允许的范围内（7天之内）
                if (checkInDate.isBefore(currentDate) || checkInDate.isAfter(currentDate.plusDays(7))) {
                    System.out.println("入住日期超出范围，请重新预订。");
                }
                else {
                    bigBedRoom.setCheckInDate(checkInDate);
                    System.out.println("请输入预订天数:");
                    int days = 0;
                    days= user.nextInt();
                    bigBedRoom.setDays(days);
                    LocalDate checkOutDate = checkInDate.plusDays(days);
                    bigBedRoom.setCheckOutDate(checkOutDate);  // 时间已设定
                    if (!isRoomAvailable(customerBigBedRooms, userType, checkInDate, checkOutDate)) {
                        System.out.println("该类型房间在选择的日期区间已满，请选择其他房型或日期！");
                        return;
                    }
                    //判断日期重叠
                    customerBigBedRooms.add(bigBedRoom);
                    ManageSystem.removeBigBedRoom(bigBedRoom);
                    System.out.println("预订成功。您租赁的是：" + bigBedRoom.getName() + "。是否包含早餐:" + breakfast);
                    System.out.println("入住日期：" + bigBedRoom.getCheckInDate() + "，退房日期：" + bigBedRoom.getCheckOutDate());
                    System.out.println("共租赁" + bigBedRoom.getDays() + "天，您的预订费用为:" + bigBedRoom.getPayMoney(days));
                    for (Hotel htl : BigBedRooms) {
                        bigBedRoom = htl;
                        if (bigBedRoom.getId() == id) {
                            bigBedRoom.setOccupied(true);
                            break;
                        }
                    }
                }
            }
        }
        if(userIn==2) {
            for (Hotel htl : DoubleBedsRooms) {
                DoubleBedsRoom doubleBedsRoom = (DoubleBedsRoom) htl;
                System.out.println("房间类型:" + doubleBedsRoom.getName() + "-" + doubleBedsRoom.getId());
            }
            System.out.println("请输入房型编号：");
            while(!user.hasNextInt()) {
                System.out.println("请输入有效的整数：");
                user.next();
            }
            id = user.nextInt();
            while(id>6||id<4) {
                System.out.println("没有符合条件的大床房！请重新输入：");
                id = user.nextInt();
            }
            double price = 0.0;
            if(id==4) {
                userType = "标准双床房";
                price = 600;
            }
            else if(id==5) {
                userType = "高级双床房";
                price = 800;
            }
            else if(id==6) {
                userType = "豪华双床房";
                price = 1000;
            }
            int cnt = 0;
            System.out.println("请输入是否需要早餐（需要请输入true，否则请输入false）:");
            while (true) {
                String input = "";
                input = user.next();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    breakfast = Boolean.parseBoolean(input);
                    break;
                }
                else {
                    cnt++;
                    if(cnt==3){
                        System.out.println("您已连续输错三次，请注意输入要求");
                    }
                    System.out.println("输入不合法，请重新输入（true/false）：");
                }
            }

            Hotel doubleBedsRoom = new DoubleBedsRoom(userType, id, price);
            doubleBedsRoom.setBreakfast(breakfast);
            if(doubleBedsRoom.getId()==0) {
                System.out.println("找不到该房型。");
            }else {
                System.out.println("请输入入住日期在几天之后（仅可预订7天以内办理入住）:");
                int dateAfter = 0;
                while(!user.hasNextInt()) {
                    System.out.println("请输入有效的整数：");
                    user.next();
                }
                dateAfter = user.nextInt();
                LocalDate currentDate = LocalDate.now();
                LocalDate checkInDate = currentDate.plusDays(dateAfter);
                if (checkInDate.isBefore(currentDate) || checkInDate.isAfter(currentDate.plusDays(7))) {
                    System.out.println("入住日期超出范围，请重新预订。");
                } else {
                    doubleBedsRoom.setCheckInDate(checkInDate);

                    System.out.println("请输入预订天数:");
                    int days = 0;
                    while(!user.hasNextInt()) {
                        System.out.println("请输入有效的整数：");
                        user.next();
                    }
                    days = user.nextInt();
                    doubleBedsRoom.setDays(days);
                    LocalDate checkOutDate = checkInDate.plusDays(days);
                    doubleBedsRoom.setCheckOutDate(checkOutDate);
                    if (!isRoomAvailable(customerDoubleBedsRooms, userType, checkInDate, checkOutDate)) {
                        System.out.println("该类型房间在选择的日期区间已满，请选择其他房型或日期！");
                        return;
                    }
                    doubleBedsRoom.setPayMoney(doubleBedsRoom.getPayMoney(days));
                    customerDoubleBedsRooms.add(doubleBedsRoom);
                    ManageSystem.removeDoubleBedsRoom(doubleBedsRoom);
                    System.out.println("预订成功。您租赁的是：" + doubleBedsRoom.getName() + "。是否包含早餐:" + breakfast);
                    System.out.println("入住日期：" + doubleBedsRoom.getCheckInDate() + "，退房日期：" + doubleBedsRoom.getCheckOutDate());
                    System.out.println("共租赁" + doubleBedsRoom.getDays() + "天，您的预订费用为:" + doubleBedsRoom.getPayMoney(days));
                    for (Hotel htl : DoubleBedsRooms) {
                        doubleBedsRoom = (DoubleBedsRoom) htl;
                        if (doubleBedsRoom.getId() == id) {
                            doubleBedsRoom.setOccupied(true);
                            break;
                        }
                    }
                }
            }
        }
        if(userIn==3) {
            for (Hotel htl : Suites) {
                Suite suites = (Suite) htl;
                System.out.println("房间类型:" + suites.getName() + "-" + suites.getId());
            }
            System.out.println("请输入房型编号：");
            while(!user.hasNextInt()) {
                System.out.println("请输入有效的整数：");
                user.next();
            }
            id = user.nextInt();
            while(id>10||id<7) {
                System.out.println("没有符合条件的大床房！请重新输入：");
                id = user.nextInt();
            }
            double price = 0.0;
            if(id==7) {
                userType = "景致小套房";
                price = 1500;
            }
            else if(id==8) {
                userType = "婴儿小套房";
                price = 2000;
            }
            else if(id==9) {
                userType = "亲子大套房";
                price = 2500;
            }
            int cnt = 0;
            System.out.println("请输入是否需要早餐（需要请输入true，否则请输入false）:");
            while (true) {
                String input = user.next();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    breakfast = Boolean.parseBoolean(input);
                    break;
                }
                else {
                    cnt++;
                    if(cnt==3){
                        System.out.println("您已连续输错三次，请注意输入要求");
                    }
                    System.out.println("输入不合法，请重新输入（true/false）：");
                }
            }
            Hotel suite = new Suite(userType, id, price);
            suite.setBreakfast(breakfast);
            if(suite.getId()==0) {
                System.out.println("找不到该房型。");
            }
            else {
                System.out.println("请输入入住日期在几天之后（仅可预订7天以内办理入住）:");
                int dateAfter = 0;
                while(!user.hasNextInt()) {
                    System.out.println("请输入有效的数字：");
                    user.next();
                }
                dateAfter = user.nextInt();
                LocalDate currentDate = LocalDate.now();
                LocalDate checkInDate = currentDate.plusDays(dateAfter);
                // 验证入住日期是否在允许的范围内（7天之内）
                if (checkInDate.isBefore(currentDate) || checkInDate.isAfter(currentDate.plusDays(7))) {
                    System.out.println("入住日期超出范围，请重新预订。");
                }
                else {
                    suite.setCheckInDate(checkInDate);
                    System.out.println("请输入预订天数:");
                    int days = 0;
                    while(!user.hasNextInt()) {
                        System.out.println("请输入有效的整数：");
                        user.next();
                    }
                    days = user.nextInt();
                    suite.setDays(days);
                    LocalDate checkOutDate = checkInDate.plusDays(days);
                    suite.setCheckOutDate(checkOutDate);
                    if (!isSuiteAvailable(customerSuites, userType, checkInDate, checkOutDate)) { //
                        System.out.println("该类型房间在选择的日期区间已满，请选择其他房型或日期！");
                        return;
                    }
                    suite.setPayMoney(suite.getPayMoney(days));
                    customerSuites.add(suite);
                    ManageSystem.removeSuite(suite);
                    System.out.println("预订成功。您租赁的是：" + suite.getName() + "。是否包含早餐:" + breakfast);
                    System.out.println("入住日期：" + suite.getCheckInDate() + "，退房日期：" + suite.getCheckOutDate());
                    System.out.println("共租赁" + suite.getDays() + "天，您的预订费用为:" + suite.getPayMoney(days));
                    for (Hotel htl : Suites) {
                        suite = (Suite) htl;
                        if (suite.getId() == id) {
                            suite.setOccupied(true);
                            break;
                        }
                    }
                }

            }
        }
    }

    private static Boolean isRoomAvailable(List<Hotel> bookedRooms, String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        int cnt = 2;

        for (Hotel bookedRoom : bookedRooms) {
            if (bookedRoom.getName().equals(roomType) && isDateRangeOverlapping(bookedRoom.getCheckInDate(), bookedRoom.getCheckOutDate(), checkInDate, checkOutDate)) {  // 未成立
                cnt--;
            }
            if(cnt == 0) {
                return false;
            }
        }
        return true;
    }
    private static Boolean isSuiteAvailable(List<Hotel> bookedRooms, String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        int cnt = 1;
        for (Hotel bookedRoom : bookedRooms) {
            if (bookedRoom.getName().equals(roomType) && isDateRangeOverlapping(bookedRoom.getCheckInDate(), bookedRoom.getCheckOutDate(), checkInDate, checkOutDate)) {  // 未成立
                cnt--;
            }
            if(cnt == 0) {
                return false;
            }
        }
        return true;
    }
    private static boolean isDateRangeOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !end1.isBefore(start2) || !end2.isBefore(start1) || (end1.equals(end2) || start1.equals(start2));
    }

}
