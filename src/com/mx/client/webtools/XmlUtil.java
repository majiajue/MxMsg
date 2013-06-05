package com.mx.client.webtools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mx.clent.vo.MsgFriendGroup;
import com.mx.clent.vo.MsgUser;

public class XmlUtil {
	private static final XmlUtil self = new XmlUtil();
	private DocumentBuilder builer = null;

	public synchronized static XmlUtil instance()
			throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		self.builer = factory.newDocumentBuilder();
		return self;
	}

	public Document createDocument(byte[] xml) throws SAXException, IOException {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xml);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("xml解析异常");
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}

		}
		return builer.parse(is);

	}

	public String parseXmltoString(String xml, String encode, String tagName) {
		Document document = null;

		try {
			document = createDocument(xml.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		if (tagName == null) {
			System.out.println("后续扩展");
		} else {

			Element element = document.getDocumentElement();
			NodeList list = element.getElementsByTagName(tagName);
			if (list.getLength() == 0) {
				sb.append("");
				System.out.println("无数据");
			} else {
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					sb.append(node.getTextContent());
					if (i + 1 == list.getLength()) {
						sb.append("");
					} else {

						sb.append(",");
					}

				}

			}
		}

		return sb.toString();
	}

	/**
	 * 解析好友列表
	 * 
	 * @param xml
	 * @param encode
	 * @param tagName
	 * @return
	 */
	public List<MsgFriendGroup> parseXmltoString2(String xml, String encode,
			String tagName) {
		Document document = null;
		List<MsgFriendGroup> frList = new ArrayList<MsgFriendGroup>();
		MsgFriendGroup groups = new MsgFriendGroup();
		List<MsgUser> users = new ArrayList<MsgUser>();
		try {
			document = createDocument(xml.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();

		Element element = document.getDocumentElement();
		NodeList list = element.getElementsByTagName(tagName);
		if (list.getLength() == 0) {
			sb.append("");
			System.out.println("无数据");
		} else {
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				MsgUser msgUser = new MsgUser();
				for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2
						.getNextSibling()) {

					
					if (node2.getNodeName().equals("peerid")) {
						if (node2.getTextContent() != null)
							msgUser.setUserID(node2.getTextContent().trim());
						System.out.println("peerid==="
								+ node2.getTextContent().trim());
					}

					if (node2.getNodeName().equals("name")) {
						if (node2.getTextContent() != null)
							msgUser.setUserName(node2.getTextContent().trim());
						System.out.println("name====" + node2.getTextContent());
					}

					if (node2.getNodeName().equals("phone")) {
						if (node2.getTextContent() != null)
							msgUser.setPersonWord(node2.getTextContent().trim());
						System.out.println("phone===="
								+ node2.getTextContent().trim());
					}
					
				}
				msgUser.setOnline(true);
				users.add(msgUser);
			}

		}

		groups.setUserList(users);
		frList.add(groups);
		return frList;
	}

	public String parseEnityToXml(Object object) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (object == null) {
			System.out.println("对象为空" + object);
			return "";
		} else {
			Class class1 = object.getClass();
			System.out.println(class1.getName());
			Field[] fields = class1.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String stringLetter = fieldName.substring(0, 1).toUpperCase();
				String getName = "get" + stringLetter + fieldName.substring(1);
				if (getName.equals("getSerialVersionUID")) {
					continue;
				}
				Method getMethod = class1.getDeclaredMethod(getName,
						new Class[] {});
				Object value = getMethod.invoke(object, new Object[] {});
				System.out.println(class1.getName() + "属性" + field.getName()
						+ "值" + value);
			}
			return null;
		}
	}

	// public String contractSCMXml(TContractInfo info) throws DOMException,
	// Exception{
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// DocumentBuilder builder=null;
	// DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
	// try {
	// builder =factory.newDocumentBuilder();
	// } catch (ParserConfigurationException e) {
	// // TODO 自动生成 catch 块
	// e.printStackTrace();
	// }
	// Document doc = builder.newDocument();
	// Element input = doc.createElement("INPUT");
	// Element contracts = doc.createElement("CONTRACTS");
	// Element element = doc.createElement("CONTRACT");
	// doc.appendChild(input);
	// input.appendChild(contracts);
	// contracts.appendChild(element);
	// //合同流水号
	// Element CONTRACT_NUM = doc.createElement("CONTRACT_NUM");
	// CONTRACT_NUM.setTextContent(info.getId().toString());
	// element.appendChild(CONTRACT_NUM);
	//
	// //CONTRACT_CODE 合同编号
	// Element CONTRACT_CODE = doc.createElement("CONTRACT_CODE");
	// if(info.getContractNo()!=null){
	// CONTRACT_CODE.setTextContent(info.getContractNo());
	// }else{
	//
	// CONTRACT_CODE.setTextContent("");
	// }
	// element.appendChild(CONTRACT_CODE);
	// //doc.appendChild(element);
	// //合同名称 CONTRACT_NAME
	// Element CONTRACT_NAME = doc.createElement("CONTRACT_NAME");
	// if(info.getContractName()!=null){
	// CONTRACT_NAME.setTextContent(info.getContractName());
	//
	// }else{
	// CONTRACT_NAME.setTextContent("");
	// }
	// element.appendChild(CONTRACT_NAME);
	//
	// //ORG_CODE 签约公司代码
	// Element ORG_CODE = doc.createElement("ORG_CODE");
	// if(info.getContractComId()!=null){
	// ORG_CODE.setTextContent("4522");
	// //ORG_CODE.setTextContent(info.getContractComId()+"");
	// }else{
	// ORG_CODE.setTextContent("");
	// }
	// element.appendChild(ORG_CODE);
	// //ORG_NAME 签约公司名称
	// Element ORG_NAME =doc.createElement("ORG_NAME");
	// if(info.getContractComName()!=null){
	// ORG_NAME.setTextContent("安顺分公司");
	// //ORG_NAME.setTextContent(info.getContractComName());
	// }else{
	//
	// ORG_NAME.setTextContent("");
	// }
	// element.appendChild(ORG_NAME);
	// //CONTRACT_SOURCE_TYPE 合同来源
	// Element CONTRACT_SOURCE_TYPE = doc.createElement("CONTRACT_SOURCE_TYPE");
	// if(info.getContractPartyOriginId()!=null){
	//
	// CONTRACT_SOURCE_TYPE.setTextContent(info.getContractPartyOriginId());
	//
	// }else{
	// CONTRACT_SOURCE_TYPE.setTextContent("");
	// }
	// element.appendChild(CONTRACT_SOURCE_TYPE);
	// //CONTRACT_TYPE 合同类型
	// Element CONTRACT_TYPE = doc.createElement("CONTRACT_TYPE");
	// if(info.getContractAmountType()!=null){
	// CONTRACT_TYPE.setTextContent(info.getContractAmountType());
	// }else{
	// CONTRACT_TYPE.setTextContent("");
	// }
	// element.appendChild(CONTRACT_TYPE);
	// //SOURCING_METHOD 采购方式 合同对方选择方式
	// Element SOURCING_METHOD = doc.createElement("SOURCING_METHOD");
	// if(info.getContractProjectSetupTypeId()!=null){
	// //
	// if(info.getContractProjectSetupTypeId()=="01"||"01".equals(info.getContractProjectSetupTypeId())){
	// // SOURCING_METHOD.setTextContent("公开招投标");
	// // }
	// //
	// if(info.getContractProjectSetupTypeId()=="02"||"02".equals(info.getContractProjectSetupTypeId())){
	// // SOURCING_METHOD.setTextContent("邀请招标");
	// // }
	// //
	// if(info.getContractProjectSetupTypeId()=="03"||"03".equals(info.getContractProjectSetupTypeId())){
	// // SOURCING_METHOD.setTextContent("竞争性谈判");
	// // }
	// //
	// if(info.getContractProjectSetupTypeId()=="04"||"04".equals(info.getContractProjectSetupTypeId())){
	// // SOURCING_METHOD.setTextContent("单一来源采购");
	// // }
	// //
	// if(info.getContractProjectSetupTypeId()=="05"||"05".equals(info.getContractProjectSetupTypeId())){
	// // SOURCING_METHOD.setTextContent("询价");
	// // }
	// //
	// if(info.getContractProjectSetupTypeId()=="06"||"06".equals(info.getContractProjectSetupTypeId())){
	// // SOURCING_METHOD.setTextContent("其他");
	// // }
	// SOURCING_METHOD.setTextContent(info.getContractProjectSetupTypeId());
	// }else{
	// SOURCING_METHOD.setTextContent("");
	// }
	// element.appendChild(SOURCING_METHOD);
	// IContractPartyHandler partyHandler = (IContractPartyHandler)
	// ContractBeanFactory
	// .getBean("IContractPartyHandler");
	// StringBuffer sb1 = new StringBuffer();
	// StringBuffer sb2 = new StringBuffer();
	// List ov = partyHandler.getContractPartyByContractSn(info.getId());
	// if(ov.size()>0){
	// for(int i=0;i<ov.size();i++){
	// TContractParty party = (TContractParty) ov.get(i);
	// sb1.append(party.getContractSupplierCode());
	// sb2.append(party.getContractSupplierName());
	// if(i+1==ov.size()){
	//
	// sb1.append("");
	// sb2.append("");
	// }else{
	// sb1.append("@");
	// sb2.append("@");
	// }
	// }
	//
	//
	// }else{
	//
	// System.out.println("合同无供应商"+info.getId()+ info.getContractName());
	// sb1.append("");
	// sb2.append("");
	// }
	// //VENDOR_NUMBER 供应商
	// Element VENDOR_NUMBER = doc.createElement("VENDOR_NUMBER");
	// VENDOR_NUMBER.setTextContent(sb1.toString());
	// element.appendChild(VENDOR_NUMBER);
	// //VENDOR_NAME 供应商名称
	// Element VENDOR_NAME = doc.createElement("VENDOR_NAME");
	// VENDOR_NAME.setTextContent(sb2.toString());
	// element.appendChild(VENDOR_NAME);
	// //SIGN��_CODE 签约人
	// Element SIGN_CODE = doc.createElement("SIGN_CODE");
	// SIGN_CODE.setTextContent("");
	// element.appendChild(SIGN_CODE);
	// //SIGNN_DATE 签约日期
	// Element SIGNN_DATE = doc.createElement("SIGN_DATE");
	// if(info.getContractSealTime()!=null){
	// SIGNN_DATE.setTextContent(sdf.format(info.getContractSealTime()));
	// }else{
	// SIGNN_DATE.setTextContent("");
	// }
	// element.appendChild(SIGNN_DATE);
	// //START_DATE_ACTIVE 起始时间爱你
	// Element START_DATE_ACTIVE = doc.createElement("START_DATE_ACTIVE");
	// if(info.getContractActualStartTime()!=null){
	// START_DATE_ACTIVE.setTextContent(sdf.format(info.getContractActualStartTime()).replace(" ",
	// "T"));
	// }else{
	// START_DATE_ACTIVE.setTextContent("");
	// }
	// element.appendChild(START_DATE_ACTIVE);
	// //END_DATE_ACTIVE 合同有效期至
	// Element END_DATE_ACTIVE = doc.createElement("END_DATE_ACTIVE");
	// if(info.getContractActualEndTime()!=null){
	// END_DATE_ACTIVE.setTextContent(sdf.format(info.getContractActualEndTime()).replace(" ",
	// "T"));
	// }else{
	// END_DATE_ACTIVE.setTextContent("");
	// }
	// element.appendChild(END_DATE_ACTIVE);
	// //CREATED_DATE 创建日期
	// Element CREATED_DATE = doc.createElement("CREATED_DATE");
	// if(info.getCreateTime()!=null){
	// CREATED_DATE.setTextContent(new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreateTime()).replace(" ",
	// "T"));
	// }else{
	// CREATED_DATE.setTextContent("");
	// }
	// element.appendChild(CREATED_DATE);
	// //CREATOR_CODE 经办人
	// Element CREATOR_CODE = doc.createElement("CREATOR_CODE");
	// CREATOR_CODE.setTextContent(info.getContractDrafter());
	// element.appendChild(CREATOR_CODE);
	// //FUND_TYPE
	// Element FUND_TYPE = doc.createElement("FUND_TYPE");
	// FUND_TYPE.setTextContent("01");
	// element.appendChild(FUND_TYPE);
	// //PAYEMNT_TERM 付款条款
	// Element PAYEMNT_TERM = doc.createElement("PAYMENT_TERM");
	// PAYEMNT_TERM.setTextContent("01");
	// element.appendChild(PAYEMNT_TERM);
	// //CURRENCY_CODE 币种
	// Element CURRENCY_CODE = doc.createElement("CURRENCY_CODE");
	// if(info.getContractCurrency()!=null){
	//
	//
	// switch (Integer.parseInt(info.getContractCurrency())) {
	// case 1:
	// CURRENCY_CODE.setTextContent("人民币");
	// break;
	// case 2:
	// CURRENCY_CODE.setTextContent("英镑");
	// break;
	// case 3:
	// CURRENCY_CODE.setTextContent("港币");
	// break;
	// case 4:
	// CURRENCY_CODE.setTextContent("美元");
	// break;
	// case 5:
	// CURRENCY_CODE.setTextContent("瑞士法郎");
	// break;
	// case 6:
	// CURRENCY_CODE.setTextContent("新加坡元");
	// break;
	// case 7:
	// CURRENCY_CODE.setTextContent("瑞典克朗");
	// break;
	// case 8:
	// CURRENCY_CODE.setTextContent("丹麦克朗");
	// break;
	// case 9:
	// CURRENCY_CODE.setTextContent("挪威克朗");
	// break;
	// case 10:
	// CURRENCY_CODE.setTextContent("日元");
	// break;
	// case 11:
	// CURRENCY_CODE.setTextContent("加拿大元");
	// break;
	// case 12:
	// CURRENCY_CODE.setTextContent("澳大利亚元");
	// break;
	// case 13:
	// CURRENCY_CODE.setTextContent("欧元");
	// break;
	// case 14:
	// CURRENCY_CODE.setTextContent("澳门元");
	// break;
	// case 15:
	// CURRENCY_CODE.setTextContent("菲律宾比索");
	// break;
	// case 16:
	// CURRENCY_CODE.setTextContent("泰国铢");
	// break;
	// case 17:
	// CURRENCY_CODE.setTextContent("新西兰元");
	// break;
	// case 18:
	// CURRENCY_CODE.setTextContent("韩国元");
	// break;
	// case 19:
	// CURRENCY_CODE.setTextContent("其它");
	// break;
	// default:
	// break;
	// }
	// }else{
	// CURRENCY_CODE.setTextContent("");
	//
	// }
	// element.appendChild(CURRENCY_CODE);
	// //CONVERSION_RATE 汇率
	// Element CONVERSION_RATE = doc.createElement("CONVERSION_RATE");
	// if(info.getContractExchangeRate()!=null){
	// CONVERSION_RATE.setTextContent(info.getContractExchangeRate()+"");
	// }else{
	// CONVERSION_RATE.setTextContent("");
	//
	// }
	// element.appendChild(CONVERSION_RATE);
	// //PAYMENT_METHOD 付款方式
	// Element PAYMENT_METHOD = doc.createElement("PAYMENT_METHOD");
	// PAYMENT_METHOD.setTextContent("01");
	// element.appendChild(PAYMENT_METHOD);
	// //CONTRACT_AMOUNT 合同金额
	// Element CONTRACT_AMOUNT = doc.createElement("CONTRACT_AMOUNT");
	// if(info.getContractAmount()!=null){
	// CONTRACT_AMOUNT.setTextContent(info.getContractAmount()+"");
	// }else{
	// CONTRACT_AMOUNT.setTextContent("");
	// }
	// element.appendChild(CONTRACT_AMOUNT);
	// //CONTRACT_DESCRIPTION 合同描述
	// Element CONTRACT_DESCRIPTION = doc.createElement("CONTRACT_DESCRIPTION");
	// CONTRACT_DESCRIPTION.setTextContent("測試 ");
	// element.appendChild(CONTRACT_DESCRIPTION);
	// //CONTRACT_STATUS 合同状态
	// Element CONTRACT_STATUS = doc.createElement("CONTRACT_STATUS");
	// if(info.getContractAuditState()!=null){
	// CONTRACT_STATUS.setTextContent(info.getContractAuditState());
	// }else{
	// CONTRACT_STATUS.setTextContent("");
	// }
	// element.appendChild(CONTRACT_STATUS);
	// //OU_CODE 合同组织代码
	// Element OU_CODE = doc.createElement("OU_CODE");
	// if(info.getContractComId()!=null){
	// //OU_CODE.setTextContent(info.getContractComId()+"");
	// OU_CODE.setTextContent("00450022003600000000");
	// }else{
	// OU_CODE.setTextContent("");
	// }
	// element.appendChild(OU_CODE);
	// //CONTRACT_URL参数
	// Element CONTRACT_URL = doc.createElement("CONTRACT_URL");
	// CONTRACT_URL.setTextContent("http://emis.gz.cmcc/contractMgt/ContractApproveAction.do?method=getContractApproveView&pmFlag=Y&contractId="+Encryptor.encrypt(info.getId()));
	// element.appendChild(CONTRACT_URL);
	// //LAST_UPDATE_DATE
	// Element LAST_UPDATE_DATE = doc.createElement("LAST_UPDATE_DATE");
	// if(info.getLastUpdateTime()!=null){
	// LAST_UPDATE_DATE.setTextContent(sdf.format(info.getLastUpdateTime()));
	// }else{
	// LAST_UPDATE_DATE.setTextContent("");
	// }
	// element.appendChild(LAST_UPDATE_DATE);
	// Element projects = doc.createElement("PROJECT_LINES");
	// element.appendChild(projects);
	// //合同编号
	// Element PROJECT_NUMBER = doc.createElement("PROJECT_NUMBER");
	// if(info.getContractProjectNo()!=null){
	// PROJECT_NUMBER.setTextContent(info.getContractProjectNo());
	// }else{
	// PROJECT_NUMBER.setTextContent("");
	// }
	// projects.appendChild(PROJECT_NUMBER);
	// //PROJECT_NAME 合同名字
	// Element PROJECT_NAME = doc.createElement("PROJECT_NAME");
	// if(info.getContractProjectName()!=null){
	// PROJECT_NAME.setTextContent(info.getContractProjectName());
	// }else{
	// PROJECT_NAME.setTextContent("");
	// }
	// projects.appendChild(PROJECT_NAME);
	// //Amount 金额
	// Element Amount = doc.createElement("AMOUNT");
	// if(info.getContractAmount()!=null){
	// Amount.setTextContent(info.getContractAmount()+"");
	// }else{
	// Amount.setTextContent("");
	// }
	// projects.appendChild(Amount);
	// StringWriter output = new StringWriter();
	// TransformerFactory.newInstance().newTransformer().transform(new
	// DOMSource(input), new StreamResult(output));
	// System.out.println("=====生成 XML为"+output.toString());
	// return output.toString();
	// }
}
