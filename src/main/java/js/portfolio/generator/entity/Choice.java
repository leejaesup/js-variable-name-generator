package js.portfolio.generator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Choice {
    private int index;
    private Message message;
    private Object logprobs;
    private String finish_reason;
}
