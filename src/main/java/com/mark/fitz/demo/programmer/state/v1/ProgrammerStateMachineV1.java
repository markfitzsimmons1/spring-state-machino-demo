package com.mark.fitz.demo.programmer.state.v1;

import com.mark.fitz.demo.programmer.state.ProgrammerStateMachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;

@Slf4j
public class ProgrammerStateMachineV1 implements ProgrammerStateMachine<EventV1> {

	private final StateMachine<StateV1, EventV1> stateMachine;

	public ProgrammerStateMachineV1(StateMachine<StateV1, EventV1> stateMachine) {
        this.stateMachine = stateMachine;
	}

	@Override
	public void sendEvent(EventV1 event) {
        log.info("Sending event: {}", event);
		stateMachine.sendEvent(Mono.just(MessageBuilder
						.withPayload(event).build()))
				.subscribe();
	}
}
