package mymarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


public abstract class BaseController {

    public ModelAndView modelAndView (String viewName, ModelAndView modelAndView){

        modelAndView.setViewName(viewName);

        return modelAndView;
    }

    public ModelAndView model (String viewName){

        return this.modelAndView(viewName,new ModelAndView());

    }

    public ModelAndView redirect (String url){

        return this.model("redirect:" + url);
    }
}
