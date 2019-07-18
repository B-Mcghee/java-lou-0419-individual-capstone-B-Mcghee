package com.tsg.superherosighting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private int id;
    @NotBlank(message = "name must not be blank")
    @Size(max = 30, message="First name must be less than 30 characters.")
    private String name;
    @Size(max = 250, message="First name must be less than 250 characters.")
    private String description;
    @Size(max = 250, message="First name must be less than 250 characters.")
    private String address;
    @NotBlank(message = "name must not be blank")
    private BigDecimal latitude;
    @NotBlank(message = "name must not be blank")
    private BigDecimal longitude;

}
