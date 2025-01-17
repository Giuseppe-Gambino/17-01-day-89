package it.epicode.gestione_eventi.services;

import it.epicode.gestione_eventi.auth.AppUser;
import it.epicode.gestione_eventi.auth.AppUserRepository;
import it.epicode.gestione_eventi.dto.RequestEvento;
import it.epicode.gestione_eventi.entity.Evento;
import it.epicode.gestione_eventi.repo.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoSvc {
    private final EventoRepository eventoRepo;
    private final AppUserRepository appUserRepo;

    public List<Evento> getAll() {
        return eventoRepo.findAll();
    }

    public Optional<Evento> findById(Long id) {
        if(!eventoRepo.existsById(id)) {
            throw new EntityNotFoundException("L'Evento non è stato trovato");
        }

        return eventoRepo.findById(id);
    }

    public Evento save(RequestEvento requestEvento, String name) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(requestEvento, evento);
        AppUser user = appUserRepo.findByUsername(name)
                .orElseThrow(() -> new EntityNotFoundException("User non trovato"));
        evento.setOrganizerId(user);
        return eventoRepo.save(evento);
    }


    public Evento edit(Long id, RequestEvento newEvento) {

        Evento existingEvento = eventoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("L'Evento con ID " + id + " non trovato"));

        BeanUtils.copyProperties(newEvento, existingEvento);


        return eventoRepo.save(existingEvento);
    }



    public void delete(Long id) {
        if(!eventoRepo.existsById(id)) {
            throw new EntityNotFoundException("L'Evento non è stato trovato");
        }

//        if (prenotazioneSvc.existsByEventoId(id)){
//            throw new IllegalStateException("Non puoi eliminare un viaggio con delle prenotazioni in corso, disdici prima le prenotazioni");
//        }

        eventoRepo.deleteById(id);
    }
}
