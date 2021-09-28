package com.platform.aix.cmd.bean.filter;

import com.platform.common.enumeration.EnumCountflagType;
import lombok.Data;

@Data
public class BasePageFilter extends BaseFilter {

    /**
     * 查询方式：
     * 1 (EnumCountflagType.ALL)查询记录总数和分页的记录，记录总数放在retvalue中；
     * 2 (EnumCountflagType.COUNT)仅查询记录总数，不查询分页记录，记录总数放在retvalue中，
     * 3 (EnumCountflagType.LIST)仅查询分页记录，不查询记录总数。 retvalue始终=0
     */
    private Integer countflag = EnumCountflagType.LIST.getCountflag();

    /**
     * 查询记录当前页
     */
    private Integer page = 1;

    /**
     * 查询记录每页显示数量
     */
    private Integer pagesize = 20;

    /**
     * 查询记录起始位置
     *
     * @return
     */
    public Integer getOffset() {
        return (this.getPage() - 1) * this.getPagesize();
    }

    public void setCountflag(Integer countflag) {
        if (!EnumCountflagType.isSupportType(countflag)) {
            countflag = EnumCountflagType.ALL.getCountflag();
        }
        this.countflag = countflag;
    }

    public void setPage(Integer page) {
        if (page < 1) {
            page = 1;
        }
        this.page = page;
    }

    public void setPagesize(Integer pagesize) {
        if (pagesize < 1) {
            pagesize = 20;
        }
        this.pagesize = pagesize;
    }
}
