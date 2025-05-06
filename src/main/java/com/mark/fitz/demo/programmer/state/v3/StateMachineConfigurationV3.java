package com.mark.fitz.demo.programmer.state.v3;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name="factoryV3")
public class StateMachineConfigurationV3
        extends EnumStateMachineConfigurerAdapter<StateV3, EventV3> {

	private final StateMachineListenerV3 listener;

	public StateMachineConfigurationV3(StateMachineListenerV3 listener) {
		this.listener = listener;
	}

    @Override
    public void configure(StateMachineStateConfigurer<StateV3, EventV3> states)
            throws Exception {
        states
            .withStates()
                .initial(StateV3.WRITING_CODE)
                .states(StateV3.PARENT_STATES)
                .junction(StateV3.CODE_REVIEW_JUNCTION)
                .junction(StateV3.CRYING_JUNCTION)
                .and()

            .withStates()
                .parent(StateV3.CRYING)
                    .initial(StateV3.CRYING_JUNCTION)
                    .state(StateV3.CRYING_WITH_IMPOSTER_SYNDROME)
                    .state(StateV3.CRYING_WITH_RAGE)
                    .state(StateV3.UNCONTROLLABLE_SOBBING)
        ;
    }

    @Override
    public void configure(
            StateMachineTransitionConfigurer<StateV3, EventV3> transitions)
            throws Exception {

        transitions
            .withExternal()
                .source(StateV3.WRITING_CODE)
                .target(StateV3.IN_CODE_REVIEW)
                .event(EventV3.CODE_WRITING_FINISHED)
                .and()

            .withExternal()
                .source(StateV3.IN_CODE_REVIEW)
                .target(StateV3.CODE_REVIEW_JUNCTION)
                .event(EventV3.CODE_REVIEW_FINISHED)
                .guard(GuardsV3.areProgrammerAndCoderDifferent())
                .and()

            .withExternal()
                .source(StateV3.SATISFIED_WITH_WORK)
                .target(StateV3.WRITING_CODE)
                .event(EventV3.PICKED_UP_NEW_TICKET)
                .and()

            .withExternal()
                .source(StateV3.CRYING)
                .target(StateV3.CRYING_JUNCTION)
                .event(EventV3.ATTEMPT_TO_COMPOSE_YOURSELF)
                .and()

            .withJunction()
                .source(StateV3.CODE_REVIEW_JUNCTION)
                .first(StateV3.SATISFIED_WITH_WORK, GuardsV3.isSatisfiedWithWork())
                .then(StateV3.CRYING_WITH_IMPOSTER_SYNDROME, GuardsV3.isCryingWithImposterSyndrome())
                .last(StateV3.CRYING_WITH_RAGE)
                .and()

            .withJunction()
                .source(StateV3.CRYING_JUNCTION)
                .first(StateV3.WRITING_CODE, GuardsV3.isAbleToComposeSelf())
                .last(StateV3.UNCONTROLLABLE_SOBBING)
        ;
    }

	@Override
	public void configure(StateMachineConfigurationConfigurer<StateV3, EventV3> config) throws Exception {
		config.withConfiguration().listener(listener);
	}

    //TODO: is this actually how we persist state machine?
//	@Bean
//	public StateMachineModelFactory<String, String> modelFactory() {
//		return new RepositoryStateMachineModelFactory(stateRepository, transitionRepository);
//	}
}
