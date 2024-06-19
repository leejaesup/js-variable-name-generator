package js.portfolio.generator.controller;

import js.portfolio.generator.dto.ResponseData;
import js.portfolio.generator.entity.Header;
import js.portfolio.generator.service.VariableNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class VariableNameConverterController {

    private final VariableNameService variableNameService;

    @GetMapping("/convert")
    public ResponseEntity<ResponseData> convertVariableName(@RequestParam String requestMessages) throws IOException {
        if (requestMessages == null || requestMessages.isEmpty()) {
            Header header = Header.builder()
                    .resultCode("400")
                    .resultCode("오류가 발생했습니다.")
                    .build();

            ResponseData responseData = ResponseData.builder()
                    .header(header)
                    .build();

            return ResponseEntity.badRequest().body(responseData);
        }
        return ResponseEntity.ok().body(variableNameService.convert(requestMessages));
    }
}