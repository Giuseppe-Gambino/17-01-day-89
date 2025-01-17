package it.epicode.gestione_eventi.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestEvento {

    @NotBlank(message = "Il campo title non può essere vuoto")
    private String title;

    @NotBlank(message = "Il campo description non può essere vuoto")
    private String description;

    @NotNull(message = "Il campo data non può essere nullo")
    @FutureOrPresent(message = "La data deve essere nel futuro o nel presente")
    private LocalDate date;

    @Positive(message = "Il valore deve essere positivo")
    private Integer postiDisponibili;

    @NotBlank(message = "Il campo location non può essere vuoto")
    private String location;
}
