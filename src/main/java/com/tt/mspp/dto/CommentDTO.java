package com.tt.mspp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    String c_index;             //기본키
    String c_r_index;           //매치 iD 무슨 방인지
    String c_id;                //댓글 작성자 id
    String c_context;           //댓글 작성 내용
    String c_date;              //작성 날짜
}
