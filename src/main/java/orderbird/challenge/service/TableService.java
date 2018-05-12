package orderbird.challenge.service;

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

	public long createGuestTable(GuestTable table) {
		GuestTable created = guestTableRepository.save(table);
		if (created != null) {
			return created.getId();
		}
		return 0L;
	}

	/**
	 * Returns the response status depending whether the reservation was successful
	 * or not. CREATED : in case the reservation was created, CONFLICT : table
	 * reserved at that time, NOT_FOUND: table not found or INTERNAL_SERVER_ERROR :
	 * in case persisting the reservation failed
	 * 
	 * @param table-id
	 * @param reservation
	 * @return the HTTP status
	 */
	public HttpStatus createReservation(long guestTableId, Reservation reservation) {
		Optional<GuestTable> guestTable = guestTableRepository.findById(guestTableId);
		if (guestTable.isPresent()) {
			if (!isReserved(guestTable.get(), //
					reservation.getStartTime(), //
					reservation.getEndTime())) {
				log.info("Reservation : " + reservation);
				reservation.setGuestTable(guestTable.get());
				Reservation created = reservationRepository.save(reservation);
				if(created != null) {
					return HttpStatus.CREATED;
				}
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return HttpStatus.CONFLICT;
		}
		return HttpStatus.NOT_FOUND;
	}

	public GuestTable getGuestTableById(long guestTableId) {
		Optional<GuestTable> guestTable = guestTableRepository.findById(guestTableId);
		if (guestTable.isPresent()) {
			return guestTable.get();
		}
		return null;
	}

	private boolean isReserved(GuestTable guestTable, long startTime, long endTime) {
		Collection<Reservation> reservations = guestTable.getReservations();

		List<Reservation> conflictsList = reservations.stream() //
				.filter(r -> (r.getStartTime() < endTime //
						&& r.getEndTime() > startTime)//
						|| (r.getStartTime() < startTime //
								&& r.getEndTime() > endTime)
						|| (r.getStartTime() > startTime //
								&& r.getEndTime() < endTime)//
						|| (r.getStartTime() == startTime //
								&& r.getEndTime() == endTime)) //
				.collect(Collectors.toList());
		
		if (conflictsList.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
