package live.lslm.newbuckmoo.vo;

import lombok.Data;

@Data
public class PageResultBind<T> {
    /** 当前第几页 */
    private Integer currentPage;

    /** 总共有几页 */
    private Integer totalPage;

    /** 每页多少条 */
    private Integer size;

    /** 具体内容. */
    private T data;
}