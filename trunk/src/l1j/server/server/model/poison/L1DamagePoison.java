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
package l1j.server.server.model.poison;

import static l1j.server.server.model.skill.L1SkillId.STATUS_POISON;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 毒伤害
 */
public class L1DamagePoison extends L1Poison {

	private class NormalPoisonTimer extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(_damageSpan);
				}
				catch (final InterruptedException e) {
					break;
				}

				if (!_target.hasSkillEffect(STATUS_POISON)) {
					break;
				}
				if (_target instanceof L1PcInstance) {
					final L1PcInstance player = (L1PcInstance) _target;
					player.receiveDamage(_attacker, _damage, false);
					if (player.isDead()) { // 死亡时解毒
						break;
					}
				}
				else if (_target instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) _target;
					mob.receiveDamage(_attacker, _damage);
					if (mob.isDead()) { // 死亡时解毒
						return;
					}
				}
			}
			cure(); // 解毒处理
		}
	}

	public static boolean doInfection(final L1Character attacker, final L1Character cha, final int damageSpan, final int damage) {
		if (!isValidTarget(cha)) {
			return false;
		}

		cha.setPoison(new L1DamagePoison(attacker, cha, damageSpan, damage));
		return true;
	}

	private Thread _timer;

	private final L1Character _attacker;

	private final L1Character _target;

	private final int _damageSpan;

	private final int _damage;

	private L1DamagePoison(final L1Character attacker, final L1Character cha, final int damageSpan, final int damage) {
		_attacker = attacker;
		_target = cha;
		_damageSpan = damageSpan;
		_damage = damage;

		doInfection();
	}

	@Override
	public void cure() {
		if (_timer != null) {
			_timer.interrupt(); // 解毒计时器
		}

		_target.setPoisonEffect(0);
		_target.killSkillEffectTimer(STATUS_POISON);
		_target.setPoison(null);
	}

	@Override
	public int getEffectId() {
		return 1;
	}

	private void doInfection() {
		_target.setSkillEffect(STATUS_POISON, 30000);
		_target.setPoisonEffect(1);

		if (isDamageTarget(_target)) {
			_timer = new NormalPoisonTimer();
			GeneralThreadPool.getInstance().execute(_timer); // 通常毒タイマー開始
		}
	}

	boolean isDamageTarget(final L1Character cha) {
		return (cha instanceof L1PcInstance) || (cha instanceof L1MonsterInstance);
	}
}
