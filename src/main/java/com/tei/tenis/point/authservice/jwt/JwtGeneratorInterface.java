package com.tei.tenis.point.authservice.jwt;

import com.tei.tenis.point.authservice.entity.User;

import java.util.Map;

public interface JwtGeneratorInterface {

    Map<String, String> generateToken(User user);
}
