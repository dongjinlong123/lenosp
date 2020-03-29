package com.len.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * qq互联接入
 */
@Controller
@Slf4j
@RequestMapping("/qq")
public class QQController {

    @GetMapping(value = "/qqLogin")
    public void qqLogin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/html;charset=utf-8");

            String authorizeURL = new Oauth().getAuthorizeURL(req);

            resp.sendRedirect(authorizeURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }


}
