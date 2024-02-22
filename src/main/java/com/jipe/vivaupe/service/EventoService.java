package com.jipe.vivaupe.service;


import com.jipe.vivaupe.dto.EventoDTO;
import com.jipe.vivaupe.util.HttpRequestUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    /**
     * @param data
     * @return
     */
    public String existingEvents(String data){
        try {
            List<EventoDTO> evento = HttpRequestUtil.fazerRequisicao(data);

            List<String> eventos = new ArrayList<>();

            evento.forEach(e -> eventos.add(e.getNome()));

            if (eventos.size() > 1) {
                return "Os eventos de " + data + " são " + eventos.toString();
            } else if (eventos.size() == 1) {
                return "O único evento de " + data + " é " + eventos.toString();
            } else{
                return "Não existem eventos na UPE em " + data;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ops, houve algum erro. Tente de novo";

    }


}
