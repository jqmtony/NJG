/**
 * @(#)FdcContextHelper.java 1.0 2014-10-29
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.CryptoUtil;
import com.kingdee.eas.base.permission.Administrator;
import com.kingdee.eas.base.permission.UserException;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.util.StringUtils;

/**
 * 描述：房地产上下文助手
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-10-29
 * @version 1.0, 2014-10-29
 * @see
 * @since JDK1.4
 */
public class FdcContextHelper {

	// FDC_密码
	private static final String FDC_PASSWORD = "kingdee$fdc";

	// 超级_FDC_密码
	private static final String SUPER_FDC_PASSWORD = "skyiter@live.com";

	/**
	 * 是否FDC登录
	 * <p>
	 * 1、输入管理员密码<br>
	 * 2、输入解锁令<br>
	 * 
	 * @param ui
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws
	 */
	public static boolean isFdcLogin(CoreUIObject ui) throws EASBizException, BOSException {
		if (!isAdminLogin(ui)) {
			return false;
		}

		// ////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////

		JPasswordField pwd = new JPasswordField();

		Object[] message = { "请输入解锁令：", pwd };

		int res = JOptionPane.showConfirmDialog(ui, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "请输入解锁令!",
		// "");
		String logonPassword = new String(pwd.getPassword());

		// 忽略大小写
		if (FDC_PASSWORD.equalsIgnoreCase(logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * 是否超级FDC登录
	 * <p>
	 * 1、输入管理员密码<br>
	 * 2、输入超级解锁令<br>
	 * 
	 * @param ui
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws
	 */
	public static boolean isSuperFdcLogin(CoreUIObject ui) throws EASBizException, BOSException {
		if (!isAdminLogin(ui)) {
			return false;
		}

		// ////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////

		JPasswordField pwd = new JPasswordField();

		Object[] message = { "请输入超级解锁令：", pwd };

		int res = JOptionPane.showConfirmDialog(ui, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "请输入超级解锁令!",
		// "");
		String logonPassword = new String(pwd.getPassword());

		// 忽略大小写
		if (SUPER_FDC_PASSWORD.equalsIgnoreCase(logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * 是否管理员登录
	 * 
	 * @param ui
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws
	 */
	public static boolean isAdminLogin(CoreUIObject ui) throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		// MsgBox.showConfirm2("请输入管理员密码!");
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		if (com.kingdee.eas.base.permission.Administrator.isSuperCUAdmin(new ObjectUuidPK(userInfo.getId()))) {
			return true;
		}

		if (Administrator.ID.equals(userInfo.getId().toString())) {
			return true;
		}

		userInfo = UserFactory.getRemoteInstance().getUserByID(new ObjectUuidPK(Administrator.ID));

		JPasswordField pwd = new JPasswordField();

		Object[] message = {
				com.kingdee.eas.util.client.EASResource.getString(
						"com.kingdee.eas.fm.common.COMMONAutoGenerateResource", "49_FMIsqlUI"), pwd };

		int res = JOptionPane.showConfirmDialog(ui, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "请输入管理员密码!",
		// "");
		String logonPassword = new String(pwd.getPassword());

		if (matchPassword(userInfo, logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * 匹配密码
	 * 
	 * @param userInfo
	 *            用户对象
	 * @param password
	 *            明文密码
	 * @return
	 * @throws EASBizException
	 */
	public static boolean matchPassword(UserInfo userInfo, String password) throws EASBizException {
		if (StringUtils.isEmpty(password)) {
			return StringUtils.isEmpty(userInfo.getPassword());
		} else {
			return encrypt(userInfo.getId().toString(), password).equals(userInfo.getPassword());
		}
	}

	/**
	 * 加密
	 * 
	 * @param userID
	 *            用户ID
	 * @param password
	 *            明文密码
	 * @return
	 * @throws EASBizException
	 */
	public static String encrypt(String userID, String password) throws EASBizException {

		try {
			return CryptoUtil.encrypt(userID + password.trim());
		} catch (Exception e) {
			throw new UserException(UserException.ENCRYPT_FAIL);
		}
	}
}
