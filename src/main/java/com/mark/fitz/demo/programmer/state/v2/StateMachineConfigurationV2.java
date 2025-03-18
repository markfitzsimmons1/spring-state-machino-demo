package com.mark.fitz.demo.programmer.state.v2;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name="factoryV2")
public class StateMachineConfigurationV2
		extends EnumStateMachineConfigurerAdapter<StateV2, EventV2> {

	@Override
	public void configure(StateMachineStateConfigurer<StateV2, EventV2> states)
			throws Exception {
		states
			.withStates()
				.initial(StateV2.WRITING_CODE)
				.states(EnumSet.allOf(StateV2.class))
				.junction(StateV2.CODE_REVIEW_JUNCTION);
	}

	@Override
	public void configure(
			StateMachineTransitionConfigurer<StateV2, EventV2> transitions)
			throws Exception {

		transitions
			.withExternal()
				.source(StateV2.WRITING_CODE)
				.target(StateV2.IN_CODE_REVIEW)
				.event(EventV2.CODE_WRITING_FINISHED)
				.action(ActionsV2.baseAction())
				.and()

			.withExternal()
				.source(StateV2.IN_CODE_REVIEW)
				.target(StateV2.CODE_REVIEW_JUNCTION)
				.event(EventV2.CODE_REVIEW_FINISHED)
				.guard(GuardsV2.areProgrammerAndCoderDifferent())
				.action(ActionsV2.baseAction())
				.and()

			.withJunction()
				.source(StateV2.CODE_REVIEW_JUNCTION)
				.first(StateV2.SATISFIED_WITH_WORK, GuardsV2.isSatisfiedWithWork(), ActionsV2.satisfiedWithWork())
				.then(StateV2.CRYING_WITH_IMPOSTER_SYNDROME, GuardsV2.isCryingWithImposterSyndrome(), ActionsV2.cryingWithImposterSyndrome())
				.last(StateV2.CRYING_WITH_RAGE, ActionsV2.cryingWithRage());
	}
}
