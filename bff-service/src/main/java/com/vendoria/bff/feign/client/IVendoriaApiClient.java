package com.vendoria.bff.feign.client;

import com.vendoria.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("vendoria")
public interface IVendoriaApiClient {
    @GetMapping("${app.api.version.vendoria}${app.api.path.users.getUser}")
    User getUserByEmail(@RequestParam("email") String email);

    @PostMapping("${app.api.version.vendoria}${app.api.path.users.signin}")
    void signIn(@RequestParam("email") String email, @RequestParam("password") String password);

    @PostMapping("${app.api.version.vendoria}${app.api.path.users.register}")
    void register(@RequestParam("email") String email, @RequestParam("password") String password);
}
