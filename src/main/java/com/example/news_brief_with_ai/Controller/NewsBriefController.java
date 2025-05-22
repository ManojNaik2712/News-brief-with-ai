package com.example.news_brief_with_ai.Controller;

import com.example.news_brief_with_ai.DTO.NewsSummaryResponse;
import com.example.news_brief_with_ai.Service.NewsBriefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news-brief")
public class NewsBriefController {
    private final NewsBriefService newsBriefService;

    @Autowired
    public NewsBriefController(NewsBriefService newsBriefService) {
        this.newsBriefService = newsBriefService;
    }

    @GetMapping(value = "/general-brief",produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsSummaryResponse generalBrief(){
        return newsBriefService.generateGeneralNewsBrief();
    }

//    @GetMapping(value = "/general-brief/render",produces = MediaType.TEXT_HTML_VALUE)
//    public NewsSummaryResponse generalBrief(){
//        return newsBriefService.generateGeneralNewsBrief();
//    }

}
