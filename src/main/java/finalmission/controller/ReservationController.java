package finalmission.controller;

import finalmission.annotation.AccessToken;
import finalmission.domain.Reservation;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.dto.layer.ReservationCreationContent;
import finalmission.dto.request.AddReservationRequest;
import finalmission.servcie.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<Void> addReservation(
            @AccessToken AccessTokenContent accessToken,
            @Valid @RequestBody AddReservationRequest request
    ) {
        ReservationCreationContent creationContent = new ReservationCreationContent(accessToken.memberId(), request);
        Reservation reservation = reservationService.addReservation(creationContent);
        return ResponseEntity.created(URI.create("reservation/" + reservation.getId())).build();
    }
}
