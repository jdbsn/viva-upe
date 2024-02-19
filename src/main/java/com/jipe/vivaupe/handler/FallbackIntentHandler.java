package com.jipe.vivaupe.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class FallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String resposta = "Desculpe, eu não conheço essa.";

        return input.getResponseBuilder()
                .withSpeech(resposta)
                .withSimpleCard("HelloWorld", resposta)
                .withReprompt(resposta)
                .build();
    }

}
