package orderbird.challenge.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import orderbird.challenge.domain.GuestTable;
import orderbird.challenge.domain.Reservation;
import orderbird.challenge.service.TableService;

@RestController
@RequestMapping(value = "/api/v1")
public class TableController {

	@Autowired
	TableService service;

	@RequestMapping(value = "/table", method = RequestMethod.POST)
	public long create(@RequestBody GuestTable guestTable) {
		if (guestTable != null) {
			GuestTable created = service.create(guestTable);
			return created.getId();
		}
		return 0L;
	}

	/**
	 * Returns the response status depending whether the reservation was successful
	 * or not. NO_CONTENT : no reservation received, NOT_FOUND : table not found,
	 * CONFLICT : table reserved at that time, CREATED : in case the reservation was
	 * Successful
	 * 
	 * @param table-id
	 * @param reservation
	 * @return the HTTP status
	 */
	
	@RequestMapping(value = "/table/{id}/reservation", method = RequestMethod.POST)
	public ResponseEntity<Void> makeReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
		if(reservation != null) {
			HttpStatus httpStatus = service.createReservation(id, reservation);
			return new ResponseEntity<Void>(httpStatus);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/table/{id}", method = RequestMethod.GET)
	public Collection<Reservation> getReservationsList(@PathVariable("id") long id) {
		return service.getReservationsByGuestTableId(id);
	}
}