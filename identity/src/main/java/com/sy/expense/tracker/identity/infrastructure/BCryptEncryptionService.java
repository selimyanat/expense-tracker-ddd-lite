package com.sy.expense.tracker.identity.infrastructure;

import com.sy.expense.tracker.identity.domain.EncryptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@Slf4j
public class BCryptEncryptionService implements EncryptionService {

  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public String encrypt(String aPlainTextValue) {

    return passwordEncoder.encode(aPlainTextValue);
  }
}
