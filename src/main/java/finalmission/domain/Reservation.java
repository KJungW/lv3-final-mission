package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Position position;

    @Lob
    @Column(nullable = false)
    private String reason;

    protected Reservation() {
    }

    public Reservation(Member member, Position position, String reason) {
        this.member = member;
        this.position = position;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Position getPosition() {
        return position;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        if (getId() == null || reservation.getId() == null) {
            return false;
        }
        return Objects.equals(id, reservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
