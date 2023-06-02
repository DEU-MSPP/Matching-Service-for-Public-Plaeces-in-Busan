package com.tt.mspp.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    private String r_index;
    private String r_id;
    private String r_day;
    private String r_stime;
    private String r_etime;
    private String r_place;
    private int r_max;
    private String r_discribe;
    private String r_ok;
    private int r_p_index;
    private int count=0;
}
