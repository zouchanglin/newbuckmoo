package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ApplyPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String applyId;

    private String positionId;

    private String openId;

    private Integer readStatus;

    private Long createTime;

    private Long updateTime;
}