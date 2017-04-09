package br.com.medibot.domain;

import java.util.List;
import java.util.Map;

public class Conversation {

    private String messages;
    private List<Symptom> symptons;
    private Map<String, Object> context;

    public String getMessage() {
        return messages;
    }

    public void setMessage(String messages) {
        this.messages = messages;
    }

    public List<Symptom> getSymptons() {
        return symptons;
    }

    public void setSymptons(List<Symptom> symptons) {
        this.symptons = symptons;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}
