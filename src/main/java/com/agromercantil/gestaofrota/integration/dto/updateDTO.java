package com.agromercantil.gestaofrota.integration.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TruckUpdateDTO {

    @NotNull
    @Size(min = 1, max = 100)
    private String brand;

    @NotNull
    @Size(min = 1, max = 100)
    private String model;
}
