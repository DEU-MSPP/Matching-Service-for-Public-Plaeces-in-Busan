package com.tt.mspp.dao;

import com.tt.mspp.dto.CommentDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
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

    private static CommentDAO uniqueInstance = new CommentDAO();

    public static CommentDAO getInstance() {
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

    public List<CommentDTO> getCommentList(String r_index) {
        // 데이터를 저장할 변수 생성.
        //(여기서 객체 생성을 안하는 이유는 접속이 되었을 때만 객체생성을 해야 의미가 있기 때문에.
        //접속 안됬는데 객체를 생성한다면 이 DB에 데이터를 넣을수 있는지 없는지 판단할 기준이 애매해짐.
        List<CommentDTO> list = null;

        // 수행할 sql문장을 생성.
        String sql = "SELECT * FROM COMMENT_DB WHERE C_R_INDEX= "+r_index +" ORDER BY C_INDEX DESC";
        // 데이터베이스에 연결이 되었을때만 Select문 실행.
        if (connect()) {

            // 연결에 성공했을 때 작업
            try {
                //Connection객체에 연결된(DB에 연결된) Statement 객체를 생성하여 Statement변수에 대입.
                stmt = con.createStatement();
                if (stmt != null) { //위 객체가 Null이 아니라는 것은 무언가를 받아왔다는 의미. SQL문장을 받아온 것.
                    //sql구문 실행 (Select문의 결과는 ResultSet에 저장. (위에서 선언))
                    rs = stmt.executeQuery(sql);
                    //데이터를 저장할 리스트 생성
                    list = new ArrayList<CommentDTO>();

                    //데이터를 읽어서 list에 저장
                    while (rs.next()) {
                        //DTO 클래스의 객체 생성. (모든 데이터가 DTO클래스에 들어있으므로)
                        CommentDTO comment = new CommentDTO();
                        //DTO클래스의 변수에 값을 세팅하기 위해 Set메서드를 이용하고.
                        //Select의 결과를 컬럼 단위로 읽어오기 위해서 'get변수타입(컬럼명)' 메서드를 이용

                        comment.setC_index(rs.getString("c_index"));
                        comment.setC_r_index(rs.getString("c_r_index"));
                        comment.setC_id(rs.getString("c_id"));
                        comment.setC_context(rs.getString("c_context"));
                        comment.setC_date(rs.getString("c_date"));
                        list.add(comment);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            // 연결에 실패했을 때 작업
            System.out.println("데이터베이스 연결에 실패했습니다.");
            System.exit(0);
        }
        return list;
    }

    public boolean insertComment(String c_r_index, String c_id, String c_context) {
        boolean result = false;
        int r;
        if (this.connect()) {
            try {
                //값이 삽입되어야 하는 자리에는 물음표
                String sql = "INSERT INTO COMMENT_DB VALUES (comment_sequence.nextval,?,?,?,?)"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                //VALUES의 ?에 값을 바인딩. (바인딩 : ?에 들어갔어야 하는 원래 데이터 값을 입력.
                //바인딩 방법. set자료형(컬럼, 들어갈 데이터);
                LocalDate currentDate = LocalDate.now();
                String dateString = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                pstmt.setString(1, c_r_index);
                pstmt.setString(2, c_id);
                pstmt.setString(3, c_context);
                pstmt.setString(4, dateString);
                r = pstmt.executeUpdate();
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

    public boolean deleteComment(String c_index){
        boolean result = false;
        if (this.connect()) {
            try {
                String sql = "DELETE FROM COMMENT_DB WHERE c_index = ?"; //모든 컬럼에 값을 넣으므로 컬럼명 생략.
                PreparedStatement pstmt = con.prepareStatement(sql);

                pstmt.setString(1, c_index);
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
