package com.example.redditclone.security;

import org.springframework.stereotype.Service;

@Service
public interface TotpManager {
    public String generateSecret();
    public String getUriForImage(String secret);
    public boolean verifyCode(String code, String secret);
}
