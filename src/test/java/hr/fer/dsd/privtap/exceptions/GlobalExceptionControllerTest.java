package hr.fer.dsd.privtap.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.dsd.privtap.exception.ResourceNotFoundException;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.rest.ActionController;
import hr.fer.dsd.privtap.rest.TriggerController;
import hr.fer.dsd.privtap.rest.UserController;
import hr.fer.dsd.privtap.service.ActionService;
import hr.fer.dsd.privtap.service.TriggerService;
import hr.fer.dsd.privtap.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private TriggerService triggerService;

    @InjectMocks
    private TriggerController triggerController;

    @Mock
    private ActionService actionService;

    @InjectMocks
    private ActionController actionController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController, triggerController, actionController)
                .setControllerAdvice(new GlobalExceptionController())
                .build();
    }

    @Test
    void handleNoDataFoundExceptionForTriggerTest() throws Exception {
        when(triggerService.findById("1")).thenThrow(new NoTriggerFoundException("Trigger with id 1 does not exist"));

        mockMvc.perform(get("/trigger/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertNotNull(result.getResolvedException()))
                .andExpect(result -> assertEquals("Trigger with id 1 does not exist",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void handleNoDataFoundExceptionForActionTest() throws Exception {
        when(actionService.findById("1")).thenThrow(new NoActionFoundException("Action with id 1 does not exist"));

        mockMvc.perform(get("/action/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertNotNull(result.getResolvedException()))
                .andExpect(result -> assertEquals("Action with id 1 does not exist",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void handleMethodArgumentNotValidForTriggerTest() throws Exception {
        Trigger trigger = new Trigger(null, null, null, null, null, null,
                null, null, null, null);

        mockMvc.perform(post("/trigger")
                        .content(objectMapper.writeValueAsString(trigger))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertNotNull(result.getResolvedException()));
    }

    @Test
    void handleMethodArgumentNotValidForActionTest() throws Exception {
        Action action = new Action(null, null, null, null, null, null,
                null, null, null, null, null);

        mockMvc.perform(post("/action")
                        .content(objectMapper.writeValueAsString(action))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertNotNull(result.getResolvedException()));
    }

}
