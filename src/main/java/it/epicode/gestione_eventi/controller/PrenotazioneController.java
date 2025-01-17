package it.epicode.gestione_eventi.controller;

import it.epicode.gestione_eventi.dto.RequestPrenotazione;
import it.epicode.gestione_eventi.entity.Prenotazione;
import it.epicode.gestione_eventi.services.PrenotazioneSvc;
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
@RequestMapping("/prenotazione")
public class PrenotazioneController {
    private final PrenotazioneSvc prenotazioneSvc;

    @GetMapping
    public ResponseEntity<List<Prenotazione>> getAll(){
        return ResponseEntity.ok(prenotazioneSvc.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prenotazioneSvc.findById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{idUser}/{idEv}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Prenotazione> save(@PathVariable Long idUser, @PathVariable Long idEv, @Valid @RequestBody RequestPrenotazione r) {
        return new ResponseEntity<>(prenotazioneSvc.save(idUser,idEv,r), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody RequestPrenotazione d) {
        return ResponseEntity.ok(prenotazioneSvc.edit(id, d));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prenotazioneSvc.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findByUser/{userID}")
    public ResponseEntity<List<Prenotazione>> findByUser(@PathVariable Long userID){
        return ResponseEntity.ok(prenotazioneSvc.findByUserId(userID));
    }
}
