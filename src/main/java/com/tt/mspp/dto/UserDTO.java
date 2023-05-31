package com.tt.mspp.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String u_id;
    private String u_pw;
    private String u_name;
    private String u_nick;
    private String u_phone;
    private String u_path;

}
