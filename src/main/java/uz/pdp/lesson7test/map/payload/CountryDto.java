package uz.pdp.lesson7test.map.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    private String name;
    private String capital;
    private int continentId;
}
