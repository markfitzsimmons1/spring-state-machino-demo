package com.mark.fitz.demo.programmer.state.v2;

import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import org.springframework.statemachine.action.Action;

public class ActionsV2 {

    public static Action<StateV2, EventV2> baseAction() {
            return context -> {
                Programmer programmer = (Programmer) context.getExtendedState().getVariables().get(
                    Constants.PROGRAMMER);
                programmer.setState(context.getTarget().getId());
            };
    }

    public static Action<StateV2, EventV2> satisfiedWithWork() {
        return context -> {
            Programmer programmer = (Programmer) context.getExtendedState().getVariables().get(
                Constants.PROGRAMMER);
            programmer.setState(StateV2.SATISFIED_WITH_WORK);
        };
    }

    public static Action<StateV2, EventV2> cryingWithImposterSyndrome() {
        return context -> {
            Programmer programmer = (Programmer) context.getExtendedState().getVariables().get(
                Constants.PROGRAMMER);
            programmer.setState(StateV2.CRYING_WITH_IMPOSTER_SYNDROME);
        };
    }

    public static Action<StateV2, EventV2> cryingWithRage() {
        return context -> {
            Programmer programmer = (Programmer) context.getExtendedState().getVariables().get(
                Constants.PROGRAMMER);
            programmer.setState(StateV2.CRYING_WITH_RAGE);
        };
    }


    private ActionsV2() {}

}
