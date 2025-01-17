package it.epicode.gestione_eventi.repo;

import it.epicode.gestione_eventi.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}