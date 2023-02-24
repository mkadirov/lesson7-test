package uz.pdp.lesson7test.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7test.university.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
