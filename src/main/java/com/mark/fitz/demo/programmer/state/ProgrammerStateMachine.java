package com.mark.fitz.demo.programmer.state;


public interface ProgrammerStateMachine<T> {

    void sendEvent(T event);

}
