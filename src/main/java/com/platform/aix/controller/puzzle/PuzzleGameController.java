package com.platform.aix.controller.puzzle;

import com.platform.aix.common.annotation.Access;
import com.platform.aix.common.datacommon.db.domain.PuzzleHistory;
import com.platform.aix.common.datacommon.db.service.PuzzleHistoryService;
import com.platform.aix.common.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年03月07日 20:25
 */
@RestController
@RequestMapping("/api/puzzle")
@Access()
public class PuzzleGameController {

    @Autowired
    private PuzzleHistoryService puzzleHistoryService;

    @GetMapping("history")
    @ResponseBody
    public APIResponse getPuzzleHistory(){
        List<PuzzleHistory> puzzleHistories = puzzleHistoryService.queryAll();
        return new APIResponse(puzzleHistories);
    }

    @PostMapping("savehistory")
    @ResponseBody
    public APIResponse savePuzzleHistory(@RequestBody  PuzzleHistory puzzleHistory){
        return new APIResponse(puzzleHistoryService.savePuzzleHistoryReturnEntity(puzzleHistory));
    }

    @PostMapping
    @ResponseBody
    public APIResponse clearHistory(){
        return new APIResponse(puzzleHistoryService.deleteAll());
    }
    @DeleteMapping("/history/{id}")
    public APIResponse deleteHistory(@PathVariable Integer id) {
        return new APIResponse( puzzleHistoryService.deleteHistory(id));
    }
}
