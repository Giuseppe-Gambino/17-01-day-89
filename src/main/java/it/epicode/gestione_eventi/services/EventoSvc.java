package it.epicode.gestione_eventi.services;

import it.epicode.gestione_eventi.auth.AppUser;
import it.epicode.gestione_eventi.auth.AppUserRepository;
import it.epicode.gestione_eventi.dto.RequestEvento;
import it.epicode.gestione_eventi.entity.Evento;
import it.epicode.gestione_eventi.exceptions.UserNotAuthorizedException;
import it.epicode.gestione_eventi.repo.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
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


    public Evento edit(Long id, RequestEvento newEvento, String name) {

        Evento existingEvento = eventoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("L'Evento con ID " + id + " non trovato"));

        AppUser user = appUserRepo.findByUsername(name)
                .orElseThrow(() -> new EntityNotFoundException("User non trovato"));

        Long idOrgEvent = existingEvento.getOrganizerId().getId();

        if (!Objects.equals(idOrgEvent, user.getId())){
            throw new UserNotAuthorizedException("Non hai i permessi per modificare quest'evento, puoi modificare soltanto i tuoi eventi");
        }


        BeanUtils.copyProperties(newEvento, existingEvento);


        return eventoRepo.save(existingEvento);
    }



    public void delete(Long id) {
        if(!eventoRepo.existsById(id)) {
            throw new EntityNotFoundException("L'Evento non è stato trovato");
        }


        eventoRepo.deleteById(id);
    }
}
