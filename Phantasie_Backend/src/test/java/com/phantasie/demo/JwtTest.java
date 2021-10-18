package com.phantasie.demo;

import com.auth0.jwt.interfaces.Claim;
import com.phantasie.demo.utils.JwtUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JwtTest {

    @Test
    public void testJWT() throws UnsupportedEncodingException {
        String token = JwtUtil.createJWT("jchWill");
        System.out.println(token);
        Assert.assertNotNull(token);

        String name = JwtUtil.verify(token).asString();
        Assert.assertNotNull(name);
        Assert.assertEquals("jchWill",name);

    }
}
