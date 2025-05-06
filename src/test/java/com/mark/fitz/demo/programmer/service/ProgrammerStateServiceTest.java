package com.mark.fitz.demo.programmer.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.mark.fitz.demo.programmer.state.v1.StateV1;
import com.mark.fitz.demo.programmer.state.v2.StateV2;

import com.mark.fitz.demo.programmer.state.v3.StateV3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProgrammerStateServiceTest {

    @Autowired
    private ProgrammerStateService programmerStateService;

    @Autowired
    private ProgrammerService programmerService;

    private final Long markId = 1L;

    private final Programmer mark = Programmer.builder()
            .name("Mark")
            .id(markId)
            .tired(false)
            .competent(false)
            .stressed(false)
            .build();

    private final Programmer phil = Programmer.builder()
            .name("Phil")
            .id(2L)
            .tired(false)
            .competent(true)
            .build();

    @BeforeEach
    void setUp() {
        programmerService.saveProgrammer(mark);
        programmerService.saveProgrammer(phil);
    }

    @Test
    void testV1StateMachine() {
        programmerStateService.finishedCodingV1(mark);
        Programmer fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV1.IN_CODE_REVIEW);
        programmerStateService.finishedCodeReviewV1(fetchedMark);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV1.SATISFIED_WITH_WORK);
    }

    @Test
    void testV2StateMachine() {
        // Flow to Satisfaction
        programmerStateService.finishedCodingV2(mark);
        Programmer fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV2.IN_CODE_REVIEW);
        programmerStateService.finishedCodeReviewV2(fetchedMark, phil);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV2.SATISFIED_WITH_WORK);

        // Flow to Crying
        mark.setState(StateV2.WRITING_CODE);
        mark.setStressed(true);
        programmerStateService.finishedCodingV2(mark);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV2.IN_CODE_REVIEW);
        programmerStateService.finishedCodeReviewV2(mark, phil);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV2.CRYING_WITH_IMPOSTER_SYNDROME);
    }

    @Test
    void testV3StateMachine() {
        // Flow to Satisfaction
        programmerStateService.finishedCodingV3(mark);
        Programmer fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV3.IN_CODE_REVIEW);
        programmerStateService.finishedCodeReviewV3(mark, phil);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV3.SATISFIED_WITH_WORK);

        // Pick up new ticket
        programmerStateService.pickUpNewTicketV3(fetchedMark);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV3.WRITING_CODE);

        // Flow to Crying
        fetchedMark.setStressed(true);
        programmerStateService.finishedCodingV3(fetchedMark);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV3.IN_CODE_REVIEW);
        programmerStateService.finishedCodeReviewV3(mark, phil);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV3.CRYING_WITH_IMPOSTER_SYNDROME);

        // Flow to Uncontrollable Sobbing
        programmerStateService.attemptToComposeSelfV3(fetchedMark);
        fetchedMark = programmerService.getProgrammer(markId);
        assertThat(fetchedMark.getState()).isEqualTo(StateV3.UNCONTROLLABLE_SOBBING);
    }

}
