package uz.pdp.lesson7test.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.Address;
import uz.pdp.lesson7test.map.entity.District;
import uz.pdp.lesson7test.map.repository.AddressRepository;
import uz.pdp.lesson7test.map.repository.DistrictRepository;
import uz.pdp.lesson7test.university.entity.Group;
import uz.pdp.lesson7test.university.entity.Student;
import uz.pdp.lesson7test.university.entity.Subject;
import uz.pdp.lesson7test.university.payload.StudentDto;
import uz.pdp.lesson7test.university.repository.GroupRepository;
import uz.pdp.lesson7test.university.repository.StudentRepository;
import uz.pdp.lesson7test.university.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;



    @GetMapping
    public List<Student> getStudentList(){
        return studentRepository.findAll();
    }

    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto){
       Group group;
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if(optionalGroup.isPresent()){
            group = optionalGroup.get();
        }else {
            return "There isn't Group with ID:" + studentDto.getGroupId();
        }
        List<Subject> subjectList = new ArrayList<>();
        List<Integer> subjectsIdList = studentDto.getSubjectsIdList();
        for (Integer subjectId : subjectsIdList) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
            if(optionalSubject.isPresent()){
                subjectList.add(optionalSubject.get());
            }else {
                return "There isn't Subject with ID:" + subjectId;
            }
        }
        Optional<District> optionalDistrict = districtRepository.findById(studentDto.getDistrictId());
        District district;
        if (optionalDistrict.isPresent()){
            district = optionalDistrict.get();
        }else {
            return "there isn't district with this id";
        }
        Address address = new Address(null, studentDto.getStreet(),
                studentDto.getHouseNumber(), district);
        Student student = new Student(null, studentDto.getFirstName(), studentDto.getLastName(),
                group, addressRepository.save(address),subjectList);
        studentRepository.save(student);
        return "Successfully added";
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElseGet(Student::new);
    }

    @GetMapping("/ministry")
    public Page<Student> getStudentPageByGroupId(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        return studentRepository.findAll(pageable);
    }
    @GetMapping("/university/{id}")
    public Page<Student> getStudentsByUniversityId(@PathVariable Integer id, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        return  studentRepository.findAll(pageable);
    }

    @PutMapping("/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto){
        boolean isEdited = false;
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Student student;
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        Optional<District> optionalDistrict = districtRepository.findById(studentDto.getDistrictId());

        // Student, Group va  Manzildagi Districtni mavjud ekanligini tekshiradi

        if (optionalStudent.isPresent() && optionalGroup.isPresent() && optionalDistrict.isPresent()){
            student = optionalStudent.get();
            List<Subject> subjectList = new ArrayList<>();
            List<Integer> subjectsIdList= studentDto.getSubjectsIdList();
            for (Integer subjectId : subjectsIdList) {
                Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
                if (optionalSubject.isPresent()){
                    subjectList.add(optionalSubject.get());
                }else {
                    return "There isn't Subject with ID:" + subjectId;
                }
            }
            Address address = student.getAddress();
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());
            address.setDistrict(optionalDistrict.get());
            address.setStreet(studentDto.getStreet());
            address.setHouseNumber(studentDto.getHouseNumber());
            student.setGroup(optionalGroup.get());
            student.setSubjects(subjectList);
            addressRepository.save(address);
            studentRepository.save(student);
            isEdited = true;
        }
        return isEdited? "Successfully edited":"There isn't group or student with this ID";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            studentRepository.deleteById(id);
            isDeleted= true;
        }
        return isDeleted? "Successfully deleted": "There isn't any student with ID" + id;
    }
}
