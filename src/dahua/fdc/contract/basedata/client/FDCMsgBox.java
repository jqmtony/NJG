package com.kingdee.eas.fdc.basedata.client;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
/**
 * 
 */
public class FDCMsgBox
{
	private static Logger logger = Logger.getLogger(FDCMsgBox.class.getName());

	public static boolean isExceptionOccured = false;

	public final static int YES = JOptionPane.YES_OPTION;

	public final static int NO = JOptionPane.NO_OPTION;

	public final static int OK = JOptionPane.OK_OPTION;

	public final static int CANCEL = JOptionPane.CANCEL_OPTION;

	private static String title = EASResource.getString("promotInfoBox");
	
	private FDCMsgBox()	{}

	public static void setTitle(String value)
	{
		MsgBox.setTitle(value);
	}

	/**
	 * ?��????????????
	 */
	public static boolean isYes(int choice)
	{
		return choice == YES;
	}

	/**
	 * ?��????????????
	 */
	public static boolean isNo(int choice)
	{
		return choice == NO;
	}

	/**
	 * ?��????????????
	 */
	public static boolean isCancel(int choice)
	{
		return choice == CANCEL;
	}

	/**
	 * ?��????????????
	 */
	public static boolean isOk(int choice)
	{
		return choice == OK;
	}

	/**
	 * ?????????��??????????????,
	 */
	public static void showInfo(String info)
	{
		showInfo(null, info);
	}

	/**
	 * ????????��??????????????
	 */
	public static void showInfo(Component comp, String info)
	{
		if(isHidedBox()){
			logger.info(info);
			return;
		}
		MsgBox.showInfo(comp, info);
	}

	/**
	 * ????????��??????????????
	 */
	public static void showWarning(Component comp, String warning)
	{
		if(isHidedBox()){
			logger.info(warning);
			return;
		}
		MsgBox.showWarning(comp, warning);
	}

	/**
	 * ?????????��??????????????
	 */
	public static void showWarning(String warning)
	{
		showWarning(null, warning);
	}

	/**
	 * ???????????????????????(???????????????????????)
	 */
	public static void showError(String error)
	{
		showError((Component) null, error);
	}

	/**
	 * ????????��??????????????(???????????????????????)
	 */
	public static void showError(Component comp, String error)
	{
		if(isHidedBox()){
			logger.info(error);
			return;
		}
		MsgBox.showError(comp, error);

	}

	/**
	 * ?????????��???????????????????????????????????
	 * ????????????????????????????????????
	 */
	public static void showError(String error, String errorDetail)
	{
		showError((Component) null, error, errorDetail);
	}

	/**
	 * ????????��???????????????????????????????????
	 * ????????????????????????????????????
	 */
	public static void showError(Component comp, String error,
					String errorDetail)
	{
		if(isHidedBox()){
			logger.info(error);
			return;
		}
		MsgBox.showError(comp, error, errorDetail);
	}

	/**
	 * @param comp
	 *            ??????????
	 * @param error
	 *            ??????
	 * @param errorDetail
	 *            ?????????
	 * @param msgType :
	 *            ERROR_MESSAGE = 0 , INFORMATION_MESSAGE = 1 , WARNING_MESSAGE =
	 *            2 , QUESTION_MESSAGE = 3
	 */
	public static void showDetailAndOK(Component comp, String error,
					String errorDetail, int msgType)
	{
		if(isHidedBox()){
			logger.info("error:"+error+"\t detail:"+errorDetail);
			return;
		}
		MsgBox.showDetailAndOK(comp, error, errorDetail, msgType);
	}

	/**
	 * }??????????????? ?????????????????
	 */
	public static int showConfirm2(String msg)
	{
		return showConfirm2(null, msg);
	}

	/**
	 * }??????????????? ?????????????????
	 */
	public static int showConfirm2(Component comp, String msg)
	{
		if(isHidedBox()){
			logger.info(msg);
			return YES;
		}
		return MsgBox.showConfirm2(comp, msg);
	}

	public static int showConfirm2New(Component comp, String msg)
	{
		if(isHidedBox()){
			logger.info(msg);
			return YES;
		}
		return MsgBox.showConfirm2New(comp, msg);
	}

	/**
	 * ???????????????? ?????????????????
	 */
	public static int showConfirm3(String msg)
	{
		return showConfirm3(null, msg);
	}

	/**
	 * ???????????????? ?????????????????
	 */
	public static int showConfirm3(Component comp, String msg)
	{
		if(isHidedBox()){
			logger.info(msg);
			return YES;
		}
		return MsgBox.showConfirm3(comp, msg);
	}

	/**
	 * ?????????????????? ?????????????? ?????????????????
	 */
	public static int showConfirm3a(String msg, String detail)
	{
		return showConfirm3a(null, msg, detail);
	}

	/**
	 * ?????????????????? ?????????????? ?????????????????
	 */
	public static int showConfirm3a(Component comp, String msg, String detail)
	{
		if(isHidedBox()){
			logger.info("msg:"+msg+"\t detal:"+detail);
			return YES;
		}
		return MsgBox.showConfirm3a(comp, msg, detail);
	}

	public static void showConnectionError()
	{
		showConnectionError(null, null);
	}

	public static void showConnectionError(String msg)
	{
		showConnectionError(null, msg);
	}

	public static void showConnectionError(Component comp)
	{
		showConnectionError(comp, null);
	}

	public static void showConnectionError(Component comp, String msg)
	{
		if(isHidedBox()){
			logger.info(msg);
			return;
		}
		MsgBox.showConnectionError(comp);

	}
	
	/**
	 * @param comp
	 * @param error	��Ϣ
	 * @param errorDetailMap
	 *            ����Map
	 * @param msgType :
	 *            ERROR_MESSAGE = 0 , INFORMATION_MESSAGE = 1 , WARNING_MESSAGE =
	 *            2 , QUESTION_MESSAGE = 3
	 	����
		String error="test";
		Map errorDetailMap=new HashMap();
		errorDetailMap.put("head", new String[]{"����","ֵ"});
		errorDetailMap.put("data", new Object[][]{{"1","���⣱"},{"2","����2"}});
		errorDetailMap.put("format", new Object[]{"","amount",""});
		showTableDetailAndOK(null, error, errorDetailMap, 0);
	 */
	public static void showTableDetailAndOK(Component comp, String error,
					Map errorDetailMap, int msgType)
	{
		if(isHidedBox()){
			logger.info("error:"+error+"\t detail:"+errorDetailMap);
			return;
		}
		FDCTableMsgBox.createAdvMsgBox(comp, title, error, errorDetailMap, msgType,
				AdvMsgBox.DETAIL_OK_OPTION).show();
	}
	
	private static boolean isHidedBox=false;
	public static boolean isHidedBox() {
		return isHidedBox;
	}
	public static void setHidedBox(boolean isHidedBox){
		FDCMsgBox.isHidedBox=isHidedBox;
	}
	
	public static void main(String[] args){
		String error="test";
		Map errorDetailMap=new HashMap();
		errorDetailMap.put("head", new String[]{"����","ֵ"});
		errorDetailMap.put("data", new String[][]{{"title","����"},{"value","ֵ"}});
//		UIManager.setLookAndFeel("com.kingdee.bos.plaf.TeemsLookAndFeel");
		showTableDetailAndOK(null, error, errorDetailMap, 0);
	}
}