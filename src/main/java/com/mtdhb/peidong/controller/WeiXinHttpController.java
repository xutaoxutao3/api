package com.mtdhb.peidong.controller;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mtdhb.peidong.action.BaseWeixinMsgAction;
import com.mtdhb.peidong.pojo.BaseWeixinPlatformRequest;
import com.mtdhb.peidong.pojo.BaseWeixinPlatformResponse;
import com.thoughtworks.xstream.XStream;

/**
 * weixin request controller
 * @author zhaopeidong
 *
 */
@RestController
@RequestMapping("/weixin")
public class WeiXinHttpController extends BaseWeixinController {
	/**
	 * 微信验证签名 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/signature", method = RequestMethod.GET)
	public @ResponseBody String response4Signature(HttpServletRequest request) {
		System.out.println("-----微信后台发送验证签名请求--------");
		if (request.getParameter("echostr") != null){//微信验签
			if (super.checkSignature(request)){//验签通过
				return request.getParameter("echostr");
			}
		}
		return "Who the fuck are you?";
		
	}
	/**
	 * 转发测试 入口
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/proxy", method = RequestMethod.POST)
	public ModelAndView entryMethod(@RequestBody DOMSource domsource, HttpServletRequest request) {
//		System.out.println("-----请求xml数据Start--------");
//		String xmlStr = parseDOMSource(domsource);
//		
//		System.out.println("-----sax获取xml报文中MSgType字段--------");
//		SAXReader reader = new SAXReader();
//		Document document = null;
//		try {
//			document = reader.read(new ByteArrayInputStream(xmlStr.getBytes("utf-8")));
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		} catch (DocumentException e1) {
//			e1.printStackTrace();
//		}//读取xml字符串，注意这里要转成输入流
//        Element root = document.getRootElement();//获取根元素
//        List<Element> childElements = root.elements();//获取当前元素下的全部子元素
//        String msgType = "";
//        for (Element child : childElements) {//遍历xml子节点
//        	if (child.getName().equals("MsgType")){
//        		msgType = (String) child.getData();
//        		msgType = msgType.trim();
//        		break;
//        	}
//        }
//        
//		System.out.println("-----获取resolveBean--------");
//		ServletContext servletContext = request.getSession().getServletContext(); 
//	    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext );
//	    WeiXinHttpResolver resolver = (WeiXinHttpResolver) ctx.getBean("resolver");
//	    
//	    
		HashMap map = new HashMap();
		map.put("phone", "13338617594");
//		return new RedirectView("/rest/weixin/testPath", true, true, false);
		ModelAndView mAndView = new ModelAndView(new RedirectView("/user/register", true, true, false), map);  
		return mAndView;

//		System.out.println("-----微信后台发送验证签名请求--------");
//		if (request.getParameter("echostr") != null){//微信验签
//			if (super.checkSignature(request)){//验签通过
//				return request.getParameter("echostr");
//			}
//		}
//		return "Who the fuck are you?";
		
	}
	/**
	 * 测试转发 业务逻辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/testPath", produces ="application/json", method = RequestMethod.GET)
	public ModelAndView bussAction(ModelMap map) {
		System.out.println("-----测试转发目标方法--------");
		Map resMap = new HashMap();
		resMap.put("phone", map.get("phone"));
		resMap.put("MsgType", "text");
		ModelAndView mAndView = new ModelAndView(new RedirectView("/rest/weixin/responseOut", true, true, false), resMap);  
		return mAndView;
	}
	
	/**
	 * 测试转发 返回
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/responseOut", produces ="text/xml", method = RequestMethod.GET)
	public @ResponseBody BaseWeixinPlatformResponse exitMethod(ModelMap map) {
		System.out.println("-----测试转发目标方法--------");
		String msgType = (String) map.get("MsgType");//信息类型
		BaseWeixinPlatformResponse response = new BaseWeixinPlatformResponse();
		return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
	@RequestMapping(value="/signature" , consumes = "text/xml" , produces ="text/xml", method = RequestMethod.POST)
	public @ResponseBody BaseWeixinPlatformResponse response4Post(@RequestBody DOMSource domsource, HttpServletRequest request) {
		System.out.println("-----请求xml数据Start--------");
		String xmlStr = parseDOMSource(domsource);
		
		System.out.println("-----sax获取xml报文中MSgType字段--------");
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new ByteArrayInputStream(xmlStr.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}//读取xml字符串，注意这里要转成输入流
        Element root = document.getRootElement();//获取根元素
        List<Element> childElements = root.elements();//获取当前元素下的全部子元素
        String msgType = "";
        for (Element child : childElements) {//遍历xml子节点
        	if (child.getName().equals("MsgType")){
        		msgType = (String) child.getData();
        		msgType = msgType.trim();
        		break;
        	}
        }
        
		System.out.println("-----获取resolveBean--------");
		ServletContext servletContext = request.getSession().getServletContext(); 
	    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext );
	    WeiXinHttpResolver resolver = (WeiXinHttpResolver) ctx.getBean("resolver");
	    
	    System.out.println("-----获取resolve请求Bean--------");
	    Class requestClazz = resolver.getRequestPoJoMap().get(msgType).getClass();
	    
	    System.out.println("-----解析xml报文成request对象--------");
		XStream xStream = new XStream();
		xStream.alias("xml", requestClazz);
		BaseWeixinPlatformRequest requestPojo = (BaseWeixinPlatformRequest) xStream.fromXML(xmlStr);
		
	    System.out.println("-----执行业务逻辑--------");
	    BaseWeixinMsgAction action = (BaseWeixinMsgAction) resolver.getExecuteMap().get(msgType);
	    
	    System.out.println("-----请求xml数据End 返回xml报文--------");
		return action.execute(requestPojo);
	}

    
	/**
	 * 请求解析为xml字符串
	 * @param domsource
	 * @return
	 */
	private String parseDOMSource(DOMSource domsource) {
	try {
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domsource, result);
        return writer.toString();
    } catch (TransformerConfigurationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (TransformerFactoryConfigurationError e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (TransformerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
}
}
