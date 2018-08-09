package com.rzc.mvc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonRen
 * @since 2018/8/9 上午2:12
 */
public class ModelAndView {
    /**
     * 页面路径
     */
    private String view;

    /**
     * 页面data数据
     */
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public String getView() {
        return view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public ModelAndView addAllObjects(Map<String, ?> modelMap) {
        model.putAll(modelMap);
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
