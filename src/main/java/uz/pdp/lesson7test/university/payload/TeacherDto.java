package uz.pdp.lesson7test.university.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private String firstName;
    private String lastName;

    private List<Integer> subjectIdList;
}
