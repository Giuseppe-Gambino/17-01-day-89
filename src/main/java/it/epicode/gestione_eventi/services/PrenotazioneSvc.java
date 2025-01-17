package it.epicode.gestione_eventi.services;

import it.epicode.gestione_eventi.auth.AppUser;
import it.epicode.gestione_eventi.auth.AppUserRepository;
import it.epicode.gestione_eventi.dto.RequestPrenotazione;
import it.epicode.gestione_eventi.entity.Evento;
import it.epicode.gestione_eventi.entity.Prenotazione;
import it.epicode.gestione_eventi.repo.EventoRepository;
import it.epicode.gestione_eventi.repo.PrenotazioneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrenotazioneSvc {
    private final PrenotazioneRepository prenotazioneRepo;
    private final AppUserRepository appUserRepo;
    private final EventoRepository eventoRepo;

    public List<Prenotazione> getAll() {
        return prenotazioneRepo.findAll();
    }

    public Optional<Prenotazione> findById(Long id) {
        if(!prenotazioneRepo.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }

        return prenotazioneRepo.findById(id);
    }

    public Prenotazione save(String name, Long idEv, RequestPrenotazione requestPrenotazione) {


        AppUser d = appUserRepo.findByUsername(name)
                .orElseThrow(() -> new EntityNotFoundException("User non trovato"));
        Evento e = eventoRepo.findById(idEv)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));


        Prenotazione p = new Prenotazione();
        BeanUtils.copyProperties(requestPrenotazione, p);
        p.setAppUser(d);
        p.setEvento(e);
        return prenotazioneRepo.save(p);
    }


    public Prenotazione edit(Long id, RequestPrenotazione newPrenotazione) {
        if(!prenotazioneRepo.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }

        Prenotazione existingPrenotazione = prenotazioneRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione con ID " + id + " non trovata"));

        BeanUtils.copyProperties(newPrenotazione, existingPrenotazione);


        return prenotazioneRepo.save(existingPrenotazione);
    }

    public void delete(Long id) {
        if(!prenotazioneRepo.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        prenotazioneRepo.deleteById(id);
    }

    public boolean existsByEventoId(Long id) {
        return prenotazioneRepo.existsByEventoId(id);
    }

    public List<Prenotazione> findByUserId(Long id) {
        return prenotazioneRepo.findByAppUserId(id);
    }
}
