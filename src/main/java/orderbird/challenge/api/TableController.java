package orderbird.challenge.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/table/{id}/reservation", method = RequestMethod.POST)
	public void makeReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
		if(reservation != null) {
			service.createReservation(id, reservation);
		}
	}

	@RequestMapping(value = "/table/{id}", method = RequestMethod.GET)
	public Collection<Reservation> getReservationsList(@PathVariable("id") long id) {
		return service.getReservationsByGuestTableId(id);
	}
}