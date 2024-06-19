package js.portfolio.generator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import js.portfolio.generator.dto.ChatCompletion;
import js.portfolio.generator.dto.ResponseData;
import js.portfolio.generator.entity.Body;
import js.portfolio.generator.entity.Choice;
import js.portfolio.generator.entity.Header;
import js.portfolio.generator.entity.VariableNameFormats;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariableNameServiceImpl implements VariableNameService {

    private final RestTemplate restTemplate;
    ObjectMapper mapper = new ObjectMapper();

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;
    @Value("${openai.secret-key}")
    private String apiKey;

    @Override
    public ResponseData convert(String messages) throws IOException {
        String convertMessages = createNewVariable(messages);
        System.out.println("convertMessages = " + convertMessages);

        List<VariableNameFormats> parseMessages = parseMessages(messages);

        Header header = Header.builder()
                .resultCode("200")
                .resultMsg("성공")
                .build();

        Body body = Body.builder()
                .totalCount(parseMessages.size())
                .items(parseMessages)
                .build();

        return ResponseData.builder()
                .header(header)
                .body(body)
                .build();
    }

    private List<VariableNameFormats> parseMessages(String messages) throws IOException {
        List<VariableNameFormats> resultMessages = new ArrayList<>();
        String[] responseMessages = createNewVariable(messages).split("\\n");

        for (String responseMessage : responseMessages) {
            try {
                VariableNameFormats formattedMessage = formatMessage(responseMessage);
                resultMessages.add(formattedMessage);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("오류 메시지 파싱 중 오류 발생 : " + responseMessage);
            }
        }

        return resultMessages;
    }

    private VariableNameFormats formatMessage(String responseMessage) {
        String[] parts = responseMessage.split(" ");

        int index = Integer.parseInt(parts[0].replace(".", "").trim());
        String message = parts[1].trim();

        return new VariableNameFormats(index,
                                        message,
                                        toCamelCase(message),
                                        toPascalCase(message),
                                        toSnakeCase(message, false),
                                        toSnakeCase(message, true));
    }


    public String createNewVariable(String messages) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String payload = String.format("{" +
                "\"model\":\"%s\"," +
                "\"messages\":[{" +
                "   \"role\":\"user\"," +
                "   \"content\":\"Please suggest three programming identifier or function names in camel case in a consistent format for the description of %s\"" +
                "   }]," +
                "\"temperature\": 0.7," +
                "\"max_tokens\": 100" +
                "}", model, messages);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
        return extractTranslation(response.getBody());
    }

    public String extractTranslation(String json) throws IOException {
        try {
            ChatCompletion chatCompletion = mapper.readValue(json, ChatCompletion.class);

            for (Choice choice : chatCompletion.choices) {
                json = choice.getMessage().getContent();
            }
        } catch (IOException e) {
//            System.err.println("JSON 파싱 중 오류 발생 : " + e.getMessage());
            throw new IOException("JSON 파싱 중 오류 발생 : " + e.getMessage());

        } catch (Exception e) {
//            System.err.println("예기치 않은 오류가 발생 : " + e.getMessage());
        }

        return json;
    }

    public String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("(?=[A-Z])"); //대문자를 기준으로 분리
        StringBuilder builder = new StringBuilder(words[0].toLowerCase());
        for (int i = 1; i < words.length; i++) {
            builder.append(capitalize(words[i]));
        }
        return builder.toString();
    }

    public String toPascalCase(String input) {
        String camelCase = toCamelCase(input);
        camelCase = camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
        return camelCase;
    }

    public String toSnakeCase(String input, boolean uppercase) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String result = input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();

        if (uppercase) { //대문자로 강제변경
            return result.toUpperCase();
        }
        return result;
    }

    private String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}