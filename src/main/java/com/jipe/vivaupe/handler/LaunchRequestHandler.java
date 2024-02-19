package com.jipe.vivaupe.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String resposta = "Bem vindo ao Viva U.P.E.! Funcionando através de uma api Spring boot.";

        return input.getResponseBuilder()
                .withSpeech(resposta)
                .withSimpleCard("HelloWorld", resposta)
                .withReprompt(resposta)
                .build();
    }
}
