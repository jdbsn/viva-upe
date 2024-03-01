package com.jipe.vivaupe.service;


import com.jipe.vivaupe.dto.EventoDTO;
import com.jipe.vivaupe.util.HttpRequestUtil;
import com.jipe.vivaupe.util.Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EventoService {

    private static final Logger logger = Logger.getLogger(EventoService.class.getName());

    /**
     * @param data
     * @return
     */
    public String existingEvents(String data){
        try {
            String dataFormatada = "&timeMin=" + Util.converterData(data);
            List<EventoDTO> evento = HttpRequestUtil.fazerRequisicao(dataFormatada);

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
            logger.log(Level.WARNING, "Ocorreu um erro no método existingEvents().", e);
        }
        return "Ops, houve algum erro. Tente de novo";

    }

    public String detailingEvents(String events){
        try{
            String nome = "&q=" + events;
            List<EventoDTO> eventos = HttpRequestUtil.fazerRequisicao(nome);

            if (!eventos.isEmpty()){
                EventoDTO evento = eventos.get(0);
                return ("O evento: "+ evento.getNome()+ ". Na data: "+ evento.getHora().getHora().substring(0, 10)
                        + ". Possui a seguinte descrição: " + evento.getDescricao());
            }else{
                return ("Não encontrei "+ events + " registrado no google Calendar da U P E .");
            }
        } catch (Exception e){
            logger.log(Level.WARNING, "Ocorreu um erro no método detailingEvents().", e);
        }
        return "Ops, houve algum erro. Tente de novo";
    }

}
