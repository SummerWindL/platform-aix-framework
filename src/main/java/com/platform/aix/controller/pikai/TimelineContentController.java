package com.platform.aix.controller.pikai;

import com.platform.aix.common.datacommon.db.service.pikai.PikaiTimelineContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * pikai时光纪事内容api
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 10:59
 */
@RestController
@RequestMapping("/pikai/api/timeline")
public class TimelineContentController {

    @Autowired
    private PikaiTimelineContentService pikaiTimelineContentService;
//    // 创建时光纪事
//    @PostMapping
//    public ResponseEntity<TimelineEntry> createEntry(
//            @RequestBody TimelineEntryDTO entryDTO,
//            @AuthenticationPrincipal User user) {
//        TimelineEntry entry = pikaiTimelineContentService.createEntry(entryDTO, user);
//        return ResponseEntity.ok(entry);
//    }
//
//    // 更新时光纪事
//    @PutMapping("/{id}")
//    public ResponseEntity<TimelineEntry> updateEntry(
//            @PathVariable Long id,
//            @RequestBody TimelineEntryDTO entryDTO,
//            @AuthenticationPrincipal User user) {
//        TimelineEntry entry = timelineEntryService.updateEntry(id, entryDTO, user);
//        return ResponseEntity.ok(entry);
//    }
//
//    // 获取单个时光纪事
//    @GetMapping("/{id}")
//    public ResponseEntity<TimelineEntry> getEntry(
//            @PathVariable Long id,
//            @AuthenticationPrincipal User user) {
//        TimelineEntry entry = timelineEntryService.getEntry(id, user);
//        return ResponseEntity.ok(entry);
//    }
//
//    // 获取用户所有时光纪事
//    @GetMapping
//    public ResponseEntity<List<TimelineEntry>> getUserEntries(
//            @AuthenticationPrincipal User user) {
//        List<TimelineEntry> entries = timelineEntryService.getUserEntries(user);
//        return ResponseEntity.ok(entries);
//    }
}
