package com.odbpo.flutter.bean;

import com.odbpo.flutter.util.StringUtil;

/**
 * createDate: 2023/9/16 on 14:02
 * desc:
 *
 * @author azhon
 */

public class ModulePageBean {

    private final String inputName;
    private final String moduleFileName;
    private final String routeFileName;
    private final String pageFileName;
    private final String apiFileName;
    private final String moduleClsName;
    private final String routeClsName;
    private final String pageClsName;
    private final String apiClsName;

    public ModulePageBean(String input) {
        inputName = input;
        moduleFileName = "module.dart";
        routeFileName = input + ".route.dart";
        pageFileName = input + ".page.dart";
        apiFileName = input + ".api.dart";

        String clsName = StringUtil.toCamelCase(input, true);

        moduleClsName = clsName + "Module";
        routeClsName = clsName + "Route";
        pageClsName = clsName + "Page";
        apiClsName = clsName + "Api";
    }

    public String getModuleFileName() {
        return moduleFileName;
    }

    public String getRouteFileName() {
        return routeFileName;
    }

    public String getPageFileName() {
        return pageFileName;
    }

    public String getApiFileName() {
        return apiFileName;
    }

    public String getModuleClsName() {
        return moduleClsName;
    }

    public String getRouteClsName() {
        return routeClsName;
    }

    public String getPageClsName() { return pageClsName; }

    public String getApiClsName() {
        return apiClsName;
    }

    public String getInputName() {
        return inputName;
    }
}
