
package util;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

/**
 * 改变实体BASEDN的类。 项目名称：i4a-v2.0
 * <p>
 * Title: MoveEntryUtil
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SI-TECH
 * </p>
 * 
 * @author xingzy 
 * 创建时间：Aug 28, 2009 2:45:04 PM
 * @version 1.0 
 * 修改人：xingzy 
 * 修改时间：Aug 28, 2009 2:45:04 PM 
 * 修改备注：
 * 
 */
public class MoveEntryUtil {

	private static final int BASE = SearchControls.OBJECT_SCOPE;
	private static final int ONE = SearchControls.ONELEVEL_SCOPE;
	private static final int SUB = SearchControls.SUBTREE_SCOPE;

	// logging for this class
	private static Logger log = Logger.getLogger(MoveEntryUtil.class);

	/**
	 * 
	 * renameTree TODO
	 * 
	 * @param
	 * @param move
	 *            为true的时候，则移除所有旧的节点。
	 * @return boolean
	 * @Exception
	 */
	public static boolean renameTree(DirContext ctx, String oldDN,
			String newDN, boolean move) {
		ArrayList entries = new ArrayList();
		String dnN, dnO, dnTmp;
		BasicAttributes attr;
		boolean rs;
		int i;
		try {
			NamingEnumeration results = search2(ctx, oldDN, "(objectclass=*)",
					null, SUB);
			while (results != null && results.hasMoreElements()) {
				SearchResult si = (SearchResult) results.next();
				entries.add(si.getName().trim());
				entries.add(si.getAttributes());
			}
			for (i = 0; i < entries.size(); i += 2) {
				dnTmp = (String) entries.get(i);
				attr = (BasicAttributes) entries.get(i + 1);
				if (dnTmp.length() == 0) {
					dnO = oldDN;
					dnN = newDN;
				} else {
					dnO = dnTmp + ", " + oldDN;
					dnN = dnTmp + ", " + newDN;
				}
				attr = renameAttr(ctx, oldDN, newDN, attr);
				ctx.createSubcontext(dnN, attr);
			}
			if (move) {
				for (i = entries.size() - 2; i >= 0; i -= 2) {
					dnTmp = (String) entries.get(i);
					if (dnTmp.length() == 0)
						ctx.destroySubcontext(oldDN);
					else
						ctx.destroySubcontext(dnTmp + ", " + oldDN);
				}
			}
		} catch (NamingException e) {
			if (move)
				log.error("Failed during tree move!", e);
			else
				log.error("Failed during tree copy!", e);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * renameAttr TODO
	 * 
	 * @param
	 * @return BasicAttributes
	 * @Exception
	 */
	private static BasicAttributes renameAttr(DirContext ctx, String o,
			String n, BasicAttributes attr) throws NamingException {

		BasicAttributes newAttrib = new BasicAttributes();

		String attrName, value;
		Object tmp;

		for (NamingEnumeration ae = attr.getAll(); ae.hasMoreElements();) {
			BasicAttribute at = (BasicAttribute) ae.next();

			attrName = at.getID();
			Enumeration vals = at.getAll();

			BasicAttribute newAt = new BasicAttribute(attrName);

			while (vals.hasMoreElements()) {
				tmp = vals.nextElement();
				if (tmp instanceof byte[]) {
					newAt.add(tmp);
				} else {
					value = tmp.toString();
					value = replace(o, n, value);
					newAt.add(value);
				}
			}

			newAttrib.put(newAt);
		}

		return newAttrib;
	}

	/**
	 * 
	 * replace  把相关的属性值进行替换。
	 * TODO 
	 * @param    
	 * @return String
	 * @Exception
	 */
	private static String replace(String o, String n, String msg) {

		StringBuffer nmsg = new StringBuffer();

		int to = o.length();

		if (to > msg.length())
			return msg;

		for (int i = 0; i < msg.length(); i++) {
			if ((i + to) > msg.length()) {
				nmsg.append(msg.substring(i));
				break;
			}

			String tmp = msg.substring(i, i + to);

			if (tmp.equalsIgnoreCase(o)) {
				nmsg.append(n);
				i += to - 1;
			} else {
				nmsg.append(msg.charAt(i));
			}

		}

		return nmsg.toString();
	}

	/**
	 * 
	 * search2 查询 baseDn 为 oldDN的所有子节点。 TODO
	 * 
	 * @param
	 * @return NamingEnumeration
	 * @Exception
	 */
	private static NamingEnumeration search2(DirContext ctx, String dn,
			String filter, String[] attribs, int type) throws NamingException {
		SearchControls constraints = new SearchControls();

		constraints.setSearchScope(type);
		constraints.setReturningAttributes(attribs);

		NamingEnumeration results = ctx.search(dn, filter, constraints);
		return results;
	}
}
