package orderbird.challenge.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import orderbird.challenge.domain.GuestTable;
import orderbird.challenge.domain.GuestTableRepository;
import orderbird.challenge.domain.Reservation;
import orderbird.challenge.domain.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TableServiceTest {
	
	@InjectMocks
	private TableService tableService;

	@Mock
	GuestTableRepository guestTableRepository;

	@Mock
	ReservationRepository reservationRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateReservationWhenConflict() {
		GuestTable guestTable = new GuestTable();
		guestTable.setId(1);
		Reservation reservation = new Reservation();
		reservation.setStartTime(1526155200); // 2018-5-12 20:00:00
		reservation.setEndTime(1526162400); // 2018-5-12 22:00:00
		reservation.setGuestTable(guestTable);
		Reservation reservation2 = new Reservation();
		reservation2.setStartTime(1526158800); // 2018-5-12 21:00:00
		reservation2.setEndTime(1526166000); // 2018-5-12 23:00:00
		Reservation reservation3 = new Reservation();
		reservation3.setStartTime(1526151600);// 2018-5-12 19:00:00
		reservation3.setEndTime(1526158800);// 2018-5-12 21:00:00
		Reservation reservation4 = new Reservation();
		reservation4.setStartTime(1526157000);// 2018-5-12 20:30:00
		reservation4.setEndTime(1526160600);// 2018-5-12 21:30:00
		Reservation reservation5 = new Reservation();
		reservation5.setStartTime(1526151600);// 2018-5-12 19:00:00
		reservation5.setStartTime(1526166000);// 2018-5-12 23:00:00
		when(guestTableRepository.findById(1L)).thenReturn(Optional.of(guestTable));
		when(reservationRepository.save(reservation2)).thenReturn(reservation2);
		when(reservationRepository.save(reservation3)).thenReturn(reservation3);
		when(reservationRepository.save(reservation4)).thenReturn(reservation4);
		when(reservationRepository.save(reservation5)).thenReturn(reservation5);
		
		HttpStatus status2 = tableService.createReservation(1, reservation2);
		HttpStatus status3 = tableService.createReservation(1, reservation3);
		HttpStatus status4 = tableService.createReservation(1, reservation4);
		HttpStatus status5 = tableService.createReservation(1, reservation5);
		
		assertThat (status2, is(HttpStatus.CONFLICT));
		assertThat (status3, is(HttpStatus.CONFLICT));
		assertThat (status4, is(HttpStatus.CONFLICT));
		assertThat (status5, is(HttpStatus.CONFLICT));
	}

	@Test
	public void testCreateReservationWhenSuccess() {
		GuestTable guestTable = new GuestTable();
		guestTable.setId(1);
		Reservation reservation = new Reservation();
		reservation.setStartTime(1526155200); // 2018-5-12 20:00:00
		reservation.setEndTime(1526158800); // 2018-5-12 21:00:00
		reservation.setGuestTable(guestTable);
		Reservation reservation2 = new Reservation();
		reservation2.setStartTime(1526158800); // 2018-5-12 21:00:00
		reservation2.setEndTime(1526166000); // 2018-5-12 23:00:00
		Reservation reservation3 = new Reservation();
		reservation3.setStartTime(1526151600); // 2018-5-12 19:00:00
		reservation3.setEndTime(1526155200); // 2018-5-12 20:00:00

		when(guestTableRepository.findById(1L)).thenReturn(Optional.of(guestTable));
		when(reservationRepository.save(reservation2)).thenReturn(reservation2);
		when(reservationRepository.save(reservation3)).thenReturn(reservation3);
		
		HttpStatus status2 = tableService.createReservation(1, reservation2);
		HttpStatus status3 = tableService.createReservation(1, reservation3);
		
		assertThat (status2, is(HttpStatus.CREATED));
		assertThat (status3, is(HttpStatus.CREATED));
	}

}
