package orderbird.challenge.domain;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GuestTable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "guest_table_id")
	long id;
	
	String name;
	
	@OneToMany(mappedBy = "guestTable", cascade = CascadeType.ALL)
	Collection<Reservation> reservations = new LinkedHashSet<Reservation>();

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addReservation(Reservation reservation) {
		if (reservations == null) {
			reservations = new LinkedHashSet<Reservation>();
		}
		reservations.add(reservation);
		if (reservation.getGuestTable() != this) {
			reservation.setGuestTable(this);
		}
	}
	
	public Collection<Reservation> getReservations() {
		return reservations;
	}
	
	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}
}