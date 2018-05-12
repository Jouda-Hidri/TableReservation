package orderbird.challenge.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	public HttpStatus createReservation(long guestTableId, Reservation reservation) {
		Optional<GuestTable> guestTable = guestTableRepository.findById(guestTableId);
		
		if (guestTable.isPresent()) {

			if (!isReserved(guestTable.get(), //
					reservation.getStartTime(), //
					reservation.getEndTime())) {
		
				log.info("Reservation : " + reservation);
				reservation.setGuestTable(guestTable.get());
				reservationRepository.save(reservation);
				return HttpStatus.CREATED;
			
			}
			
			return HttpStatus.CONFLICT;
		}
		
		return HttpStatus.NOT_FOUND;
	}

	private boolean isReserved(GuestTable guestTable, Timestamp startTime, Timestamp endTime) {
		Collection<Reservation> reservations = guestTable.getReservations();

		List<Reservation> conflictsList = reservations.stream() //
				.filter(r -> r.getStartTime().before(endTime) //
						&& r.getEndTime().after(startTime)) //
				.collect(Collectors.toList());
		
		if (conflictsList.isEmpty()) {
			return false;
		}
		
		return true;
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
