package js.portfolio.generator.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Response {
    private Header header;
    private Body body;
}
