package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class RecommendSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recommendId;

    /* 被推荐人openId */
    private String signOpenId;

    /* 推荐人OpenId */
    private String pushOpenId;

    /* 推荐类型：学生推荐、社团推荐、企业推荐 */
    private Integer recommendType;
}