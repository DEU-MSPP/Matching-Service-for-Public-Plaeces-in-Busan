package com.tt.mspp.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    @Getter@Setter
    private String p_index;
    @Getter@Setter
    private String p_address;
    @Getter@Setter
    private String p_name;
    @Getter@Setter
    private String p_reserve;
    @Getter@Setter
    private int p_type;
    @Getter@Setter
    private String p_phone;

    public PlaceDTO() {
        //super();
    }

    public PlaceDTO(String p_index, String p_address, String p_name, String p_reserve, int p_type, String p_phone) {
        //super();
        this.p_index = p_index;
        this.p_address = p_address;
        this.p_name = p_name;
        this.p_reserve = p_reserve;
        this.p_type = p_type;
        this.p_phone = p_phone;
    }
}
