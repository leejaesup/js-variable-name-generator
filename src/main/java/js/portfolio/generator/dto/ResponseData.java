package js.portfolio.generator.dto;

import js.portfolio.generator.entity.Body;
import js.portfolio.generator.entity.Header;
import lombok.Builder;
import lombok.Data;

@Data
public class ResponseData {
    private Header header;
    private Body body;

    @Builder
    public ResponseData(Header header, Body body) {
        this.header = header;
        this.body = body;
    }
}
