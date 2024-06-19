package js.portfolio.generator.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Body<T> {
    private long totalCount;
    private T items;

}
