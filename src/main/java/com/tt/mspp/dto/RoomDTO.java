package com.tt.mspp.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    public String r_index;
    public String r_id;
    public String r_day;
    public String r_stime;
    public String r_etime;
    public String r_place;
    public int r_max;
    public String r_discribe;
    public String r_ok;
    public int r_p_index;

}
