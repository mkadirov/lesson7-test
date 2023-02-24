package uz.pdp.lesson7test.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7test.university.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
