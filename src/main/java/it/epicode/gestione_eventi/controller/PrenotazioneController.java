package it.epicode.gestione_eventi.controller;

import it.epicode.gestione_eventi.auth.AppUserService;
import it.epicode.gestione_eventi.dto.RequestPrenotazione;
import it.epicode.gestione_eventi.entity.Prenotazione;
import it.epicode.gestione_eventi.services.PrenotazioneSvc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prenotazione")
public class PrenotazioneController {
    private final PrenotazioneSvc prenotazioneSvc;
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<Prenotazione>> getAll(){
        return ResponseEntity.ok(prenotazioneSvc.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prenotazioneSvc.findById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{idEv}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Prenotazione> save(@AuthenticationPrincipal UserDetails principal, @PathVariable Long idEv, @Valid @RequestBody RequestPrenotazione r) {
        String name = principal.getUsername();
        return new ResponseEntity<>(prenotazioneSvc.save(name,idEv,r), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        String name = principal.getUsername();
        prenotazioneSvc.delete(id, name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findByUser/{userID}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Prenotazione>> findByUser(@PathVariable Long userID){
        return ResponseEntity.ok(prenotazioneSvc.findByUserId(userID));
    }
}
