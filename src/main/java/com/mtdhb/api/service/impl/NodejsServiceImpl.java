package com.mtdhb.api.service.impl;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtdhb.api.configuration.NodejsConfiguration;
import com.mtdhb.api.constant.e.ThirdPartyApplication;
import com.mtdhb.api.dto.nodejs.CookieCheckDTO;
import com.mtdhb.api.dto.nodejs.CookieStatusDTO;
import com.mtdhb.api.dto.nodejs.RedPacketDTO;
import com.mtdhb.api.dto.nodejs.RedPacketResultDTO;
import com.mtdhb.api.dto.nodejs.ResultDTO;
import com.mtdhb.api.entity.Cookie;
import com.mtdhb.api.exception.BusinessException;
import com.mtdhb.api.service.NodejsService;
import com.mtdhb.api.util.Connections;
import com.mtdhb.api.util.CreateMobile;
import com.mtdhb.api.util.HttpClientUtil;
import com.mtdhb.api.util.URLParser;
import com.mtdhb.api.util.Util;

/**
 * @author i@huangdenghe.com
 * @date 2018/03/28
 */
@Service
public class NodejsServiceImpl implements NodejsService {

	@Autowired
	private NodejsConfiguration nodejsConfiguration;

	private String quray = "https://h5.ele.me/hongbao/#hardware_id=&is_lucky_group=True&lucky_number=8&track_id=&platform=4&sn=29e47b57971c1c9d&theme_id=1969&device_id=";

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Override
	public CookieCheckDTO checkCookie(String cookieValue, ThirdPartyApplication application) throws IOException {
		String spec = nodejsConfiguration.getUrl() + nodejsConfiguration.getCheckCookie();
		Map<String, Object> arg = new HashMap<>();
		arg.put("cookie", cookieValue);
		arg.put("application", application.ordinal());

		if (cookieValue.indexOf("snsInfo[wx2a416286e96100ed]=") == -1
				&& cookieValue.indexOf("snsInfo[101204453]=") == -1) {
			throw new IOException("cookie 不正确，请确保内容包含：\n\nsnsInfo[wx2a416286e96100ed] 或 snsInfo[101204453]");
		}

		String[] cookieGroup = cookieValue.split(";");

		String target = null;
		for (String s : cookieGroup) {
			if (s.indexOf("snsInfo") != -1) {
				target = s;
				break;
			}
		}
		String s = java.net.URLDecoder.decode(target.split("=")[1], "UTF-8");
		Map sns = new HashMap();
		Gson gson = new Gson();
		sns = gson.fromJson(s, new TypeToken<Map<String, Object>>() {
		}.getType());

		if (sns == null || sns.size() == 0 || "".equals(sns.get("openid"))) {
			// throw new
		}

		URLParser urlP = new URLParser();
		String sn = urlP.fromURL(quray).compile().getParameter("sn");
		Map data = this.hongbao("18322129797", (String) sns.get("openid"), (String) sns.get("eleme_key"), 4, sn);

		if (data.get("name") == "SNS_UID_CHECK_FAILED") {
			// return response(1, 'cookie 不正确，请按照教程一步一步获取');
		}

		if ("5".equals(data.get("ret_code"))) {
			// return response(2, '请换一个不领饿了么红包的小号来贡献');
		}

		ResultDTO<CookieCheckDTO> resultDTO = new ResultDTO<>();
		resultDTO.setCode(0);
		resultDTO.setMessage("cookie 验证通过");

		CookieCheckDTO cookieCheckDTO = new CookieCheckDTO();
		cookieCheckDTO.setOpenid(Util.toStringAndTrim(sns.get("openid")));
		cookieCheckDTO.setHeadimgurl(Util.toStringAndTrim(sns.get("avatar")));
		cookieCheckDTO.setNickname(Util.toStringAndTrim(sns.get("nickname")));
		resultDTO.setData(cookieCheckDTO);

		return resultDTO.getData();
	}

	@Override
	public ResultDTO<RedPacketDTO> getHongbao(String url, String mobile, ThirdPartyApplication application, long limit,
			List<Cookie> cookies) throws IOException {
		ResultDTO<RedPacketDTO> resultDTO = new ResultDTO<RedPacketDTO>();
		List listCookieDto = new ArrayList();
		CookieStatusDTO cookieDTO = new CookieStatusDTO();

		URLParser urlP = new URLParser();
		CreateMobile createMobile = new CreateMobile();
		int number = -1;
		String theme_id = urlP.fromURL(url).compile().getParameter("theme_id");
		String sn = urlP.fromURL(url).compile().getParameter("sn");

		Map luckyMap = lucky(theme_id, sn);

		String phone = null;

		if ("".equals(sn) || sn == null || "".equals((Double) luckyMap.get("lucky_number"))) {
			resultDTO.setCode(3);
			resultDTO.setMessage("饿了么红包链接不正确");
			return resultDTO;
		}
		Double double1 = (Double) luckyMap.get("lucky_number");
		int lucky_number = Integer.parseInt(new java.text.DecimalFormat("0").format(double1));
		// Integer.parseInt(urlP.fromURL(url).compile().getParameter("lucky_number"))
		// 两种方式获取 lucky_number
		number = lucky_number;

		for (Cookie cookie : cookies) {

			// if (mobile === NO_MOBILE && number === 1) {
			// return response(99, '已领取到最佳前一个红包。下一个是最大红包，请手动打开红包链接领取');
			// }

			String[] cookieGroup = cookie.getValue().split(";");

			String target = null;
			for (String s : cookieGroup) {
				if (s.indexOf("snsInfo") != -1) {
					target = s;
					break;
				}
			}
			String s = java.net.URLDecoder.decode(target.split("=")[1], "UTF-8");
			Map sns = new HashMap();
			Gson gson = new Gson();
			sns = gson.fromJson(s, new TypeToken<Map<String, Object>>() {
			}.getType());
			if (sns == null || sns.size() == 0 || "".equals(sns.get("openid"))) {
				// throw new
			}

			Map data = new HashMap();
			int count = 0;
			while (true) {

				phone = number == 1 ? mobile : createMobile.getMobile();
				data = this.hongbao(phone, (String) sns.get("openid"), (String) sns.get("eleme_key"), 4, sn);
				if (data == null || data.size() == 0 || 10.0!=((Double)data.get("ret_code"))) {
					break;
				}
				

				// 是 code 10 并且是你填的那个号码
				// 按逻辑这里只有在 number === 1 的时候才会成立
				if (phone == mobile) {
					return creatResultDTO(12, "你的手机号没有注册饿了么账号或者需要填写验证码，无法领最大。下一个是最大红包，别再点网站的领取按钮，请手动打开红包链接领取", "未知",
							new BigDecimal(0), sn, listCookieDto);
				}

				// 100 次都是 code 10，防止死循环，直接报错

				if (++count >= 20) {
					return creatResultDTO(13, "请求饿了么服务器失败，请重试", "未知", new BigDecimal(0), sn, listCookieDto);
				}
			}

			if (data == null || "".equals(data.get("ret_code"))) {
				continue;
			}

			if ("SNS_UID_CHECK_FAILED".equals(data.get("name"))) {
				// 库里面 cookie 无效，虽然这种可能性比较小
				// 标记 cookie 状态
				BeanUtils.copyProperties(cookie, cookieDTO);
				cookieDTO.setStatus(2);
			} else {
				
				switch (Integer.parseInt(new java.text.DecimalFormat("0").format((Double) data.get("ret_code")))) {
				case 1:
					return creatResultDTO(17, "手气最佳红包已被领取", "未知",
							new BigDecimal(0), sn, listCookieDto);
				case 2:
					/*
					 * SUCCESS: 0, // 成功使用 USED: 1, // 已使用过 INVALID: 2, // 无效
					 * Cookie LIMIT: 3 // 领取次数达到上限
					 */
					BeanUtils.copyProperties(cookie, cookieDTO);
					cookieDTO.setStatus(1);
					listCookieDto.add(cookieDTO);

					// 这个号 抢过这个红包
					// cookie.status = CookieStatus.USED;
					// TODO: 有一定几率不对，有可能是 cookie 号领过，因为概率比较低，所以暂时先这样
					if (phone == mobile) {
						return creatResultDTO(11, "你的手机号之前领过小红包，无法领最大。下一个是最大红包，别再点网站的领取按钮，请手动打开红包链接领取", "未知",
								new BigDecimal(0), sn, listCookieDto);
					}
					break;
				case 3:
				case 4:
					// 领取成功
					BeanUtils.copyProperties(cookie, cookieDTO);
					cookieDTO.setStatus(0);
					listCookieDto.add(cookieDTO);
					break;
				case 5:
					// 没次数了，有可能是手机号没次数了，也有可能是 cookie 没次了
					// 如果是自己的号领的，没次了，直接提示
					BeanUtils.copyProperties(cookie, cookieDTO);
					cookieDTO.setStatus(3);
					listCookieDto.add(cookieDTO);
					
					if (phone == mobile) {
						
						return creatResultDTO(9, "你的手机号（或代领最佳的小号）今日领取次数已达上限。下一个是最大红包，别再点网站的领取按钮，请手动打开红包链接领取", "未知",
								new BigDecimal(0), sn, listCookieDto);
					}
					break;
				}
				
				number = lucky_number - ((List) data.get("promotion_records")).size();

				if ((data.get("promotion_records")) == null || ((List) data.get("promotion_records")).size() == 0
						|| number <= 0) {

					Map map = (Map) ((List) data.get("promotion_records")).get(lucky_number - 1);

					if (map == null || map.size() == 0) {

						return creatResultDTO(0, "手气最佳红包已被领取", "未知", new BigDecimal(0), sn, listCookieDto);
					}

					return creatResultDTO(0, "手气最佳红包已被领取", (String) map.get("sns_username"),
							new BigDecimal((Double)map.get("amount")) , sn, listCookieDto);

				}
				
				logger.info("还要领 ${"+number+"} 个红包才是手气最佳");
				
		        // < 号，左侧：若要尝试再抢一次，还剩余的体力值。
		        // < 号，右侧：还要尝试的次数，如果是手动，则还要尝试 number - 1 次；自动，则还要尝试 number 次
//		        if (limit - 1 < number - (mobile === NO_MOBILE ? 1 : 0)) {
//		          return response(8, `您的剩余可消耗次数不足以领取此红包，还差 ${number} 个是最佳红包`);
//		        }
			}
		}

		return resultDTO;
	}

	public ResultDTO<RedPacketDTO> creatResultDTO(int code, String message, String nickName, BigDecimal amount,
			String sn, List listCookieDto) {
		ResultDTO<RedPacketDTO> resultDTO = new ResultDTO<RedPacketDTO>();
		RedPacketDTO rp = new RedPacketDTO();
		RedPacketResultDTO rpr = new RedPacketResultDTO();

		resultDTO.setCode(code);
		resultDTO.setMessage(message);

		rpr.setNickname(nickName);
		rpr.setPrice(amount);
		rpr.setDate((new Date()).toString());
		rpr.setId(sn);
		rp.setResult(rpr);
		rp.setCookies(listCookieDto);

		return resultDTO;
	}

	// openid: sns.openid,
	// sign: sns.eleme_key,
	// platform: query.platform

	public Map hongbao(String phone, String openid, String sign, int platform, String sn) {

		Map paramsMap = new HashMap();

		paramsMap.put("is_lucky_group", "True");
		paramsMap.put("lucky_number", "8");
		paramsMap.put("platform", "4");
		paramsMap.put("sn", "29e47b57971c1c9d");
		paramsMap.put("theme_id", "1969");
		paramsMap.put("sign", sign);
		paramsMap.put("phone", phone);
		Gson gson = new Gson();
		String paramsJson = gson.toJson(paramsMap);

		String httpUrl = "https://h5.ele.me/restapi/v1/weixin/" + openid + "/phone";

		HttpClientUtil httpClient = new HttpClientUtil();
		String result = httpClient.sendHttpPutJson(httpUrl, paramsJson);

		if (!"AAAAAAA".equals(result)) {
			result = httpClient.sendHttpPutJson(httpUrl, paramsJson);
		}
		if (!"AAAAAAA".equals(result)) {
			throw new BusinessException(null, result, httpUrl);
		}

		httpUrl = "https://h5.ele.me/restapi/marketing/promotion/weixin/" + openid;
		Map hongbaoMap = new HashMap();

		hongbaoMap.put("sign", sign);
		hongbaoMap.put("group_sn", sn);
		String hongbaoJson = gson.toJson(hongbaoMap);

		String result1 = httpClient.sendHttpPostJson(httpUrl, hongbaoJson);
		Map<String, Object> map2 = gson.fromJson(result1, new TypeToken<Map<String, Object>>() {
		}.getType());
		return map2;
	}

	public Map lucky(String theme_id, String sn) {

		String httpUrl = "https://h5.ele.me/restapi/marketing/themes/" + theme_id + "/group_sns/" + sn;
		Map hongbaoMap = new HashMap();

		HttpClientUtil httpClient = new HttpClientUtil();
		String result = httpClient.sendHttpGet(httpUrl);
		Gson gson = new Gson();
		Map<String, Object> map2 = gson.fromJson(result, new TypeToken<Map<String, Object>>() {
		}.getType());
		return map2;

	}

}
