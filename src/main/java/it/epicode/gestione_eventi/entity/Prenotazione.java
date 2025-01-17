package it.epicode.gestione_eventi.entity;

import it.epicode.gestione_eventi.auth.AppUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "prenotazioni")
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

}
