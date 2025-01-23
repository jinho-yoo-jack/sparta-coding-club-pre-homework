package sparta.coding.club.prehomework.global.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FailInsertToPersistence extends RuntimeException {
    private final String message;

}
