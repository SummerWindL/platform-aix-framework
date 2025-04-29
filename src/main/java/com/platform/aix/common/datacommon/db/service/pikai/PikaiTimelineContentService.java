package com.platform.aix.common.datacommon.db.service.pikai;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.controller.pikai.common.request.PikaiTimelineContentReq;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 11:01
 */
public interface PikaiTimelineContentService extends AsyncService<Integer, PikaiTimelineContent> {

    /**
     * 保存内容
     * @author fuyanliang
     * @date 2025/4/17 11:06
     * @param content
     * @return int
     */
    int savePikaiTimelineContent(PikaiTimelineContentReq content);

    /**
     * 更新
     * @author fuyanliang
     * @date 2025/4/17 11:07
     * @param content
     * @return int
     */
    int updatePikaiTimelineContent(PikaiTimelineContent content);

    /**
     * 删除
     * @author fuyanliang
     * @date 2025/4/17 11:07
     * @param content
     * @return int
     */
    int deletPikaiTimelineContent(Integer id);

    PikaiTimelineContent queryPikaiTimelineContentOne(Integer id);
}
