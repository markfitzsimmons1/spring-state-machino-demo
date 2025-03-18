package com.mark.fitz.demo.programmer.service;

import com.mark.fitz.demo.programmer.state.State;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ProgrammerRepository {

    private final Map<Long, Programmer> programmerMap = new HashMap<>();

    public void save(Programmer programmer) {
        programmerMap.put(programmer.getId(), programmer);
    }

    public Optional<Programmer> getProgrammer(Long id) {
        return Optional.ofNullable(programmerMap.getOrDefault(id, null));
    }

}
