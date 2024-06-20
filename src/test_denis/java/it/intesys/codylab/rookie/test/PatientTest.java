package it.intesys.codylab.rookie.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.intesys.rookie.App;
import it.intesys.rookie.domain.BloodGroup;
import it.intesys.rookie.dto.BloodGroupDTO;
import it.intesys.rookie.dto.PatientDTO;
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

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Transactional
public class PatientTest {
    public static final Long OPD = 1L;
    public static final Long IDP = 1L;
    public static final String NAME = "Carlo";
    public static final String SURNAME = "Marchiori";
    public static final String PHONE_NUMBER = "6666666666";
    public static final String ADDRESS = "Via Roveggia";
    public static final String EMAIL = "carlo.marchiori@intesys.it";
    public static final String AVATAR = "base64 image";
    public static final BloodGroup BLOOD_GROUP = BloodGroup.A_PLUS;
    public static final String NOTES = "IT";
    public static final Boolean CHRONICPATIENT = false;
    public static final Instant DATE = Instant.now();

    private static final long TIME_TOLERANCE_MS = 10000; // Tolleranza di 1 secondo


    @Autowired
    private WebApplicationContext applicationContext;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreatePatient() throws Exception {
        PatientDTO patient = createPatient();

        assertEquals(OPD, patient.getOpd());
        assertEquals(IDP, patient.getIdp());
        assertEquals(NAME, patient.getName());
        assertEquals(SURNAME, patient.getSurname());
        assertEquals(PHONE_NUMBER, patient.getPhoneNumber());
        assertEquals(ADDRESS, patient.getAddress());
        assertEquals(EMAIL, patient.getEmail());
        assertEquals(AVATAR, patient.getAvatar());
        assertEquals(BLOOD_GROUP, convertToBloodGroup(patient.getBloodGroup()));
        assertEquals(NOTES, patient.getNotes());
        assertEquals(CHRONICPATIENT, patient.getChronicPatient());
        assertTrue(Duration.between(DATE, patient.getLastAdmission()).abs().toMillis() <= TIME_TOLERANCE_MS,
                "The difference between expected and actual dates exceeds tolerance");
    }

    private PatientDTO createPatient() throws Exception {
        PatientDTO patient = new PatientDTO();
        patient.setPhoneNumber(PHONE_NUMBER);
        patient.setOpd(OPD);
        patient.setIdp(IDP);
        patient.setName(NAME);
        patient.setSurname(SURNAME);
        patient.setEmail(EMAIL);
        patient.setAddress(ADDRESS);
        patient.setAvatar(AVATAR);
        patient.setNotes(NOTES);
        patient.setChronicPatient(CHRONICPATIENT);
        patient.setBloodGroup(BloodGroupDTO.valueOf(BLOOD_GROUP.name()));
        patient.setLastAdmission(DATE);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient))).andReturn().getResponse();
        assertEquals(200, response.getStatus());

        return objectMapper.readValue(response.getContentAsString(), PatientDTO.class);
    }

    @Test
    public void testGetPatient() throws Exception {
        createPatient();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(200, response.getStatus());

        PatientDTO patient = objectMapper.readValue(response.getContentAsString(), PatientDTO.class);

        assertEquals(OPD, patient.getOpd());
        assertEquals(IDP, patient.getIdp());
        assertEquals(NAME, patient.getName());
        assertEquals(SURNAME, patient.getSurname());
        assertEquals(PHONE_NUMBER, patient.getPhoneNumber());
        assertEquals(ADDRESS, patient.getAddress());
        assertEquals(EMAIL, patient.getEmail());
        assertEquals(AVATAR, patient.getAvatar());
        assertEquals(BLOOD_GROUP, convertToBloodGroup(patient.getBloodGroup()));
        assertEquals(NOTES, patient.getNotes());
        assertEquals(CHRONICPATIENT, patient.getChronicPatient());
        assertTrue(Duration.between(DATE, patient.getLastAdmission()).abs().toMillis() <= TIME_TOLERANCE_MS,
                "The difference between expected and actual dates exceeds tolerance");
    }

    private BloodGroup convertToBloodGroup(BloodGroupDTO bloodGroupDTO) {
        return BloodGroup.valueOf(bloodGroupDTO.name());
    }
}
