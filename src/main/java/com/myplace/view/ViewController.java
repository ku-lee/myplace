package com.myplace.view;

import com.myplace.place.web.controller.PlaceSearchController;
import com.myplace.place.web.dto.PlaceSearchResponseDto;
import com.myplace.place.web.dto.PlaceTopKeywordResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ViewController {

    @Autowired
    PlaceSearchController controller;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @GetMapping("/search")
    public String search(){
        return "search";
    }

    @GetMapping("/result")
    public String result(@RequestParam("keyword") String keyword,
                         @RequestParam(value = "page", defaultValue = "1") String page,
                         @RequestParam(value = "increase", required = false) String increase,  Model model) throws Exception {

        PlaceSearchResponseDto result = controller.search(keyword, "kakao", page, increase).getBody();
        model.addAttribute("search_info", result.getSearch_info());
        model.addAttribute("place_info", result.getPlace_info());
        model.addAttribute("keyword", keyword);
        model.addAttribute("current_page", result.getCurrent_page());

        PlaceTopKeywordResponseDto topKeywords = controller.keywords(10).getBody();
        model.addAttribute("keywords", topKeywords.getKeywords());

        return "result";
    }

}
