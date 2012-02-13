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
package l1j.server.server.templates;

public class L1NpcChat {
	private int _npcId;

	private int _chatTiming;

	private int _startDelayTime;

	private String _chatId1;

	private String _chatId2;

	private String _chatId3;

	private String _chatId4;

	private String _chatId5;

	private int _chatInterval;

	private boolean _isShout;

	private boolean _isWorldChat;

	private boolean _isRepeat;

	private int _repeatInterval;

	private int _gameTime;

	public L1NpcChat() {
	}

	public String getChatId1() {
		return _chatId1;
	}

	public String getChatId2() {
		return _chatId2;
	}

	public String getChatId3() {
		return _chatId3;
	}

	public String getChatId4() {
		return _chatId4;
	}

	public String getChatId5() {
		return _chatId5;
	}

	public int getChatInterval() {
		return _chatInterval;
	}

	public int getChatTiming() {
		return _chatTiming;
	}

	public int getGameTime() {
		return _gameTime;
	}

	public int getNpcId() {
		return _npcId;
	}

	public int getRepeatInterval() {
		return _repeatInterval;
	}

	public int getStartDelayTime() {
		return _startDelayTime;
	}

	public boolean isRepeat() {
		return _isRepeat;
	}

	public boolean isShout() {
		return _isShout;
	}

	public boolean isWorldChat() {
		return _isWorldChat;
	}

	public void setChatId1(final String s) {
		_chatId1 = s;
	}

	public void setChatId2(final String s) {
		_chatId2 = s;
	}

	public void setChatId3(final String s) {
		_chatId3 = s;
	}

	public void setChatId4(final String s) {
		_chatId4 = s;
	}

	public void setChatId5(final String s) {
		_chatId5 = s;
	}

	public void setChatInterval(final int i) {
		_chatInterval = i;
	}

	public void setChatTiming(final int i) {
		_chatTiming = i;
	}

	public void setGameTime(final int i) {
		_gameTime = i;
	}

	public void setNpcId(final int i) {
		_npcId = i;
	}

	public void setRepeat(final boolean flag) {
		_isRepeat = flag;
	}

	public void setRepeatInterval(final int i) {
		_repeatInterval = i;
	}

	public void setShout(final boolean flag) {
		_isShout = flag;
	}

	public void setStartDelayTime(final int i) {
		_startDelayTime = i;
	}

	public void setWorldChat(final boolean flag) {
		_isWorldChat = flag;
	}

}
