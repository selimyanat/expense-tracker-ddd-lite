package com.sy.expense.tracker.identityaccess.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AuthenticationService {

  private final UserRepository userRepository;

  private final EncryptionService encryptionService;

  public Optional<UserDescriptor> authenticate(final Email userEmail, final Password userPassword) {

    var existingUser = this.userRepository.findByUserEmail(userEmail);

    if (existingUser.isEmpty())
      return Optional.empty();

    assertPasswordNotSame(existingUser.get().getEncryptedPassword(), userPassword);

    return Optional.of(new UserDescriptor(
        existingUser.get().getId(),
        existingUser.get().getUsername(),
        existingUser.get().getEmail()));
  }

  private void assertPasswordNotSame(final EncryptedPassword encryptedUserPassword, final Password passwordToAssess) {

    checkArgument(encryptedUserPassword.getValue().equals(encryptionService.encrypt(passwordToAssess.getValue())),
        "The given password does not match existing password");
  }

}
