package org.example.chargingstationvalidationservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Response {
    @Id
    private UUID id;
    private String idStation;
    @ElementCollection
    private List<String> idConnectors;
    @Column(columnDefinition = "TEXT")
    private String message = "";

    public void setMessage(StringBuilder message) {
            this.message += message.toString();
    }
}
