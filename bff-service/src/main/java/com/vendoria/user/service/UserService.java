package com.vendoria.user.service;

import com.vendoria.bff.feign.client.IVendoriaApiClient;
import com.vendoria.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IVendoriaApiClient vendoriaApiClient;

    public User getUser(String email) {
        return vendoriaApiClient.getUserByEmail(email);
    }
}
