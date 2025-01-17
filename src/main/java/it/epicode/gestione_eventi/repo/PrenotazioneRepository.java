package it.epicode.gestione_eventi.repo;

import it.epicode.gestione_eventi.auth.AppUser;
import it.epicode.gestione_eventi.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    List<Prenotazione> findByAppUser(AppUser appUser);

    List<Prenotazione> findByAppUserId(Long appUserID);

    void deleteByAppUserId(Long id);

    boolean existsByEventoId(Long eventoId);
}