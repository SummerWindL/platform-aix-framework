package com.platform.aix.controller.pikai.content;

import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiTimelineContentService;
import com.platform.aix.controller.pikai.common.ApiResponse;
import com.platform.aix.controller.pikai.common.AuthResponse;
import com.platform.aix.controller.pikai.common.request.PikaiTimelineContentReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内容保存
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月29日 14:23
 */
@RestController
@RequestMapping("pikai/api/timeline")
@RequiredArgsConstructor
public class TimelineContentController {
    @Autowired
    private PikaiTimelineContentService pikaiTimelineContentService;
    @PostMapping("content")
    public ApiResponse<String> saveContent(@RequestBody PikaiTimelineContentReq pikaiTimelineContentReq) {
        pikaiTimelineContentService.savePikaiTimelineContent(pikaiTimelineContentReq);
        return ApiResponse.success("保存成功");
    }

    @PostMapping("getUserAllContents")
    public ApiResponse<List<PikaiTimelineContent>> getUserAllContent(@RequestBody PikaiTimelineContentReq pikaiTimelineContentReq){
        return ApiResponse.success(pikaiTimelineContentService.queryAllContent(pikaiTimelineContentReq));
    }

    @DeleteMapping("content/{id}")
    public ApiResponse<String> deleteContent(Integer id){
        int i = pikaiTimelineContentService.deletPikaiTimelineContent(id);
        return ApiResponse.success("删除成功");
    }

}
