package com.Ecommerce_BE.Service.ServiceImp;

import com.Ecommerce_BE.Dto.AddressDto;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Mapper.EntityDtoMapper;
import com.Ecommerce_BE.Model.Address;
import com.Ecommerce_BE.Model.User;
import com.Ecommerce_BE.Repository.AddressRepository;
import com.Ecommerce_BE.Service.AddressService;
import com.Ecommerce_BE.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AddressServiceImp implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;


    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();
        Address address = user.getAddress();

        //save
        if(address == null)
        {
            address = new Address();
            user.setAddress(address);
        }

        //update
        if(addressDto.getStreet() != null)
        {
            address.setStreet(addressDto.getStreet());
        }
        if(addressDto.getState() != null)
        {
            address.setState(addressDto.getState());
        }
        if(addressDto.getCity() != null)
        {
            address.setCity(addressDto.getCity());
        }
        if(addressDto.getCountry() != null)
        {
            address.setCountry(addressDto.getCountry());
        }
        if(addressDto.getZipCode() != null)
        {
            address.setZipCode(addressDto.getZipCode());
        }

        addressRepository.save(address);

        String message = (user.getAddress() == null) ? "Address successfully created" : "Address successfully updated";

        return Response.builder()
                .status(200)
                .message(message)
                .build();

    }

}
