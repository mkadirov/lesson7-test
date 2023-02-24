package uz.pdp.lesson7test.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.District;
import uz.pdp.lesson7test.map.entity.Region;
import uz.pdp.lesson7test.map.payload.DistrictDto;
import uz.pdp.lesson7test.map.repository.DistrictRepository;
import uz.pdp.lesson7test.map.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    RegionRepository regionRepository;

    @GetMapping
    public List<District> getDistrictList(){
        return districtRepository.findAll();
    }

    @PostMapping
    public String addDistrict(@RequestBody DistrictDto districtDto){
        boolean isAdded = false;
        Optional<Region> optionalRegion = regionRepository.findById(districtDto.getRegionId());

        if (optionalRegion.isPresent()){
            District district = new District();
            district.setName(districtDto.getName());
            district.setRegion(optionalRegion.get());
            districtRepository.save(district);
            isAdded = true;
        }
        return isAdded? "Successfully added":"There isn't region with id:"+ districtDto.getRegionId();
    }

    @GetMapping("/{id}")
    public District getDistrictById(@PathVariable Integer id){
        Optional<District> optionalDistrict = districtRepository.findById(id);
        return optionalDistrict.orElseGet(District::new);
    }

    @PutMapping("/{id}")
    public String editDistrict(@PathVariable Integer id, @RequestBody DistrictDto districtDto){
        boolean isEdited = false;
        Optional<District> optionalDistrict = districtRepository.findById(id);
        Optional<Region> optionalRegion = regionRepository.findById(districtDto.getRegionId());
        if (optionalDistrict.isPresent() && optionalRegion.isPresent()){
            District district = optionalDistrict.get();
            district.setName(districtDto.getName());
            district.setRegion(optionalRegion.get());
            districtRepository.save(district);
            isEdited=true;
        }
        return isEdited? "Successfully edited": "There isn't region or district with  this id";
    }

    @DeleteMapping("/{id}")
    public String deleteDistrict(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isPresent()){
            districtRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted":"There isn't District by ID:"+id;
    }
}
