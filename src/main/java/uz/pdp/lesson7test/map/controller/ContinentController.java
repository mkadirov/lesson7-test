package uz.pdp.lesson7test.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.Continent;
import uz.pdp.lesson7test.map.repository.ContinentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/continent")
public class ContinentController {

    @Autowired
    ContinentRepository continentRepository;

    @GetMapping
    public List<Continent> continentList(){
        return continentRepository.findAll();
    }

    @PostMapping
    public String addContinent(@RequestBody Continent continent){
        for (Continent c : continentRepository.findAll()) {
            if (c.getName().equals(continent.getName())){
                return "This Continent exists";
            }
        }
        continentRepository.save(continent);
        return "Successfully added";
    }

    @GetMapping("/{id}")
    public Continent getContinent(@PathVariable Integer id){
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        return optionalContinent.orElseGet(Continent::new);
    }

    @PutMapping("/{id}")
    public String editContinent(@PathVariable Integer id, @RequestBody Continent continent){
        boolean isEdited = false;
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isPresent()) {
            Continent editingContinent = optionalContinent.get();
            editingContinent.setName(continent.getName());
            continentRepository.save(editingContinent);
            isEdited = true;
        }
        return isEdited? "successfully edited":"There isn't any Continent with ID:" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteContinent(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if(optionalContinent.isPresent()){
            continentRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted":"There isn't Continent with ID:" +id;
    }

}
