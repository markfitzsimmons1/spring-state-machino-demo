package com.mark.fitz.demo.programmer.state.v3;

import com.mark.fitz.demo.programmer.state.ProgrammerStateMachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;

@Slf4j
public class ProgrammerStateMachineV3 implements ProgrammerStateMachine<EventV3> {

	private final StateMachine<StateV3, EventV3> stateMachine;

	public ProgrammerStateMachineV3(StateMachine<StateV3, EventV3> stateMachine) {
        this.stateMachine = stateMachine;
	}

	@Override
	public void sendEvent(EventV3 event) {
		log.info("Sending event: {}", event);
		stateMachine.sendEvent(Mono.just(MessageBuilder
						.withPayload(event).build()))
				.subscribe();
	}
}
