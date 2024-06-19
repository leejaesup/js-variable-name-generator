package js.portfolio.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class VariableNameFormats {
    private int index;
    private String originMessage;
    private String camelCase;
    private String pascalCase;
    private String snakeCaseLower;
    private String snakeCaseUpper;
}