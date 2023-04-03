package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.ProdusRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.ProdusDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.ProdusService;
import ro.unibuc.hello.service.ProdusService;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ProdusControllerTest {

    @Mock
    private ProdusService produsService;

    @Mock
    private ProdusRepository repo;

    @InjectMocks
    private ProdusController produsController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(produsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_getProdus() throws Exception {
        // Arrange
        ProdusDTO produs= new ProdusDTO("1","nume","pret");

        when(produsService.getProdus(any())).thenReturn(produs);

        // Act
        MvcResult result = mockMvc.perform(get("/getProdus?id=1&nume=nume&pret=pret")
                .content(objectMapper.writeValueAsString(produs))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(produs));
    }
    // @Test
    // public void createProdus() throws Exception {
    //     ProdusDTO produsDto = new ProdusDTO("1", "nume", "pret");
    //     Produs produs=new Produs("1", "nume", "pret");
    //     // studentService.addCourse to respond back with mockCourse
    //     Mockito.when(produsService.createProdus(
    //             Mockito.any(ProdusDTO.class))).thenReturn(produs);
    //     ProdusDTO produsDTO = produsService.getProdus(produsDto.getId());

    //     Assertions.assertEquals(produsDto.getNume(),produsDTO.getNume());
    // }
}