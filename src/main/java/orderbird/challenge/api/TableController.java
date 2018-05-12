package orderbird.challenge.api;

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
	public long createTable(@RequestBody GuestTable guestTable) {
		return service.createGuestTable(guestTable);
	}

	@RequestMapping(value = "/table/{id}/reservation", method = RequestMethod.POST)
	public ResponseEntity<Void> makeReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
		 HttpStatus status = service.createReservation(id, reservation);
		 return new ResponseEntity<Void>(status);
	}

	@RequestMapping(value = "/table/{id}", method = RequestMethod.GET)
	public GuestTable getGuestTableById(@PathVariable("id") long id) {
		return service.getGuestTableById(id);
	}
}