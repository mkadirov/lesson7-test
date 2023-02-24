package uz.pdp.lesson7test.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7test.university.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
