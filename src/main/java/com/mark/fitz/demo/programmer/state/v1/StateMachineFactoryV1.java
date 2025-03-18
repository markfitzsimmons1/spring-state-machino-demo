package com.mark.fitz.demo.programmer.state.v1;

import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import com.mark.fitz.demo.programmer.state.ProgrammerStateMachineFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class StateMachineFactoryV1 implements ProgrammerStateMachineFactory<EventV1> {

    private final StateMachineFactory<StateV1, EventV1> stateMachineV1;

    public StateMachineFactoryV1(StateMachineFactory<StateV1, EventV1> stateMachineV1) {
        this.stateMachineV1 = stateMachineV1;
    }

    @Override
    public ProgrammerStateMachineV1 createMachine(Programmer programmer, Programmer reviewer) {
        StateMachine<StateV1, EventV1> machine = stateMachineV1.getStateMachine(programmer.getId().toString());

        machine.getStateMachineAccessor().doWithAllRegions(r ->
                r.resetStateMachineReactively(new DefaultStateMachineContext<>(
                    (StateV1) programmer.getState(), null, null, null)
                ).block());

        machine.getExtendedState().getVariables().put(Constants.PROGRAMMER, programmer);
        machine.startReactively().block();
        return new ProgrammerStateMachineV1(machine);
    }
}
