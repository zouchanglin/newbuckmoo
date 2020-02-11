package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class ApplyPosition implements Serializable {
    private static final long serialVersionUID = 8846152246562286676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applyId;

    private String positionId;

    private String openId;

    private Integer readStatus;

    private Long createTime;

    private Long updateTime;
}