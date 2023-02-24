package uz.pdp.lesson7test.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.Country;
import uz.pdp.lesson7test.map.entity.Region;
import uz.pdp.lesson7test.map.payload.RegionDto;
import uz.pdp.lesson7test.map.repository.CountryRepository;
import uz.pdp.lesson7test.map.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;

    @GetMapping
    public List<Region> getRegionsList(){
        return regionRepository.findAll();
    }

    @PostMapping
    public String addRegion(@RequestBody RegionDto regionDto){
        boolean isAdded = false;
        Region region = new Region();
        Optional<Country> optionalCountry = countryRepository.findById(regionDto.getCountryId());
        if (optionalCountry.isPresent()){
            region.setName(regionDto.getName());
            region.setCountry(optionalCountry.get());
            regionRepository.save(region);
            isAdded = true;
        }
        return isAdded? "Successfully added":"There isn't Country with ID:" + regionDto.getCountryId();
    }

    @GetMapping("/{id}")
    public Region getRegionById(@PathVariable Integer id){
        Optional<Region> optionalRegion = regionRepository.findById(id);
        return optionalRegion.orElseGet(Region::new);
    }

    @PutMapping("/{id}")
    public String editRegion(@PathVariable Integer id, @RequestBody RegionDto regionDto){
        boolean isEdited = false;
        Optional<Region> optionalRegion = regionRepository.findById(id);
        Optional<Country> optionalCountry = countryRepository.findById(regionDto.getCountryId());
        if (optionalRegion.isPresent() && optionalCountry.isPresent()){
            Region region = optionalRegion.get();
            region.setCountry(optionalCountry.get());
            region.setName(regionDto.getName());
            regionRepository.save(region);
            isEdited = true;
        }
        return isEdited? "Successfully edited":"There isn't country or region with this ID";
    }

    @DeleteMapping("/{id}")
    public String deleteRegion(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (optionalRegion.isPresent()){
            regionRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted":"There isn't region with this ID:"+id;
    }
}
