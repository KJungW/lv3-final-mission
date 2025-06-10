package finalmission.servcie;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Seat;
import finalmission.dto.layer.ReservationCreationContent;
import finalmission.dto.response.FindAllReservationBySeat;
import finalmission.exception.BadRequestException;
import finalmission.exception.NotFoundException;
import finalmission.repository.MemberRepository;
import finalmission.repository.PositionRepository;
import finalmission.repository.ReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final PositionRepository positionRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            MemberRepository memberRepository,
            PositionRepository positionRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.positionRepository = positionRepository;
    }

    @Transactional(readOnly = true)
    public List<FindAllReservationBySeat> findAllReservationBySeats(long seatId) {
        Seat seat = getSeatById(seatId);
        List<Reservation> reservations = reservationRepository.findAllBySeat(seat);
        return reservations.stream().map(FindAllReservationBySeat::new).toList();
    }

    @Transactional
    public Reservation addReservation(ReservationCreationContent content) {
        Member member = getMemberById(content.memberId());
        Seat seat = getSeatById(content.positionId());

        Reservation reservation = new Reservation(member, seat, content.reason(), content.date());
        validateAlreadyReservation(reservation);
        validateAlreadyReservation(reservation);

        return reservationRepository.save(reservation);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("ID에 해당하는 회원이 존재하지 않습니다."));
    }

    private Seat getSeatById(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow(
                () -> new NotFoundException("ID에 해당하는 자리가 존재하지 않습니다."));
    }

    private void validateAlreadyReservation(Reservation reservation) {
        if (reservationRepository.existsByDate(reservation.getDate())) {
            throw new BadRequestException("이미 예약된 자리에 예약을 시도하고 있습니다.");
        }
    }

    private void validateAddPastReservation(Reservation reservation) {
        if (reservation.isPast()) {
            throw new BadRequestException("과거의 날짜로 예약을 시도하고 있습니다.");
        }

    }
}
