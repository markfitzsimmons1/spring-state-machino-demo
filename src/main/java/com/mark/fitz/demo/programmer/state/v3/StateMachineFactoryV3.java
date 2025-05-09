package com.mark.fitz.demo.programmer.state.v3;

import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import com.mark.fitz.demo.programmer.state.ProgrammerStateMachineFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class StateMachineFactoryV3 implements ProgrammerStateMachineFactory<EventV3> {

    private final StateMachineFactory<StateV3, EventV3> stateMachineV3;

    public StateMachineFactoryV3(StateMachineFactory<StateV3, EventV3> stateMachineV3) {
        this.stateMachineV3 = stateMachineV3;
    }

    @Override
    public ProgrammerStateMachineV3 createMachine(Programmer programmer, Programmer reviewer) {
        StateMachine<StateV3, EventV3> machine = stateMachineV3.getStateMachine(programmer.getId().toString());

        machine.getStateMachineAccessor().doWithAllRegions(r ->
            r.resetStateMachineReactively(new DefaultStateMachineContext<>(
                (StateV3) programmer.getState(), null, null, null)
            ).block());

        machine.getExtendedState().getVariables().put(Constants.PROGRAMMER, programmer);
        if (reviewer != null) {
            machine.getExtendedState().getVariables().put(Constants.REVIEWER, reviewer);
        }
        machine.startReactively().block();

        return new ProgrammerStateMachineV3(machine);
    }
}
