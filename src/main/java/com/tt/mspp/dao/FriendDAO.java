package com.tt.mspp.dao;

import com.tt.mspp.dto.FriendDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO {

    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("클래스 로드 실패 : " + e.getMessage());
        }
    }

    private static FriendDAO uniqueInstance = new FriendDAO();

    public static FriendDAO getInstance() {
        return uniqueInstance;
    }

    private boolean connect() {
        boolean result = false;
        try {
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@1.255.169.22:1522:xe", "c##20183226", "20183226");
            result = true;
        } catch (Exception e) {
            System.out.println("연결 실패 : " + e.getMessage());
        }
        return result;
    }

    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("해제 실패 : " + e.getMessage());
        }
    }

    public List<FriendDTO> getFriendList(String id) {
        List<FriendDTO> list = null;

        String sql = "SELECT * FROM FRIEND_DB WHERE F_ID1 = ? or F_ID2 = ?";
        if (connect()) {
            try {
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, id);

                if (pstmt != null) {
                    rs = pstmt.executeQuery();
                    list = new ArrayList<FriendDTO>();
                    while (rs.next()) {
                        FriendDTO friend = new FriendDTO();

                        friend.setF_index(rs.getString("f_index"));
                        friend.setF_id1(rs.getString("f_id1"));
                        friend.setF_id2(rs.getString("f_id2"));
                        friend.setF_ok(rs.getString("f_ok"));
                        list.add(friend);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("데이터베이스 연결에 실패했습니다.");
            System.exit(0);
        }
        return list;
    }

    public boolean addFriend(String f_id1, String f_id2){
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "INSERT INTO FRIEND_DB VALUES (friend_sequence.NEXTVAL,?,?,0)"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                pstmt.setString(1, f_id1);
                pstmt.setString(2, f_id2);
                pstmt.executeUpdate();

                pstmt.close();
                this.close();
                result = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("데이터베이스 연결에 실패");
            System.exit(0);
        }
        return result;
    }

    public boolean deleteFriend(String f_id1, String f_id2){
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "DELETE FROM FRIEND_DB WHERE F_ID1 = ? and F_ID2 = ? and F_OK = 0"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                pstmt.setString(1, f_id1);
                pstmt.setString(2, f_id2);
                pstmt.executeUpdate();

                pstmt.close();
                this.close();
                result = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("데이터베이스 연결에 실패");
            System.exit(0);
        }
        return result;
    }

    public boolean acceptFriend(String f_id1, String f_id2){
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "UPDATE FRIEND_DB SET F_OK = '1' WHERE F_ID1 = ? and F_ID2 = ? and F_OK = 0"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                pstmt.setString(1, f_id1);
                pstmt.setString(2, f_id2);
                pstmt.executeUpdate();

                pstmt.close();
                this.close();
                result = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("데이터베이스 연결에 실패");
            System.exit(0);
        }
        return result;
    }

}
