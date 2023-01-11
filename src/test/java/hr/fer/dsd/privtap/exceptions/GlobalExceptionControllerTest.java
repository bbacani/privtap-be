package hr.fer.dsd.privtap.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.rest.*;
import hr.fer.dsd.privtap.service.ActionService;
import hr.fer.dsd.privtap.service.PlatformService;
import hr.fer.dsd.privtap.service.ServiceProviderService;
import hr.fer.dsd.privtap.service.TriggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionControllerTest {

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

    @Mock
    private ServiceProviderService serviceProviderService;

    @InjectMocks
    private ServiceProviderController serviceProviderController;

    @Mock
    private PlatformService platformService;

    @InjectMocks
    private PlatformController platformController;

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

    @Test
    public void testRegisterPlatform() {
        String providerId = "provider1";
        Platform platform = new Platform();
        platform.setName("test_platform");

        when(serviceProviderService.registerPlatform(providerId, platform)).thenReturn(platform);

        Platform result = serviceProviderController.registerPlatform(providerId, platform);

        assertEquals(platform, result);
        verify(serviceProviderService).registerPlatform(providerId, platform);
    }

    @Test
    public void testGetPlatform() {
        String providerId = "provider1";
        Platform platform = new Platform();
        platform.setName("test_platform");

        when(serviceProviderService.getPlatform(providerId)).thenReturn(platform);

        Platform result = serviceProviderController.getPlatform(providerId);

        assertEquals(platform, result);
        verify(serviceProviderService).getPlatform(providerId);
    }

    @Test
    public void testRegisterActionType() {
        String platformName = "test_platform";
        ActionType actionType = new ActionType();
        actionType.setName("Test Action");
        actionType.setDescription("This is a test action");
        Platform platform = new Platform();
        platform.setName(platformName);
        when(serviceProviderService.registerActionType(platformName, actionType)).thenReturn(platform);

        Platform result = serviceProviderController.registerActionType(platformName, actionType);

        assertEquals(platform, result);
        verify(serviceProviderService).registerActionType(platformName, actionType);
    }

    @Test
    public void testRegisterTriggerType() {
        String platformName = "test_platform";
        TriggerType triggerType = new TriggerType();
        triggerType.setName("Test Trigger");
        triggerType.setDescription("This is a test trigger");
        Platform platform = new Platform();
        platform.setName(platformName);
        when(serviceProviderService.registerTriggerType(platformName, triggerType)).thenReturn(platform);

        Platform result = serviceProviderController.registerTriggerType(platformName, triggerType);

        assertEquals(platform, result);
        verify(serviceProviderService).registerTriggerType(platformName, triggerType);
    }

    @Test
    public void testGetOAuthScopesWithUserId() {
        String platformName = "test_platform";
        String userId = "test_user";
        Set<OAuthScope> expectedScopes = new HashSet<>();
        expectedScopes.add(new OAuthScope("scope1", "desc1"));
        expectedScopes.add(new OAuthScope("scope2", "desc2"));
        when(platformService.getOAuthScopes(platformName, userId)).thenReturn(expectedScopes);

        Set<OAuthScope> result = platformController.getOAuthScopes(platformName, userId);

        assertEquals(expectedScopes, result);
        verify(platformService).getOAuthScopes(platformName, userId);
    }

    @Test
    public void testGetOAuthScopesWithoutUserId() {
        String platformName = "test_platform";
        Set<OAuthScope> expectedScopes = new HashSet<>();
        expectedScopes.add(new OAuthScope("scope1", "desc1"));
        expectedScopes.add(new OAuthScope("scope2", "desc2"));
        when(platformService.getOAuthScopes(platformName)).thenReturn(expectedScopes);

        Set<OAuthScope> result = platformController.getOAuthScopes(platformName);

        assertEquals(expectedScopes, result);
        verify(platformService).getOAuthScopes(platformName);
    }

    @Test
    public void testGetUnacceptedOAuthScopes() {
        String platformName = "test_platform";
        String userId = "test_user";
        Set<OAuthScope> expectedScopes = new HashSet<>();
        expectedScopes.add(new OAuthScope("scope1", "desc1"));
        expectedScopes.add(new OAuthScope("scope2", "desc2"));
        when(platformService.getUnacceptedOAuthScopes(platformName, userId)).thenReturn(expectedScopes);

        Set<OAuthScope> result = platformController.getUnacceptedOAuthScopes(platformName, userId);

        assertEquals(expectedScopes, result);
        verify(platformService).getUnacceptedOAuthScopes(platformName, userId);
    }

    @Test
    public void testGetByName() {
        String platformName = "test_platform";
        Platform expectedPlatform = new Platform();
        expectedPlatform.setName(platformName);
        when(platformService.getByName(platformName)).thenReturn(expectedPlatform);

        Platform result = platformController.getByName(platformName);

        assertEquals(expectedPlatform, result);
        verify(platformService).getByName(platformName);
    }

    @Test
    public void testGetPlatformNames() {
        List<String> expectedPlatforms = new ArrayList<>();
        expectedPlatforms.add("platform1");
        expectedPlatforms.add("platform2");
        when(platformService.getPlatformNames()).thenReturn(expectedPlatforms);

        List<String> result = platformController.getPlatformNames();

        assertEquals(expectedPlatforms, result);
        verify(platformService).getPlatformNames();
    }

    @Test
    public void testGetAllActionPlatforms() {
        List<String> expectedPlatforms = new ArrayList<>();
        expectedPlatforms.add("platform1");
        expectedPlatforms.add("platform2");
        when(platformService.getAllActionPlatforms()).thenReturn(expectedPlatforms);

        List<String> result = platformController.getAllActionPlatforms();

        assertEquals(expectedPlatforms, result);
        verify(platformService).getAllActionPlatforms();
    }

    @Test
    public void testGetAllTriggerPlatforms() {
        List<String> expectedPlatforms = new ArrayList<>();
        expectedPlatforms.add("platform1");
        expectedPlatforms.add("platform2");
        when(platformService.getAllTriggerPlatforms()).thenReturn(expectedPlatforms);

        List<String> result = platformController.getAllTriggerPlatforms();

        assertEquals(expectedPlatforms, result);
        verify(platformService).getAllTriggerPlatforms();
    }

    @Test
    public void testGetOAuthToken() {
        String platformName = "test_platform";
        String userId = "test_user";
        String userCode = "test_code";
        Platform platform = new Platform();
        platform.setName(platformName);
        when(platformService.getByName(platformName)).thenReturn(platform);
        doNothing().when(platformService).getAuthToken(platform, userCode, userId);

        platformController.getOAuthToken(platformName, userId, userCode);

        verify(platformService).getByName(platformName);
        verify(platformService).getAuthToken(platform, userCode, userId);
    }

    @Test
    public void testGetPlatformLogin() {
        String platformName = "test_platform";
        Platform platform = new Platform();
        platform.setName(platformName);
        List<OAuthScope> scopes = new ArrayList<>();
        scopes.add(new OAuthScope("scope1", "desc1"));
        scopes.add(new OAuthScope("scope2", "desc2"));
        when(platformService.getByName(platformName)).thenReturn(platform);
        when(platformService.getAuthorizationURL(platform, scopes)).thenReturn("https://test.com/login");

        String result = platformController.getPlatformLogin(platformName, scopes);

        assertEquals("https://test.com/login", result);
        verify(platformService).getByName(platformName);
        verify(platformService).getAuthorizationURL(platform, scopes);
    }

    @Test
    public void testGetAuthorizationURLNewScopes() {
        String platformName = "test_platform";
        String userId = "test_user";
        Platform platform = new Platform();
        platform.setName(platformName);
        List<OAuthScope> scopes = new ArrayList<>();
        scopes.add(new OAuthScope("scope1", "desc1"));
        scopes.add(new OAuthScope("scope2", "desc2"));
        when(platformService.getByName(platformName)).thenReturn(platform);
        when(platformService.getAuthorizationURLNewScopes(platform, scopes, userId)).thenReturn("https://test.com/login?scopes=scope1,scope2");

        String result = platformController.getAuthorizationURLNewScopes(platformName, userId, scopes);

        assertEquals("https://test.com/login?scopes=scope1,scope2", result);
        verify(platformService).getByName(platformName);
        verify(platformService).getAuthorizationURLNewScopes(platform, scopes, userId);
    }


}
