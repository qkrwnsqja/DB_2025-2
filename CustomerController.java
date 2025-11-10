package mvc_jdbc_test.controller;

import jdbc_test.JDBCConnector;
import mvc_jdbc_test.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {

    // 고객 추가
    public static boolean insertCustomer(Customer c) {
        Connection con = JDBCConnector.getConnection();
        String sql = "INSERT INTO 고객 (고객아이디, 고객이름, 나이, 등급, 직업, 적립금) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getCustomerid());
            ps.setString(2, c.getCustomername());
            ps.setInt(3, c.getAge());
            ps.setString(4, c.getLevel());
            ps.setString(5, c.getJob());
            ps.setInt(6, c.getReward());

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            return result > 0;

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    // 고객 수정
    public static boolean updateCustomer(Customer c) {
        Connection con = JDBCConnector.getConnection();
        String sql = "UPDATE 고객 SET 고객이름=?, 나이=?, 등급=?, 직업=?, 적립금=? WHERE 고객아이디=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getCustomername());
            ps.setInt(2, c.getAge());
            ps.setString(3, c.getLevel());
            ps.setString(4, c.getJob());
            ps.setInt(5, c.getReward());
            ps.setString(6, c.getCustomerid());

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            return result > 0;

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    // 고객 삭제 (주문도 함께)
    public static boolean deleteCustomer(String id) {
        Connection con = JDBCConnector.getConnection();

        try {
            con.setAutoCommit(false);

            // 주문 먼저 삭제
            String sql1 = "DELETE FROM 주문 WHERE 주문고객 = ?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setString(1, id);
            int orderCnt = ps1.executeUpdate();
            ps1.close();

            // 고객 삭제
            String sql2 = "DELETE FROM 고객 WHERE 고객아이디 = ?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, id);
            int result = ps2.executeUpdate();
            ps2.close();

            con.commit();
            con.close();

            if (orderCnt > 0) {
                System.out.println("(관련 주문 " + orderCnt + "건도 함께 삭제되었습니다.)");
            }

            return result > 0;

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    // ID로 고객 조회
    public static Customer getCustomerById(String id) {
        Connection con = JDBCConnector.getConnection();
        Customer c = null;

        try {
            String sql = "SELECT * FROM 고객 WHERE 고객아이디 = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Customer();
                c.setCustomerid(rs.getString("고객아이디"));
                c.setCustomername(rs.getString("고객이름"));
                c.setAge(rs.getInt("나이"));
                c.setLevel(rs.getString("등급"));
                c.setJob(rs.getString("직업"));
                c.setReward(rs.getInt("적립금"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }

        return c;
    }

    // 전체 고객 조회
    public static ArrayList<Customer> getAllCustomers() {
        Connection con = JDBCConnector.getConnection();
        ArrayList<Customer> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM 고객";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerid(rs.getString("고객아이디"));
                c.setCustomername(rs.getString("고객이름"));
                c.setAge(rs.getInt("나이"));
                c.setLevel(rs.getString("등급"));
                c.setJob(rs.getString("직업"));
                c.setReward(rs.getInt("적립금"));
                list.add(c);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }

        return list;
    }
}