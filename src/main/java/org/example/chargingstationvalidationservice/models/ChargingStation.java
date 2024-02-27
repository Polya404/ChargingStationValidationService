package org.example.chargingstationvalidationservice.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStation {
    @NotBlank
    @Id
    private String id;
    private String title;
    private String description;
    @Size(min = 10)
    @Pattern(regexp = ".*\\d.*")
    private String address;
    @Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
    private String geoCoordinates;
    @NotNull
    private Boolean isPublic;

    @Size(min = 1, max = 8)
    @OneToMany(mappedBy = "chargingStation", cascade = CascadeType.ALL)
    private List<Connector> connectors;
}
