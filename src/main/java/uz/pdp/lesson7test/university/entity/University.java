package uz.pdp.lesson7test.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lesson7test.map.entity.Address;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Address address;
}
