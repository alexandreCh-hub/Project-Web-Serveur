package com.uca.gui;

import com.uca.core.UserCore;
import com.uca.entity.UserEntity;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserGUI {

    public static String getAllUsers(UserEntity user) throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("users", UserCore.getAllUsers());
        input.put("id",user.getId());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("users/users.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }
 
    public static String getFile(String name) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = UserGUI.class.getResourceAsStream(String.format("/static/html/%s", name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        return stringBuilder.toString();
    }
}
