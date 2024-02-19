package com.jipe.vivaupe.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String texto = "Como posso ajuda?";

        return input.getResponseBuilder()
                .withSpeech(texto)
                .withSimpleCard("HelloWorld", texto)
                .withReprompt(texto)
                .build();
    }

}
