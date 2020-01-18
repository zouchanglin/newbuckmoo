package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SystemSettings {
    @Id
    private String systemKey;

    private String systemValue;

    private String systemRemark;
}