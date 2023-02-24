package uz.pdp.lesson7test.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.Continent;
import uz.pdp.lesson7test.map.entity.Country;
import uz.pdp.lesson7test.map.payload.CountryDto;
import uz.pdp.lesson7test.map.repository.ContinentRepository;
import uz.pdp.lesson7test.map.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    ContinentRepository continentRepository;

    @GetMapping
    public List<Country> getCountryList(){
        return countryRepository.findAll();
    }

    @PostMapping
    public String addCountry(@RequestBody CountryDto countryDto) {
        Country newCountry = new Country();
        List<Country> countries = countryRepository.findAll();
        if (!countries.isEmpty()){
            for (Country country : countries) {
                if (country.getName().equals(countryDto.getName())) {
                    return "this country exists";
                }
            }
        }
        newCountry.setName(countryDto.getName());
        Optional<Continent> optionalContinent = continentRepository.findById(countryDto.getContinentId());
        if(optionalContinent.isPresent()){
             newCountry.setContinent(optionalContinent.get());
        }else {
            return "There isn't any Continent with ID:" + countryDto.getContinentId();
        }
        countryRepository.save(newCountry);
        return "Successfully added";
    }

    @GetMapping("/{id}")
    public Country getCountrById(@PathVariable Integer id){
        Optional<Country> optionalCountry = countryRepository.findById(id);
        return optionalCountry.orElseGet(Country::new);
    }

    @PutMapping("/{id}")
    public String editCountry(@PathVariable Integer id, @RequestBody CountryDto countryDto){
        boolean isEdited = false;
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isPresent()){
            Country country = optionalCountry.get();
            if(!country.getName().equals(countryDto.getName())) {
                for (Country c : countryRepository.findAll()) {
                    if (c.getName().equals(countryDto.getName())) {
                        return "this country exists";
                    }
                }
                country.setName(countryDto.getName());
            }
            Optional<Continent> optionalContinent = continentRepository.findById(countryDto.getContinentId());
            if (optionalContinent.isPresent()){
                country.setContinent(optionalContinent.get());
                countryRepository.save(country);
                isEdited = true;
            }else {
                return "There isn't Continent with ID:" + countryDto.getContinentId();
            }
        }
        return isEdited? "Successfully edited":"There isn't Country with ID:" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteCountryByID(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isPresent()){
            countryRepository.deleteById(id);
            isDeleted=true;
        }
        return isDeleted? "Successfully deleted": "There isn't Country with ID:" + id;
    }
}
