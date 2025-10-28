package com.example.demo;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class EchoController {

    @RequestMapping(value = {"/", "/echo"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> echo(HttpServletRequest request, @RequestBody(required = false) Map<String, Object> body) {
        Map<String, Object> response = new LinkedHashMap<>();

        // Method
        response.put("method", request.getMethod());

        // Headers
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.put(name, request.getHeader(name));
        }
        response.put("headers", headers);

        // Query args
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> args = new HashMap<>();
        params.forEach((k, v) -> args.put(k, Arrays.asList(v)));
        response.put("args", args);

        // Form (basic reuse of args here)
        response.put("form", args.isEmpty() ? Collections.emptyMap() : args);

        // JSON body
        response.put("json", body);

        // Remote addr
        response.put("remote_addr", request.getRemoteAddr());

        // URL and path
        String url = request.getRequestURL().toString();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
        response.put("url", url);
        response.put("path", request.getRequestURI());

        return response;
    }
}
