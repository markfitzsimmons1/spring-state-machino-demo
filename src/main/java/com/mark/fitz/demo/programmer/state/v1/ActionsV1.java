package com.mark.fitz.demo.programmer.state.v1;

import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import org.springframework.statemachine.action.Action;

public class ActionsV1 {

    public static Action<StateV1, EventV1> v1Action() {
        return context -> {
            Programmer programmer = (Programmer) context.getExtendedState().getVariables().get(
                Constants.PROGRAMMER);
            programmer.setState(context.getTarget().getId());
        };
    }

    private ActionsV1() {}

}
