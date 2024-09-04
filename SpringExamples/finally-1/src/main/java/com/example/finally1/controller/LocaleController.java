package com.example.finally1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import java.util.Locale;

@RestController
public class LocaleController {

    private final LocaleResolver localeResolver;

    public LocaleController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @GetMapping("/changeLanguage")
    public void changeLangguage(@RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response) {
        Locale newLocale = new Locale(lang);
        localeResolver.setLocale(request, response, newLocale);
    }
}
