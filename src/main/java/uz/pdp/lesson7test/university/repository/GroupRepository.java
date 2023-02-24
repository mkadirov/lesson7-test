package uz.pdp.lesson7test.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7test.university.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
