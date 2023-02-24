package uz.pdp.lesson7test.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7test.map.entity.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {
}
