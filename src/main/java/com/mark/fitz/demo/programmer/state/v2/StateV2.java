package com.mark.fitz.demo.programmer.state.v2;

import com.mark.fitz.demo.programmer.state.State;

public enum StateV2 implements State {

    // Parent States
    WRITING_CODE,
    IN_CODE_REVIEW,
    SATISFIED_WITH_WORK,
    CRYING_WITH_IMPOSTER_SYNDROME,
    CRYING_WITH_RAGE,

    // Junction States (pseudo-states)
    CODE_REVIEW_JUNCTION
}