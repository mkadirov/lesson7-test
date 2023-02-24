package uz.pdp.lesson7test.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7test.map.entity.Address;
import uz.pdp.lesson7test.map.entity.District;
import uz.pdp.lesson7test.map.payload.AddressDto;
import uz.pdp.lesson7test.map.repository.AddressRepository;
import uz.pdp.lesson7test.map.repository.DistrictRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;

    @GetMapping
    public List<Address> getAddressList(){
        return addressRepository.findAll();
    }

    @PostMapping
    public String addAddress(@RequestBody AddressDto addressDto){
        boolean isAdded = false;
        Optional<District> optionalDistrict = districtRepository.findById(addressDto.getDistrictId());
        if (optionalDistrict.isPresent()) {
            District district = optionalDistrict.get();
            Address address = new Address(null, addressDto.getStreet(),
                    addressDto.getHouseNumber(), district);
            addressRepository.save(address);
            isAdded = true;
        }
        return isAdded? "Successfully added":"There isn't District with ID:"+addressDto.getDistrictId();
    }

    @GetMapping("/{id}")
    public Address getAddress(@PathVariable Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElseGet(Address::new);
    }

    @PutMapping("/{id}")
    public String editAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto){
        boolean isEdited = false;
        Optional<Address> optionalAddress = addressRepository.findById(id);
        Optional<District> optionalDistrict = districtRepository.findById(addressDto.getDistrictId());
        if (optionalAddress.isPresent() && optionalDistrict.isPresent()){
            Address address = optionalAddress.get();
            address.setStreet(addressDto.getStreet());
            address.setHouseNumber(addressDto.getHouseNumber());
            address.setDistrict(optionalDistrict.get());
            addressRepository.save(address);
            isEdited = true;
        }
        return isEdited? "Successfully edited":"There isn't District or Address with this id";
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            addressRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted":"There isn't Address with this id";
    }
}
