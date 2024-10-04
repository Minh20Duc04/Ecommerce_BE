package com.Ecommerce_BE.Service;

import com.Ecommerce_BE.Dto.LoginRequest;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Dto.UserDto;
import com.Ecommerce_BE.Model.User;

public interface UserService {

    Response registerUser(UserDto registrationRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUser();

    User getLoginUser();

    Response getUserInfoAndOrderHistory(); //lay thong tin nguoi dung dang logIn

}
