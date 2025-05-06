package com.mark.fitz.demo.programmer.service;

import com.mark.fitz.demo.programmer.state.ProgrammerStateMachine;
import com.mark.fitz.demo.programmer.state.ProgrammerStateMachineFactory;
import com.mark.fitz.demo.programmer.state.v1.EventV1;
import com.mark.fitz.demo.programmer.state.v2.EventV2;
import com.mark.fitz.demo.programmer.state.v3.EventV3;
import org.springframework.stereotype.Service;

@Service
public class ProgrammerStateService {

    private final ProgrammerStateMachineFactory<EventV1> stateMachineFactoryV1;

    private final ProgrammerStateMachineFactory<EventV2> stateMachineFactoryV2;

    private final ProgrammerStateMachineFactory<EventV3> stateMachineFactoryV3;

    private final ProgrammerRepository programmerRepository;

    public ProgrammerStateService(ProgrammerStateMachineFactory<EventV1> stateMachineFactoryV1,
        ProgrammerStateMachineFactory<EventV2> stateMachineFactoryV2,
        ProgrammerStateMachineFactory<EventV3> stateMachineFactoryV3,
        ProgrammerRepository programmerRepository) {
        this.stateMachineFactoryV1 = stateMachineFactoryV1;
        this.stateMachineFactoryV2 = stateMachineFactoryV2;
        this.stateMachineFactoryV3 = stateMachineFactoryV3;
        this.programmerRepository = programmerRepository;
    }

    // V1

    public void finishedCodingV1(Programmer programmer) {
        ProgrammerStateMachine<EventV1> machine = stateMachineFactoryV1.createMachine(programmer, null);
        machine.sendEvent(EventV1.CODE_WRITING_FINISHED);
        programmerRepository.save(programmer);
    }

    public void finishedCodeReviewV1(Programmer programmer) {
        ProgrammerStateMachine<EventV1> machine = stateMachineFactoryV1.createMachine(programmer, null);
        machine.sendEvent(EventV1.CODE_REVIEW_FINISHED);
        programmerRepository.save(programmer);
    }

    // V2

    public void finishedCodingV2(Programmer programmer) {
        ProgrammerStateMachine<EventV2> machine = stateMachineFactoryV2.createMachine(programmer, null);
        machine.sendEvent(EventV2.CODE_WRITING_FINISHED);
        programmerRepository.save(programmer);
    }

    public void finishedCodeReviewV2(Programmer programmer, Programmer reviewer) {
        ProgrammerStateMachine<EventV2> machine = stateMachineFactoryV2.createMachine(programmer, reviewer);
        machine.sendEvent(EventV2.CODE_REVIEW_FINISHED);
        programmerRepository.save(programmer);
    }

    // V3

    public void finishedCodingV3(Programmer programmer) {
        ProgrammerStateMachine<EventV3> machine = stateMachineFactoryV3.createMachine(programmer, null);
        machine.sendEvent(EventV3.CODE_WRITING_FINISHED);
    }

    public void finishedCodeReviewV3(Programmer programmer, Programmer reviewer) {
        ProgrammerStateMachine<EventV3> machine = stateMachineFactoryV3.createMachine(programmer, reviewer);
        machine.sendEvent(EventV3.CODE_REVIEW_FINISHED);
    }

    public void attemptToComposeSelfV3(Programmer programmer) {
        ProgrammerStateMachine<EventV3> machine = stateMachineFactoryV3.createMachine(programmer, null);
        machine.sendEvent(EventV3.ATTEMPT_TO_COMPOSE_YOURSELF);
    }

    public void pickUpNewTicketV3(Programmer programmer) {
        ProgrammerStateMachine<EventV3> machine = stateMachineFactoryV3.createMachine(programmer, null);
        machine.sendEvent(EventV3.PICKED_UP_NEW_TICKET);
    }

}
