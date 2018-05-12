package orderbird.challenge.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reservation_id")
	long id;

	String customerName;
	
	Timestamp startTime;
	
	Timestamp endTime;
	
	@ManyToOne
	@JoinColumn(name = "guest_table_id")
	private GuestTable guestTable;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public GuestTable getGuestTable() {
		return guestTable;
	}

	public void setGuestTable(GuestTable guestTable) {
		this.guestTable = guestTable;
		if (!guestTable.getReservations().contains(this)) {
			guestTable.getReservations().add(this);
		}
	}
	
	@Override
	public String toString() {
		return "customer "+customerName+" from: "+startTime+" to: "+endTime;
	}
}
