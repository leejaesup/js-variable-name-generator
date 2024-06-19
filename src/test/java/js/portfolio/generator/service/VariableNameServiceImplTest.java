package js.portfolio.generator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class VariableNameServiceImplTest {

    @InjectMocks
    private VariableNameServiceImpl service;

    @Test
    public void camelCase_성공_테스트() {
        String input = "TestMessage";
        String result = service.toCamelCase(input);
        assertEquals("testMessage", result);
    }

    @Test
    public void pascalCase_성공_테스트() {
        String input = "testMessage";
        String result = service.toPascalCase(input);
        assertEquals("TestMessage", result);
    }

    @Test
    public void snakeCase_Lowercase_성공_테스트() {
        String input = "TestMessage";
        String result = service.toSnakeCase(input, false);
        assertEquals("test_message", result);
    }

    @Test
    public void snakeCase_Uppercase_성공_테스트() {
        String input = "TestMessage";
        String result = service.toSnakeCase(input, true);
        assertEquals("TEST_MESSAGE", result);
    }
}