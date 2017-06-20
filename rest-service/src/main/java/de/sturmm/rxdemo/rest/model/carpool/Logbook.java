package de.sturmm.rxdemo.rest.model.carpool;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by sturmm on 06.06.17.
 */
@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Logbook {

    @Id
    private String id;

    @NotNull
    @JsonIgnore
    private String customerId;
    @NotNull
    @JsonIgnore
    private String carId;

    private String name;

}
