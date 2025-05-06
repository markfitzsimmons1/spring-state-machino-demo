package com.mark.fitz.demo.programmer.state.v3;

import com.mark.fitz.demo.programmer.state.State;

import java.util.Set;

public enum StateV3 implements State {

    // Parent States
    WRITING_CODE,
    IN_CODE_REVIEW,
    SATISFIED_WITH_WORK,
    CRYING,

    // Substates
    CRYING_WITH_IMPOSTER_SYNDROME,
    CRYING_WITH_RAGE,
    UNCONTROLLABLE_SOBBING,

    // Junction States (pseudo-states)
    CRYING_JUNCTION,
    CODE_REVIEW_JUNCTION;

    public static final Set<StateV3> PARENT_STATES = Set.of(
        WRITING_CODE,
        IN_CODE_REVIEW,
        SATISFIED_WITH_WORK,
        CRYING
    );

}
