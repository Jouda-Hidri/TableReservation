package orderbird.challenge.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import orderbird.challenge.domain.GuestTable;
import orderbird.challenge.service.TableService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TableControllerTest {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Autowired
	private TableController controller;

	@Mock
	private TableService service;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateTable() throws Exception {
		String url = "/api/v1/table";
		GuestTable guestTable = new GuestTable();
		guestTable.setName("Table with a view to the mountains");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(guestTable);
		when(service.createGuestTable(Mockito.any(GuestTable.class))).thenReturn(1L);

		mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetGuestTableById() throws Exception {
		long guestTableId = 1;
		GuestTable guestTable = new GuestTable();
		guestTable.setId(guestTableId);
		String url = "/api/v1/table/" + guestTableId;
		when(service.getGuestTableById(guestTableId)).thenReturn(guestTable);

		mockMvc.perform(get(url)).andExpect(status().isOk());
	}
}
