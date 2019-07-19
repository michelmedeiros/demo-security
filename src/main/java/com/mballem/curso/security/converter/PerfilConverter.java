package com.mballem.curso.security.converter;

import com.mballem.curso.security.domain.Perfil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PerfilConverter implements Converter<String[], List<Perfil>> {
    @Override
    public List<Perfil> convert(String[] values) {
        List<Perfil> perfils = new ArrayList<>();
        Stream.of(values)
                .filter(p -> !p.equals("0"))
                .forEach(value -> perfils.add(new Perfil(Long.parseLong(value))));
        return perfils;
    }
}