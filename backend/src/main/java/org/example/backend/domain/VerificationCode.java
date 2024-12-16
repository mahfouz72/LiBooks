package org.example.backend.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class VerificationCode {
    private final String code;
    private final LocalDateTime expirationDate;

    public VerificationCode(String code, LocalDateTime expirationDate) {
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public boolean checkVerificationCode(String sentCode) {
        return code.equals(sentCode) && !isExpired();
    }

    private boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }
}