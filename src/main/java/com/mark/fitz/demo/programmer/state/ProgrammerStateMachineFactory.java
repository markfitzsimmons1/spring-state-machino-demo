package com.mark.fitz.demo.programmer.state;

import com.mark.fitz.demo.programmer.service.Programmer;

public interface ProgrammerStateMachineFactory<T> {

    ProgrammerStateMachine<T> createMachine(Programmer programmer, Programmer reviewer);

}
