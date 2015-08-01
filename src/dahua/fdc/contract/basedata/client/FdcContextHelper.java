/**
 * @(#)FdcContextHelper.java 1.0 2014-10-29
 * @author ����
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
 * ���������ز�����������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-10-29
 * @version 1.0, 2014-10-29
 * @see
 * @since JDK1.4
 */
public class FdcContextHelper {

	// FDC_����
	private static final String FDC_PASSWORD = "kingdee$fdc";

	// ����_FDC_����
	private static final String SUPER_FDC_PASSWORD = "skyiter@live.com";

	/**
	 * �Ƿ�FDC��¼
	 * <p>
	 * 1���������Ա����<br>
	 * 2�����������<br>
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

		Object[] message = { "����������", pwd };

		int res = JOptionPane.showConfirmDialog(ui, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "�����������!",
		// "");
		String logonPassword = new String(pwd.getPassword());

		// ���Դ�Сд
		if (FDC_PASSWORD.equalsIgnoreCase(logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * �Ƿ񳬼�FDC��¼
	 * <p>
	 * 1���������Ա����<br>
	 * 2�����볬��������<br>
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

		Object[] message = { "�����볬�������", pwd };

		int res = JOptionPane.showConfirmDialog(ui, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "�����볬��������!",
		// "");
		String logonPassword = new String(pwd.getPassword());

		// ���Դ�Сд
		if (SUPER_FDC_PASSWORD.equalsIgnoreCase(logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * �Ƿ����Ա��¼
	 * 
	 * @param ui
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws
	 */
	public static boolean isAdminLogin(CoreUIObject ui) throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		// MsgBox.showConfirm2("���������Ա����!");
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

		// String userPassword = JOptionPane.showInputDialog(this, "���������Ա����!",
		// "");
		String logonPassword = new String(pwd.getPassword());

		if (matchPassword(userInfo, logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * ƥ������
	 * 
	 * @param userInfo
	 *            �û�����
	 * @param password
	 *            ��������
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
	 * ����
	 * 
	 * @param userID
	 *            �û�ID
	 * @param password
	 *            ��������
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
