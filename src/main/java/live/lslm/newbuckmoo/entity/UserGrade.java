package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserGrade {
    @Id
    private String openId;

    private Integer studentGrade;

    private Integer clubGrade;

    private Integer companyGrade;
}