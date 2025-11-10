package mvc_jdbc_test.controller;

import mvc_jdbc_test.entity.Customer;
import mvc_jdbc_test.view.CustomerView;
import mvc_jdbc_test.view.InputCustomerInfoView2;

import java.util.ArrayList;
import java.util.Scanner;

public class MainController2 {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== 고객 정보 관리 시스템 ===");

        while (true) {
            System.out.println("\n메뉴를 선택하세요:");
            System.out.println("1. 고객 정보 추가");
            System.out.println("2. 고객 정보 수정");
            System.out.println("3. 고객 정보 삭제");
            System.out.println("4. 전체 고객 목록 조회");
            System.out.println("5. 종료");
            System.out.print("선택: ");

            String choice = sc.nextLine().trim();

            if (choice.equals("5") || choice.equalsIgnoreCase("exit")) {
                break;
            }

            switch (choice) {
                case "1":
                    addCustomer();
                    break;
                case "2":
                    updateCustomer();
                    break;
                case "3":
                    deleteCustomer();
                    break;
                case "4":
                    viewAllCustomers();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }

        System.out.println("\n프로그램을 종료합니다.");
        sc.close();
    }

    private static void addCustomer() {
        while (true) {
            Customer c = InputCustomerInfoView2.inputCustomer();
            if (c == null) break;

            boolean result = CustomerController.insertCustomer(c);

            if (result) {
                System.out.println("\n고객 정보가 저장되었습니다.");
                System.out.println("\n[저장된 정보]");
                CustomerView view = new CustomerView();
                view.printCustomer(c);
            } else {
                System.out.println("\n저장에 실패했습니다.");
            }

            if (!InputCustomerInfoView2.confirm("\n계속 추가하시겠습니까? (y/n): ")) {
                break;
            }
        }
    }

    private static void updateCustomer() {
        while (true) {
            System.out.println("\n---------- 고객 정보 수정 ----------");
            String id = InputCustomerInfoView2.inputCustomerId("고객 아이디: ");

            if (id.equalsIgnoreCase("exit")) break;

            Customer c = CustomerController.getCustomerById(id);

            if (c == null) {
                System.out.println("해당 고객이 존재하지 않습니다.\n");
                continue;
            }

            System.out.println("\n[기존 고객 정보]");
            CustomerView view = new CustomerView();
            view.printCustomer(c);

            if (!InputCustomerInfoView2.confirm("\n이 고객 정보를 수정하시겠습니까? (y/n): ")) {
                continue;
            }

            System.out.println("\n[새로운 정보 입력]");
            Customer newC = InputCustomerInfoView2.inputCustomer();
            if (newC == null) break;

            newC.setCustomerid(id);

            System.out.println("\n[수정될 정보]");
            view.printCustomer(newC);

            if (InputCustomerInfoView2.confirm("\n위 내용으로 수정하시겠습니까? (y/n): ")) {
                boolean result = CustomerController.updateCustomer(newC);
                System.out.println(result ? "\n고객 정보가 수정되었습니다.\n" : "\n수정에 실패했습니다.\n");
            }

            if (!InputCustomerInfoView2.confirm("다른 고객을 수정하시겠습니까? (y/n): ")) {
                break;
            }
        }
    }

    private static void deleteCustomer() {
        while (true) {
            System.out.println("\n---------- 고객 정보 삭제 ----------");
            String id = InputCustomerInfoView2.inputCustomerId("고객 아이디: ");

            if (id.equalsIgnoreCase("exit")) break;

            Customer c = CustomerController.getCustomerById(id);

            if (c == null) {
                System.out.println("해당 고객이 존재하지 않습니다.\n");
                continue;
            }

            System.out.println("\n[삭제할 고객 정보]");
            CustomerView view = new CustomerView();
            view.printCustomer(c);

            if (InputCustomerInfoView2.confirm("\n정말 삭제하시겠습니까? (y/n): ")) {
                boolean result = CustomerController.deleteCustomer(id);
                if (result) {
                    System.out.println("\n고객 정보가 삭제되었습니다.");
                    System.out.println("삭제된 고객 ID: " + id + "\n");
                } else {
                    System.out.println("\n삭제에 실패했습니다.\n");
                }
            }

            if (!InputCustomerInfoView2.confirm("다른 고객을 삭제하시겠습니까? (y/n): ")) {
                break;
            }
        }
    }

    private static void viewAllCustomers() {
        ArrayList<Customer> list = CustomerController.getAllCustomers();

        if (list.isEmpty()) {
            System.out.println("\n등록된 고객이 없습니다.");
            return;
        }

        CustomerView view = new CustomerView();
        view.printHead();
        for (Customer c : list) {
            view.printCustomer(c);
            System.out.println();
        }
        view.printFooter();
    }
}