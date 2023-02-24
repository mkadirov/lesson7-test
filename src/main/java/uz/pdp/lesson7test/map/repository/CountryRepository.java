package uz.pdp.lesson7test.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7test.map.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
