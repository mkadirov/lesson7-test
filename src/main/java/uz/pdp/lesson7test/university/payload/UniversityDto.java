package uz.pdp.lesson7test.university.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityDto {

    private String name;

    private String street;

    private String houseNumber;

    private int districtId;
}
