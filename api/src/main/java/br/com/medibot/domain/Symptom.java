package br.com.medibot.domain;

public class Symptom {

    private String Id;
    private String description;

    public Symptom() {}

    public Symptom(String id, String description) {
        Id = id;
        this.description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
