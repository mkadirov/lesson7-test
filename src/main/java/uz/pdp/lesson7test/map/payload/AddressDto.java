package uz.pdp.lesson7test.map.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String street;
    private String houseNumber;

    private int districtId;
}
