package com.mark.fitz.demo.programmer.state.v2;

import com.mark.fitz.demo.programmer.state.ProgrammerStateMachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;

@Slf4j
public class ProgrammerStateMachineV2 implements ProgrammerStateMachine<EventV2> {

	private final StateMachine<StateV2, EventV2> stateMachine;

	public ProgrammerStateMachineV2(StateMachine<StateV2, EventV2> stateMachine) {
        this.stateMachine = stateMachine;
	}

	@Override
	public void sendEvent(EventV2 event) {
		log.info("Sending event: {}", event);
		stateMachine.sendEvent(Mono.just(MessageBuilder
						.withPayload(event).build()))
				.subscribe();
	}
}
