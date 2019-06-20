package com.self.kaptcha.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.self.kaptcha.config.CreateImageCode;

@RefreshScope
@RestController
@RequestMapping("/cap/kaptchacode/")
public class KaptchaCodeController {

	private static Logger logger = LoggerFactory.getLogger(KaptchaCodeController.class);

	/**
	 * 1、生成验证码
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/default")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			HttpSession session) throws Exception {
		// 使用方法
		httpServletResponse.setContentType("image/jpeg"); // 禁止图像缓存。
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setHeader("Cache-Control", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		try {
			CreateImageCode vCode = new CreateImageCode(100, 30, 4, 10);
			logger.info("生成图片成功sessionId:" + session.getId() + "|code:" + vCode.getCode());
			// session.setAttribute("code", vCode.getCode());
			vCode.write(httpServletResponse.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("生成图片失败");
		}
	}
}
