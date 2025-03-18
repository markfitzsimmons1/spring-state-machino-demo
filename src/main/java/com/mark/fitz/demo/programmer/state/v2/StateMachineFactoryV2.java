package com.mark.fitz.demo.programmer.state.v2;

import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import com.mark.fitz.demo.programmer.state.ProgrammerStateMachineFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class StateMachineFactoryV2 implements ProgrammerStateMachineFactory<EventV2> {

    private final StateMachineFactory<StateV2, EventV2> stateMachineV2;

    public StateMachineFactoryV2(StateMachineFactory<StateV2, EventV2> stateMachineV2) {
        this.stateMachineV2 = stateMachineV2;
    }

    @Override
    public ProgrammerStateMachineV2 createMachine(Programmer programmer, Programmer reviewer) {
        StateMachine<StateV2, EventV2> machine = stateMachineV2.getStateMachine(programmer.getId().toString());

        machine.getStateMachineAccessor().doWithAllRegions(r ->
            r.resetStateMachineReactively(new DefaultStateMachineContext<>(
                (StateV2) programmer.getState(), null, null, null)
            ).block());

        machine.getExtendedState().getVariables().put(Constants.PROGRAMMER, programmer);
        if (reviewer != null) {
            machine.getExtendedState().getVariables().put(Constants.REVIEWER, reviewer);
        }
        machine.startReactively().block();

        return new ProgrammerStateMachineV2(machine);
    }
}
