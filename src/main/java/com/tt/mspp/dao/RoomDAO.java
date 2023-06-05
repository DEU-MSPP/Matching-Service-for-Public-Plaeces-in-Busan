package com.tt.mspp.dao;

import com.tt.mspp.dto.PlaceDTO;
import com.tt.mspp.dto.RoomDTO;
import com.tt.mspp.dto.UserDTO;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class RoomDAO {

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

    private static RoomDAO uniqueInstance = new RoomDAO();

    public static RoomDAO getInstance() {
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

    public List<RoomDTO> getRoomList() {
        List<RoomDTO> list = null;

        String sql = "SELECT * FROM ROOM_DB WHERE R_OK = '0' ORDER BY R_INDEX DESC";
        if (connect()) {
            try {
                stmt = con.createStatement();
                if (stmt != null) {
                    rs = stmt.executeQuery(sql);
                    list = new ArrayList<RoomDTO>();
                    while (rs.next()) {
                        RoomDTO room = new RoomDTO();

                        room.setR_index(rs.getString("r_index"));
                        room.setR_id(rs.getString("r_id"));
                        room.setR_day(rs.getString("r_day"));
                        room.setR_stime(rs.getString("r_stime"));
                        room.setR_etime(rs.getString("r_etime"));
                        room.setR_place(rs.getString("r_place"));
                        room.setR_max(rs.getInt("r_max"));
                        room.setR_discribe(rs.getString("r_discribe"));
                        room.setR_ok(rs.getString("r_ok"));
                        room.setR_p_index(rs.getInt("r_p_index"));
                        list.add(room);
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

    public boolean insertRoom(String id, String day, String stime, String etime, String place, int max, String discribe, int p_index) {
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "INSERT INTO ROOM_DB VALUES (room_sequence.NEXTVAL,?,?,?,?,?,?,?,'0',?)"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                pstmt.setString(1, id);
                pstmt.setString(2, day);
                pstmt.setString(3, stime);
                pstmt.setString(4, etime);
                pstmt.setString(5, place);
                pstmt.setInt(6, max);
                pstmt.setString(7, discribe);
                pstmt.setInt(8, p_index);
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

    public boolean deleteRoom(String r_index) {
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "DELETE FROM ROOM_DB CASCADE WHERE R_INDEX = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, r_index);
                int r = pstmt.executeUpdate();
                if (r > 0) {
                    result = true;
                }
                //데이터베이스 생성 객체 해제
                pstmt.close();
                this.close();
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
