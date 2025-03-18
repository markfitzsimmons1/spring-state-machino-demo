package com.mark.fitz.demo.programmer.state.v3;

import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import com.mark.fitz.demo.programmer.service.ProgrammerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StateMachineListenerV3 implements StateMachineListener<StateV3, EventV3> {

    private final ProgrammerRepository programmerRepository;

    public StateMachineListenerV3(ProgrammerRepository programmerRepository) {
        this.programmerRepository = programmerRepository;
    }

    @Override
    public void stateChanged(State state, State state1) {
        log.info("State changed from {} to {}", state, state1);
    }

    @Override
    public void stateEntered(State state) {

    }

    @Override
    public void stateExited(State state) {

    }

    @Override
    public void eventNotAccepted(Message message) {
        log.error("Event not accepted: {}", message);
    }

    @Override
    public void transition(Transition transition) {

    }

    @Override
    public void transitionStarted(Transition transition) {

    }

    @Override
    public void transitionEnded(Transition transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine stateMachine) {

    }

    @Override
    public void stateMachineStopped(StateMachine stateMachine) {

    }

    @Override
    public void stateMachineError(StateMachine stateMachine, Exception e) {

    }

    @Override
    public void extendedStateChanged(Object o, Object o1) {
        if (o.equals(Constants.PROGRAMMER)) {
            programmerRepository.save((Programmer) o1);
        }
    }

    @Override
    public void stateContext(StateContext context) {
        Programmer programmer = (Programmer) context.getExtendedState().getVariables().get(
                Constants.PROGRAMMER);
        if (programmer == null || context.getTarget() == null) {
            return;
        }
        programmer.setState((StateV3) context.getTarget().getId());
        programmerRepository.save(programmer);
    }
}
