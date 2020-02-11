package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class SystemSettings implements Serializable {
    private static final long serialVersionUID = -6079143063337802098L;
    @Id
    private String systemKey;

    private String systemValue;

    private String systemRemark;
}