package com.mark.fitz.demo.programmer.state.v1;

import java.util.EnumSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "factoryV1")
public class StateMachineConfigurationV1
		extends EnumStateMachineConfigurerAdapter<StateV1, EventV1> {

	@Override
	public void configure(StateMachineStateConfigurer<StateV1, EventV1> states)
			throws Exception {
		states
			.withStates()
				.initial(StateV1.WRITING_CODE)
				.end(StateV1.SATISFIED_WITH_WORK)
				.states(EnumSet.allOf(StateV1.class));
	}

	@Override
	public void configure(
			StateMachineTransitionConfigurer<StateV1, EventV1> transitions)
			throws Exception {

		transitions
			.withExternal()
				.source(StateV1.WRITING_CODE)
				.target(StateV1.IN_CODE_REVIEW)
				.event(EventV1.CODE_WRITING_FINISHED)
				.action(ActionsV1.v1Action())
				.and()

			.withExternal()
				.source(StateV1.IN_CODE_REVIEW)
				.target(StateV1.SATISFIED_WITH_WORK)
				.event(EventV1.CODE_REVIEW_FINISHED)
				.action(ActionsV1.v1Action())
		;
	}

	@Bean
	public StateMachineFactoryV1 stateMachineFactoryV1(StateMachineFactory<StateV1, EventV1> factoryV1) {
		return new StateMachineFactoryV1(factoryV1);
	}

}
