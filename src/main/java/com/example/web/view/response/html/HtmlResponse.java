package com.example.web.view.response.html;

import com.example.web.model.ProtoMap;
import org.springframework.web.servlet.ModelAndView;

public class HtmlResponse {

    public static String getHtmlFilePath(String fileName) {
        if (!fileName.endsWith(".html")) {
            fileName = fileName.concat(".html");
        }

        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }

        return fileName;
    }

    public static ModelAndView generate(String subPath, String fileName) {
        fileName = subPath.endsWith("/") ? subPath.concat(fileName) : subPath.concat("/").concat(fileName);
        return new ModelAndView(getHtmlFilePath(fileName));
    }

    public static ModelAndView generate(String subPath, String fileName, ProtoMap attributes) {
        return generate(subPath, fileName).addAllObjects(attributes);
    }

}
