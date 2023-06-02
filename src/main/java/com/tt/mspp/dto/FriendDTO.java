package com.tt.mspp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {

    String f_index;     //기본키
    String f_id1;       //수락한 사용자
    String f_id2;       //신청한 사용자
    String f_ok;       //친구 수락 여부  0이면 미수락 1이면 수락

}
