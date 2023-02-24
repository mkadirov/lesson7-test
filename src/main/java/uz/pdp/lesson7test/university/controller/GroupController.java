package uz.pdp.lesson7test.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.university.entity.Faculty;
import uz.pdp.lesson7test.university.entity.Group;
import uz.pdp.lesson7test.university.payload.GroupDto;
import uz.pdp.lesson7test.university.repository.FacultyRepository;
import uz.pdp.lesson7test.university.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @GetMapping
    public List<Group> getGroupList(){
        return groupRepository.findAll();
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto){
        Group group = new Group();
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if(optionalFaculty.isPresent()){
            group.setName(groupDto.getName());
            group.setFaculty(optionalFaculty.get());
            groupRepository.save(group);
            return "Successfully added";
        }else {
            return "There isn't Faculty with ID:" + groupDto.getFacultyId();
        }
    }
    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Integer id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        return optionalGroup.orElseGet(Group::new);
    }

    @PutMapping("/{id}")
    public String editGroup(@PathVariable Integer id, @RequestBody GroupDto groupDto){
        boolean isEdited = false;
        Optional<Group> optionalGroup = groupRepository.findById(id);
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if(optionalFaculty.isPresent() && optionalGroup.isPresent()){
            Group group = new Group();
            group.setName(groupDto.getName());
            Faculty faculty = optionalFaculty.get();
            group.setFaculty(faculty);
            groupRepository.save(group);
            isEdited = true;
        }
        return isEdited? "Successfully edited" : "There isn't Faculty or Group with ID:"
                + id +"/"+ groupDto.getFacultyId();
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()){
            groupRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted": "There isn't Group with ID:" + id;
    }
}
