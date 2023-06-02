package com.tt.mspp.dao;
import com.tt.mspp.dto.MatchDTO;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MatchDAO {

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("클래스 로드 실패 : " + e.getMessage());
        }
    }

    private static MatchDAO uniqueInstance = new MatchDAO();

    public static MatchDAO getInstance() {
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

    public List<MatchDTO> getMatchList() {
        List<MatchDTO> list = null;

        String sql = "SELECT m_r_index, COUNT(*) AS count FROM MATCH_DB GROUP BY m_r_index";;
        if (connect()) {
            try {
                stmt = con.createStatement();
                if (stmt != null) {
                    rs = stmt.executeQuery(sql);
                    list = new ArrayList<MatchDTO>();
                    while (rs.next()) {
                        MatchDTO match = new MatchDTO();

                        match.setM_r_index(rs.getString("m_r_index"));
                        match.setCount(rs.getInt("count"));
                        list.add(match);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();}
        } else {
            System.out.println("데이터베이스 연결에 실패했습니다.");
            System.exit(0);
        }
        return list;
    }

    public boolean insertMatch(String r_index, String id) {
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "INSERT INTO MATCH_DB VALUES (match_sequence.NEXTVAL,?,?)"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                pstmt.setString(1, r_index);
                pstmt.setString(2, id);
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
