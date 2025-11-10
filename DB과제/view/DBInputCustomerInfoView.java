package mvc_jdbc_test.view;

import mvc_jdbc_test.entity.Customer;
import java.util.Scanner;

public class InputCustomerInfoView2 {
    private static Scanner sc = new Scanner(System.in);

    public static Customer inputCustomer() {
        System.out.println("\n--- 고객 정보 입력 ---");

        Customer c = new Customer();

        try {
            System.out.print("고객 아이디: ");
            String id = sc.nextLine().trim();
            if (id.equalsIgnoreCase("exit")) {
                return null;
            }
            c.setCustomerid(id);

            System.out.print("고객 이름: ");
            String name = sc.nextLine().trim();
            if (name.equalsIgnoreCase("exit")) {
                return null;
            }
            c.setCustomername(name);

            System.out.print("나이: ");
            String age = sc.nextLine().trim();
            if (age.equalsIgnoreCase("exit")) {
                return null;
            }
            c.setAge(Integer.parseInt(age));

            System.out.print("등급: ");
            String level = sc.nextLine().trim();
            if (level.equalsIgnoreCase("exit")) {
                return null;
            }
            c.setLevel(level);

            System.out.print("직업: ");
            String job = sc.nextLine().trim();
            if (job.equalsIgnoreCase("exit")) {
                return null;
            }
            c.setJob(job);

            System.out.print("적립금: ");
            String reward = sc.nextLine().trim();
            if (reward.equalsIgnoreCase("exit")) {
                return null;
            }
            c.setReward(Integer.parseInt(reward));

            return c;

        } catch (NumberFormatException e) {
            System.out.println("숫자 입력이 잘못되었습니다.");
            return inputCustomer();
        }
    }

    public static String inputCustomerId(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    public static boolean confirm(String message) {
        System.out.print(message);
        String answer = sc.nextLine().trim().toLowerCase();
        return answer.equals("y") || answer.equals("yes");
    }
}