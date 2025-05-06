package com.mark.fitz.demo.programmer.state.v3;

import com.mark.fitz.demo.programmer.exception.ReviewerNotFoundException;
import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class GuardsV3 {

    public static Guard<StateV3, EventV3> isSatisfiedWithWork() {
        return context -> {
            Programmer programmer = context.getExtendedState().get(Constants.PROGRAMMER, Programmer.class);
            Programmer reviewer = getReviewerOrThrowException(context);
            return programmer.canWriteGoodCode() && reviewer.isCompetent() ||
                    !programmer.canWriteGoodCode() && reviewer.isTired();
        };
    }

    public static Guard<StateV3, EventV3> isCryingWithImposterSyndrome() {
        return context -> {
            Programmer reviewer = getReviewerOrThrowException(context);
            return reviewer.isCompetent();
        };
    }

    public static Guard<StateV3, EventV3> isAbleToComposeSelf() {
        return context -> {
            Programmer programmer = context.getExtendedState().get(Constants.PROGRAMMER, Programmer.class);
            return !programmer.isStressed();
        };
    }

    public static Guard<StateV3, EventV3> areProgrammerAndCoderDifferent() {
        return context -> {
            Programmer programmer = context.getExtendedState().get(Constants.PROGRAMMER, Programmer.class);
            Programmer reviewer = getReviewerOrThrowException(context);
            return programmer != reviewer;
        };
    }

    private static Programmer getReviewerOrThrowException(StateContext<StateV3, EventV3> context) {
        Programmer reviewer = context.getExtendedState().get(Constants.REVIEWER, Programmer.class);
        if (reviewer == null) {
            throw new ReviewerNotFoundException();
        }
        return reviewer;
    }

   private GuardsV3() {}

}
