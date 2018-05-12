package orderbird.challenge.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orderbird.challenge.domain.GuestTable;
import orderbird.challenge.domain.GuestTableRepository;
import orderbird.challenge.domain.Reservation;
import orderbird.challenge.domain.ReservationRepository;

@Service
public class TableService {

	private static final Logger log = LoggerFactory.getLogger(TableService.class);

	@Autowired
	GuestTableRepository guestTableRepository;

	@Autowired
	ReservationRepository reservationRepository;

	public GuestTable create(GuestTable table) {
		return guestTableRepository.save(table);
	}

	public void createReservation(long guestTableId, Reservation reservation) {
		Optional<GuestTable> guestTable = guestTableRepository.findById(guestTableId);
		if (guestTable.isPresent()) {
			// TODO check if there is a CONFLICT
			log.info("Reservation : " + reservation);
			reservation.setGuestTable(guestTable.get());
			reservationRepository.save(reservation);
		}
	}

	public Collection<Reservation> getReservationsByGuestTableId(long guestTableId) {
		Optional<GuestTable> guestTable = guestTableRepository.findById(guestTableId);
		if (guestTable.isPresent()) {
			//FIXME
			return guestTable.get().getReservations();
		}
		return null;
	}
}
