package com.vervloet.msgservices.service;

import static com.vervloet.msgservices.util.UserFactory.createUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  private static final Long USER_ID = 0L;
  private static final String PASSWORD = "password";
  private static final String WRONG_PASSWORD = "wrong-password";
  private static final String NEW_PASSWORD = "new-password";
  private static final String OTHER_EMAIL = "other_email@email.com";

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;

  @Test
  public void whenISaveAUserThatIsNotPresentThenItShouldBeSavedProperly() {
    User user = createUser();
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    ResponseEntity response = userService.create(user);
    verify(userRepository).save(any(User.class));
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  public void whenISaveAUserThatIsPresentThenItShouldNotBeSaved() {
    User user = createUser();
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    ResponseEntity response = userService.create(user);
    assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
  }

  @Test
  public void whenIDeleteAUserAndIAmAuthenticatedAsTheUserItShouldBeDeleted() {

    User user = createUser();
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    ResponseEntity response = userService.delete(USER_ID);
    verify(userRepository).delete(any(User.class));
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    verify(userRepository).delete(any(User.class));
  }

  @Test
  public void whenIDeleteAUserAndIAmNotAuthenticatedAsTheUserThenItShouldReturnForbidden() {

    User user = createUser();
    CustomUserDetails customUserDetails = new CustomUserDetails(user);
    customUserDetails.setEmail(OTHER_EMAIL);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    ResponseEntity response = userService.delete(USER_ID);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void whenIUpdateEmailAndPasswordIsCorrectThenItShouldSaveChangedUserCorrectly() {
    User user = createUser();
    User newUser = createUser();
    newUser.setEmail(OTHER_EMAIL);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(newUser);
    ResponseEntity response = userService.updateEmail(newUser, USER_ID);
    verify(userRepository).save(any(User.class));

    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void whenIUpdateEmailAndPasswordIsWrongThenItShouldReturnForbidden() {
    User user = createUser();
    User newUser = createUser();
    newUser.setEmail(OTHER_EMAIL);
    newUser.setPassword(WRONG_PASSWORD);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    ResponseEntity response = userService.updateEmail(newUser, USER_ID);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void whenIUpdatePasswordAndOldPasswordIsCorrectThenItShouldSaveChangedUserCorrectly() {
    User user = createUser();
    User newUser = createUser();
    newUser.setPassword(NEW_PASSWORD);
    Map<String, String> passwordMap = new HashMap<>();
    passwordMap.put("old-password", PASSWORD);
    passwordMap.put("new-password", NEW_PASSWORD);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(newUser);
    ResponseEntity response = userService.updatePassword(passwordMap, USER_ID);
    verify(userRepository).save(any(User.class));

    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void whenIUpdatePasswordAndOldPasswordIsWrongThenItShouldReturnForbidden() {
    User user = createUser();
    User newUser = createUser();
    newUser.setPassword(NEW_PASSWORD);
    Map<String, String> passwordMap = new HashMap<>();
    passwordMap.put("old-password", WRONG_PASSWORD);
    passwordMap.put("new-password", NEW_PASSWORD);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    ResponseEntity response = userService.updatePassword(passwordMap, USER_ID);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }
}
