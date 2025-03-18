package com.mark.fitz.demo.programmer.service;

import org.springframework.stereotype.Service;

@Service
public class ProgrammerService {

    private final ProgrammerRepository programmerRepository;

    public ProgrammerService(ProgrammerRepository programmerRepository) {
        this.programmerRepository = programmerRepository;
    }

    public Programmer getProgrammer(Long id) {
        return programmerRepository.getProgrammer(id).orElseThrow();
    }

    public Programmer drinkCoffee(Long id) {
        Programmer programmer = programmerRepository.getProgrammer(id).orElseThrow();
        programmer.setTired(false);
        programmerRepository.save(programmer);
        return programmer;
    }

    public void saveProgrammer(Programmer programmer) {
        programmerRepository.save(programmer);
    }

}
