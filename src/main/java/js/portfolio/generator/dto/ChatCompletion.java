package js.portfolio.generator.dto;

import js.portfolio.generator.entity.Choice;
import js.portfolio.generator.entity.Usage;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletion {
    public String id;
    public String object;
    public Long created;
    public String model;
    public List<Choice> choices;
    public Usage usage;
    public String system_fingerprint;
}