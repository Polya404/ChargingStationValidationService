package org.example.chargingstationvalidationservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Connector {
    @NotBlank
    @Id
    private String id;

    @NotBlank
    private String type;

    @Positive
    private int maxPowerKw;

    @ManyToOne
    @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;
}
