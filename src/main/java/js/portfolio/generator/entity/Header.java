package js.portfolio.generator.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Header {
    private String resultCode;
    private String resultMsg;
}