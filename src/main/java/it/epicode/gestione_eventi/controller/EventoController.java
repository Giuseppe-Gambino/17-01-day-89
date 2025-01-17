package it.epicode.gestione_eventi.controller;

import it.epicode.gestione_eventi.dto.RequestEvento;
import it.epicode.gestione_eventi.entity.Evento;
import it.epicode.gestione_eventi.services.EventoSvc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evento")
public class EventoController {
    private final EventoSvc eventoSvc;

    @GetMapping("/eventoAll")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Evento>> getAll(){
        return ResponseEntity.ok(eventoSvc.getAll());
    }



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventoSvc.findById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Evento> save(@Valid @RequestBody RequestEvento requestEvento) {
        return new ResponseEntity<>(eventoSvc.save(requestEvento), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody RequestEvento d) {
        return ResponseEntity.ok(eventoSvc.edit(id, d));
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventoSvc.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
