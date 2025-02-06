package sparta.coding.club.prehomework.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final int code;
    private final String error;
    private final String requestMessage;
    private final String path;

}
