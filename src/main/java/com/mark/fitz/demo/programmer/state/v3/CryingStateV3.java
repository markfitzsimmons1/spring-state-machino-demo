package com.mark.fitz.demo.programmer.state.v3;

import com.mark.fitz.demo.programmer.state.State;

public enum CryingStateV3 implements State {

    // Parent States
    CRYING,

    // Substates
    CRYING_WITH_IMPOSTER_SYNDROME,
    CRYING_WITH_RAGE,
    UNCONTROLLABLE_SOBBING,

    // Junction States (pseudo-states)
    CRYING_JUNCTION,

}
