package uz.pdp.lesson7test.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.Address;
import uz.pdp.lesson7test.map.entity.District;
import uz.pdp.lesson7test.map.repository.AddressRepository;
import uz.pdp.lesson7test.map.repository.DistrictRepository;
import uz.pdp.lesson7test.university.entity.University;
import uz.pdp.lesson7test.university.payload.UniversityDto;
import uz.pdp.lesson7test.university.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;

    @GetMapping
    public List<University> universityList(){
        return universityRepository.findAll();
    }

    @PostMapping
    public String addUniversity(@RequestBody UniversityDto universityDto){
        Optional<District> optionalDistrict = districtRepository.findById(universityDto.getDistrictId());
        District district;
        if (optionalDistrict.isPresent()){
            district= optionalDistrict.get();
        }else {
            return "There isn't District with this id";
        }
        Address address = new Address(null, universityDto.getStreet(),
                universityDto.getHouseNumber(), district);
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(addressRepository.save(address));
        universityRepository.save(university);
        return "Successfully added";
    }

    @GetMapping("/{id}")
    public University getUniById(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        return optionalUniversity.orElseGet(University::new);
    }

    @PutMapping("/{id}")
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        Optional<District> optionalDistrict = districtRepository.findById(universityDto.getDistrictId());
        boolean isEdited = false;
        if(optionalUniversity.isPresent()){
            University university = optionalUniversity.get();
            Address address = university.getAddress();
            if(optionalDistrict.isPresent()) {
                address.setDistrict(optionalDistrict.get());
            }else {
                return "There isn't District with this id";
            }
            address.setStreet(universityDto.getStreet());
            address.setHouseNumber(universityDto.getHouseNumber());
            university.setName(universityDto.getName());
            university.setAddress(address);
            addressRepository.save(address);
            universityRepository.save(university);
            isEdited = true;
        }
        return isEdited? "Successfully edited": "There isn't University with ID:" +id;
    }

    @DeleteMapping("/{id}")
    public String deleteUniversity(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        boolean isDeleted = false;
        if(optionalUniversity.isPresent()){
            universityRepository.deleteById(id);
            isDeleted=true;
        }
        return isDeleted? "Successfully deleted":"There isn't University with ID:" +id;
    }
}
