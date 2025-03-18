package com.mark.fitz.demo.programmer.state.v2;

import com.mark.fitz.demo.programmer.exception.ReviewerNotFoundException;
import com.mark.fitz.demo.programmer.service.Constants;
import com.mark.fitz.demo.programmer.service.Programmer;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class GuardsV2 {

    public static Guard<StateV2, EventV2> isSatisfiedWithWork() {
        return context -> {
            Programmer programmer = context.getExtendedState().get(Constants.PROGRAMMER, Programmer.class);
            Programmer reviewer = getReviewerOrThrowException(context);
            return programmer.canWriteGoodCode() && reviewer.isCompetent() ||
                    !programmer.canWriteGoodCode() && reviewer.isTired();
        };
    }

    public static Guard<StateV2, EventV2> isCryingWithImposterSyndrome() {
        return context -> {
            Programmer reviewer = getReviewerOrThrowException(context);
            return reviewer.isCompetent();
        };
    }

    public static Guard<StateV2, EventV2> areProgrammerAndCoderDifferent() {
        return context -> {
            Programmer programmer = context.getExtendedState().get(Constants.PROGRAMMER, Programmer.class);
            Programmer reviewer = getReviewerOrThrowException(context);
            return programmer != reviewer;
        };
    }

    private static Programmer getReviewerOrThrowException(StateContext<StateV2, EventV2> context) {
        Programmer reviewer = context.getExtendedState().get(Constants.REVIEWER, Programmer.class);
        if (reviewer == null) {
            throw new ReviewerNotFoundException();
        }
        return reviewer;
    }

   private GuardsV2() {}

}
