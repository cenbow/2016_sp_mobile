/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, shangpin.com
 * Filename:		com.shangpin.manager.shiro.CaptchaUsernamePasswordToken.java
 * Class:			CaptchaUsernamePasswordToken
 * Date:			2012-8-7
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.shangpin.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> 
 * Version 1.1.0
 * @since 2012-8-7 上午9:21:32
 */

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	/** 描述 */
	private static final long serialVersionUID = -3178260335127476542L;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken() {
		super();
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

}
