package sysu.usc.registerModule.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sysu.usc.registerModule.VO.Result;
import sysu.usc.registerModule.dto.FormDTO;
import sysu.usc.registerModule.service.impl.SchoolCardService;

/**
 * @author little-penguin
 */
@RestController
@RequestMapping("/schoolCard")
@Slf4j
public class SchoolCardController {
    SchoolCardService schoolCardService;

    @Autowired
    public SchoolCardController(SchoolCardService schoolCardService) {
        this.schoolCardService = schoolCardService;
    }




    /**
     * 添加校园卡记录接口
     * @param schoolCardForm
     * @return 是否成功
     */
    @PostMapping
    public Result saveRecord(@RequestBody FormDTO schoolCardForm){
        log.info(schoolCardForm.toString());
        return schoolCardService.saveRecord(schoolCardForm);
    }

}
