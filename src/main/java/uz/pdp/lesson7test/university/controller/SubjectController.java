package uz.pdp.lesson7test.university.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.university.entity.Subject;
import uz.pdp.lesson7test.university.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/subject")
public class SubjectController {

   @Autowired
    SubjectRepository subjectRepository;

   @GetMapping
    public List<Subject> getSubjects(){
       return subjectRepository.findAll();
   }

   @PostMapping
    public String addSubject(@RequestBody Subject subject){
       subjectRepository.save(subject);
       return "Successfully added";
   }

   @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Integer id){
       Optional<Subject> subjectOptional = subjectRepository.findById(id);
       return subjectOptional.orElseGet(Subject::new);
   }

   @PutMapping("/{id}")
    public String editSubject(@PathVariable Integer id, @RequestBody Subject subject){
       boolean isEdited = false;
       Optional<Subject> subjectOptional = subjectRepository.findById(id);
       if(subjectOptional.isPresent()){
          Subject editingSubject = subjectOptional.get();
          editingSubject.setName(subject.getName());
          subjectRepository.save(editingSubject);
          isEdited = true;
       }
       return isEdited ? "Successfully edited": "There isn't Subject with ID:"+id;
   }

   @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Integer id){
       boolean isDeleted = false;
       Optional<Subject> subjectOptional = subjectRepository.findById(id);
       if(subjectOptional.isPresent()){
           subjectRepository.deleteById(id);
           isDeleted=true;
       }
       return isDeleted? "Successfully deleted":"There isn't Subject with ID:"+id;
   }
}
