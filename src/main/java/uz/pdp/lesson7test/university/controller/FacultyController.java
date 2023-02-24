package uz.pdp.lesson7test.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.university.entity.Faculty;
import uz.pdp.lesson7test.university.entity.University;
import uz.pdp.lesson7test.university.payload.FacultyDto;
import uz.pdp.lesson7test.university.repository.FacultyRepository;
import uz.pdp.lesson7test.university.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    @GetMapping
    public List<Faculty> getFacultyList(){
        return facultyRepository.findAll();
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto){
        Faculty faculty= new Faculty();
        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if(optionalUniversity.isPresent()){
            faculty.setName(facultyDto.getName());
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
        }else {
            return "There isn't University with ID:" + facultyDto.getUniversityId();
        }
        faculty.setName(facultyDto.getName());
        return "Successfully added";
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        return optionalFaculty.orElseGet(Faculty::new);
    }

    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){
        boolean isEdited = false;
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if(optionalFaculty.isPresent() && optionalUniversity.isPresent()){
            Faculty faculty = optionalFaculty.get();
            faculty.setUniversity(optionalUniversity.get());
            faculty.setName(facultyDto.getName());
            facultyRepository.save(faculty);
            isEdited = true;
        }
            return isEdited ? "Successfully edited":"There isn't Faculty or University with ID:"+id +"/" +
                    facultyDto.getUniversityId();
    }

    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if(optionalFaculty.isPresent()){
            facultyRepository.deleteById(id);
            isDeleted = true;
        }
            return isDeleted? "Successfully deleted" : "There isn't Faculty  with ID:"+id;
    }
}
