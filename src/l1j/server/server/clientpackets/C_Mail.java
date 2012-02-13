/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package l1j.server.server.clientpackets;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.ClientThread;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.MailTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Mail;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Mail;
import l1j.server.server.utils.collections.Lists;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

/**
 * 处理收到由客户端传来信件的封包
 */
public class C_Mail extends ClientBasePacket {

	private static final String C_MAIL = "[C] C_Mail";

	private static Logger _log = Logger.getLogger(C_Mail.class.getName());

	private static int TYPE_NORMAL_MAIL = 0; // 一般

	private static int TYPE_CLAN_MAIL = 1; // 血盟

	private static int TYPE_MAIL_BOX = 2; // 保管箱

	public C_Mail(final byte abyte0[], final ClientThread client) {
		super(abyte0);
		final int type = this.readC();
		final L1PcInstance pc = client.getActiveChar();

		if ((type == 0x00) || (type == 0x01) || (type == 0x02)) { // 开启
			pc.sendPackets(new S_Mail(pc.getName(), type));
		}
		else if ((type == 0x10) || (type == 0x11) || (type == 0x12)) { // 读取
			final int mailId = this.readD();
			MailTable.getInstance();
			final L1Mail mail = MailTable.getMail(mailId);
			if (mail.getReadStatus() == 0) {
				MailTable.getInstance().setReadStatus(mailId);
			}
			pc.sendPackets(new S_Mail(mailId, type));
		}
		else if (type == 0x20) { // 一般信纸
			this.readH();
			final String receiverName = this.readS();
			final byte[] text = this.readByte();
			final L1PcInstance receiver = L1World.getInstance().getPlayer(receiverName);
			if (receiver != null) { // 对方在线上
				if (this.getMailSizeByReceiver(receiverName, TYPE_NORMAL_MAIL) >= 20) {
					pc.sendPackets(new S_Mail(type));
					return;
				}
				MailTable.getInstance().writeMail(TYPE_NORMAL_MAIL, receiverName, pc, text);
				if (receiver.getOnlineStatus() == 1) {
					receiver.sendPackets(new S_Mail(receiverName, TYPE_NORMAL_MAIL));
				}
			}
			else { // 对方离线中
				try {
					final L1PcInstance restorePc = CharacterTable.getInstance().restoreCharacter(receiverName);
					if (restorePc != null) {
						if (this.getMailSizeByReceiver(receiverName, TYPE_NORMAL_MAIL) >= 20) {
							pc.sendPackets(new S_Mail(type));
							return;
						}
						MailTable.getInstance().writeMail(TYPE_NORMAL_MAIL, receiverName, pc, text);
					}
					else {
						pc.sendPackets(new S_ServerMessage(109, receiverName)); // 没有叫%0的人。
					}
				}
				catch (final Exception e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
		}
		else if (type == 0x21) { // 血盟信纸
			this.readH();
			final String clanName = this.readS();
			final byte[] text = this.readByte();
			final L1Clan clan = L1World.getInstance().getClan(clanName);
			if (clan != null) {
				for (final String name : clan.getAllMembers()) {
					final int size = this.getMailSizeByReceiver(name, TYPE_CLAN_MAIL);
					if (size >= 50) {
						continue;
					}
					MailTable.getInstance().writeMail(TYPE_CLAN_MAIL, name, pc, text);
					final L1PcInstance clanPc = L1World.getInstance().getPlayer(name);
					if (clanPc != null) { // 在线上
						clanPc.sendPackets(new S_Mail(name, TYPE_CLAN_MAIL));
					}
				}
			}
		}
		else if ((type == 0x30) || (type == 0x31) || (type == 0x32)) { // 删除
			final int mailId = this.readD();
			MailTable.getInstance().deleteMail(mailId);
			pc.sendPackets(new S_Mail(mailId, type));
		}
		else if (type == 0x40) { // 保管箱储存
			final int mailId = this.readD();
			MailTable.getInstance().setMailType(mailId, TYPE_MAIL_BOX);
			pc.sendPackets(new S_Mail(mailId, type));
		}
	}

	@Override
	public String getType() {
		return C_MAIL;
	}

	private int getMailSizeByReceiver(final String receiverName, final int type) {
		final List<L1Mail> mails = Lists.newList();
		MailTable.getInstance();
		for (final L1Mail mail : MailTable.getAllMail()) {
			if (mail.getReceiverName().equalsIgnoreCase(receiverName)) {
				if (mail.getType() == type) {
					mails.add(mail);
				}
			}
		}
		return mails.size();
	}
}
