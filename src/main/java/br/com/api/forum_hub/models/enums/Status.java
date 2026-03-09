package br.com.api.forum_hub.models.enums;

public enum Status {
    AWAITING_RESPONSE("Aguardando Resposta"),
    REPLIED("Respondido");

    private String statusPortugues;

    Status(String statusPortugues){
        this.statusPortugues = statusPortugues;
    }

    public String getStatusPortugues() {
        return statusPortugues;
    }
}
