package it.intesys.codylab.rookie.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.intesys.openhospital.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import it.intesys.openhospital.dto.DoctorDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Transactional
public class DoctorTest {
    public static final String ADDRESS = "Via Roveggia";
    public static final String AVATAR = "base64 image";
    public static final String EMAIL = "carlo.marchiori@intesys.it";
    public static final String NAME = "Carlo";
    public static final String PHONE_NUMBER = "6666666666";
    public static final String PROFESSION = "IT";
    public static final String SURNAME = "Marchiori";
    @Autowired
    private WebApplicationContext applicationContext;
    private  ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }


    @Test
    public void testCreateDoctor() throws Exception {
        DoctorDTO doctor = createDoctor();

        assertEquals(doctor.getAddress(), ADDRESS);
        assertEquals(doctor.getAvatar(), AVATAR);
        assertEquals(doctor.getEmail(), EMAIL);
        assertEquals(doctor.getName(), NAME);
        assertEquals(doctor.getPhoneNumber(), PHONE_NUMBER);
        assertEquals(doctor.getProfession(), PROFESSION);
        assertEquals(doctor.getSurname(), SURNAME);
    }

    private DoctorDTO createDoctor () throws Exception {
        DoctorDTO doctor = new DoctorDTO();
        doctor.setAddress(ADDRESS);
        doctor.setAvatar(AVATAR);
        doctor.setEmail(EMAIL);
        doctor.setName(NAME);
        doctor.setPhoneNumber(PHONE_NUMBER);
        doctor.setProfession(PROFESSION);
        doctor.setSurname(SURNAME);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor))).andReturn().getResponse();
        assertEquals(response.getStatus(), 200);

        return objectMapper.readValue(response.getContentAsString(), DoctorDTO.class);
    }


    @Test
    public void testGetDoctor() throws Exception {
        createDoctor();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(response.getStatus(), 200);

        DoctorDTO doctor = objectMapper.readValue(response.getContentAsString(), DoctorDTO.class);

        assertEquals(doctor.getAddress(), ADDRESS);
        assertEquals(doctor.getAvatar(), AVATAR);
        assertEquals(doctor.getEmail(), EMAIL);
        assertEquals(doctor.getName(), NAME);
        assertEquals(doctor.getPhoneNumber(), PHONE_NUMBER);
        assertEquals(doctor.getProfession(), PROFESSION);
        assertEquals(doctor.getSurname(), SURNAME);
    }
}
