package uz.pdp.lesson7test.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.university.entity.Subject;
import uz.pdp.lesson7test.university.entity.Teacher;
import uz.pdp.lesson7test.university.payload.TeacherDto;
import uz.pdp.lesson7test.university.repository.SubjectRepository;
import uz.pdp.lesson7test.university.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping
    public List<Teacher> getTeacherList() {
        return teacherRepository.findAll();
    }

    @PostMapping
    public String addTeacher(@RequestBody TeacherDto teacherDto) {
        List<Subject> subjectList = new ArrayList<>();
        List<Integer> subjectIdList = teacherDto.getSubjectIdList();
        for (Integer id : subjectIdList) {
            Optional<Subject> optionalSubject = subjectRepository.findById(id);
            if (optionalSubject.isPresent()) {
                subjectList.add(optionalSubject.get());
            } else {
                return "There isn't Subject with ID:" + id;
            }
        }
        Teacher teacher = new Teacher(null, teacherDto.getFirstName(),
                teacherDto.getLastName(), subjectList);
        teacherRepository.save(teacher);
        return "Successfully added";
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Integer id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        return optionalTeacher.orElseGet(Teacher::new);
    }

    @PutMapping("/{id}")
    public String editTeacher(@PathVariable Integer id, @RequestBody TeacherDto teacherDto) {
        boolean isEdited = false;
        List<Subject> subjectList = new ArrayList<>();
        List<Integer> subjectIdList = teacherDto.getSubjectIdList();
        for (Integer subId : subjectIdList) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subId);
            if (optionalSubject.isPresent()) {
                subjectList.add(optionalSubject.get());
            } else {
                return "There isn't Subject with ID:" + id;
            }
        }
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isPresent()){
            Teacher teacher = optionalTeacher.get();
            teacher.setFirstName(teacherDto.getFirstName());
            teacher.setLastName(teacherDto.getLastName());
            teacher.setSubjectList(subjectList);
            isEdited = true;
        }
        return isEdited? "Successfully edited": "There isn't Teacher with ID:" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isPresent()){
            teacherRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted": "There isn't Teacher with ID:" + id;
    }
}
