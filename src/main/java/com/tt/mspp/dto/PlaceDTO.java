package com.tt.mspp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {

    private String p_index;
    private String p_address;
    private String p_name;
    private String p_reserve;
    private int p_type;
    private String p_phone;

}
