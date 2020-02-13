package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class LongTextStorage {
    @Id
    private String argsName;

    private String argsText;
}
