package com.Ecommerce_BE.Service;

import com.Ecommerce_BE.Dto.AddressDto;
import com.Ecommerce_BE.Dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}
