package br.com.medibot.controller;

import br.com.medibot.conversation.ConversationHelper;
import br.com.medibot.domain.Conversation;
import br.com.medibot.domain.ConversationRequest;
import br.com.medibot.domain.Symptom;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class ConversationController {

    @Autowired
    private ConversationHelper helper;
    private Map<String, List<Symptom>> symptoms;
    private Map<String, String> medicalSpecialties;

    private void loadMap() {
        if(Objects.isNull(symptoms)) {
            symptoms = new HashMap<>();
        }
        List<Symptom> l = new ArrayList<>();
        l.add(new Symptom("inchaco", "Inchaço"));
        l.add(new Symptom("queimacao", "Queimação"));
        l.add(new Symptom("trauma", "Trauma"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("dornaperna", l);

        l = new ArrayList<>();
        l.add(new Symptom("ficaquente", "Aumento de temperatura na área dolorida"));
        l.add(new Symptom("imobilismo", "Ficou muito tempo imóvel"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("inchaco", l);

        l = new ArrayList<>();
        l.add(new Symptom("perdasensibilidade", "Perda de Sensibilidade"));
        l.add(new Symptom("formigamento", "Formigamento"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("queimacao", l);

        l = new ArrayList<>();
        l.add(new Symptom("lesao", "Lesão"));
        l.add(new Symptom("praticaesportes",  "Prática de Esportes"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("trauma", l);

        l = new ArrayList<>();
        l.add(new Symptom("imobilismo", "Ficou muito tempo imóvel"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("ficaquente",  l);

        l = new ArrayList<>();
        l.add(new Symptom("formigamento", "Formigamento"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("perdasensibilidade",  l);

        l = new ArrayList<>();
        l.add(new Symptom("praticaesportes", "Prática de esportes"));
        l.add(new Symptom("nao", "Não"));
        symptoms.put("lesao",  l);

        if(Objects.isNull(medicalSpecialties)) {
            medicalSpecialties = new HashMap<>();
        }
        medicalSpecialties.put("imobilismo", "Sugerimos que consulte o especialista Angiologista. " +
                "Temos os seguintes horários disponíveis");
        medicalSpecialties.put("perdasensibilidade", "Sugerimos que consulte o especialista Neurologista. " +
                "Temos os seguintes horários disponíveis");
        medicalSpecialties.put("praticaesportes", "Sugerimos que consulte o especialista Ortopedista. " +
                "Temos os seguintes horários disponíveis");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Conversation> talk(
            @RequestBody ConversationRequest req) {
        loadMap();
        Conversation c = new Conversation();
        if(Objects.isNull(req.getSymptons())) {
            MessageResponse r = helper.teste(req.getMessage(), req.getContext());
            c.setMessage(((List<String>)r.getOutput().get("text")).stream().findFirst().orElse(StringUtils.EMPTY));
            c.setContext(r.getContext());
            if(r.getEntities().stream().count() > 0) {
                String entidade = r.getEntities().stream().findFirst().get().getValue();
                if("dornaperna".equalsIgnoreCase(entidade)) {
                    c.setSymptons(symptoms.get(entidade));
                    c.setMessage("OK, entendi! Você possui algum destes sintomas?");
                }
            }
        } else {
            if(req.getSymptons().equalsIgnoreCase("nao")) {
                c.setMessage("Por favor entre em contato com nossa Central de Atendimento " +
                        "através do telefone (66) 6666-6666. Obrigado");
            } else {
                c.setMessage("OK, entendi! Você possui algum destes outros sintomas?");
                if (Objects.nonNull(symptoms.get(req.getSymptons()))) {
                    c.setSymptons(symptoms.get(req.getSymptons()));
                } else {
                    if(req.getSymptons().equals("1") || req.getSymptons().equals("2")
                            || req.getSymptons().equals("3")) {
                        c.setMessage("Agendamento Realizado com Sucesso!");
                    } else {
                        c.setMessage(medicalSpecialties.get(req.getSymptons()));
                        List<Symptom> l = new ArrayList<>();
                        l.add(new Symptom("1", "22/02 - 12h (quinta-feira)"));
                        l.add(new Symptom("2", "22/02 - 13h (quinta-feira)"));
                        l.add(new Symptom("3", "27/02 - 11h (segunda-feira)"));
                        c.setSymptons(l);
                    }
                }
            }
        }
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

}
