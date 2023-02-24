package uz.pdp.lesson7test.university.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lesson7test.university.entity.Subject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private String firstName;
    private String lastName;

    private int groupId;

    private String street;
    private String houseNumber;
    private int districtId;
    private List<Integer> subjectsIdList;
}
