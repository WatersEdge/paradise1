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

package l1j.server.server;

/**
 *	动作代码
 */
public class ActionCodes {

	public ActionCodes() {
	}
	/** 出现动作 */
	public static final int ACTION_Appear = 4;
	/** 隐藏动作 */
	public static final int ACTION_Hide = 11;
	/** 安塔瑞斯隐藏动作 */
	public static final int ACTION_AntharasHide = 20;
	/** 行走动作 */
	public static final int ACTION_Walk = 0;
	/** 攻击动作 */
	public static final int ACTION_Attack = 1;
	/** 伤害动作 */
	public static final int ACTION_Damage = 2;
	/** 空闲动作 */
	public static final int ACTION_Idle = 3;
	/** 剑行走动作 */
	public static final int ACTION_SwordWalk = 4;
	/** 剑攻击动作 */
	public static final int ACTION_SwordAttack = 5;
	/** 剑伤害动作 */
	public static final int ACTION_SwordDamage = 6;
	/** 剑空闲动作 */
	public static final int ACTION_SwordIdle = 7;
	/** 死亡动作 */
	public static final int ACTION_Die = 8;
	/** 斧行走动作 */
	public static final int ACTION_AxeWalk = 11;
	/** 斧攻击动作 */
	public static final int ACTION_AxeAttack = 12;
	/** 斧伤害动作 */
	public static final int ACTION_AxeDamage = 13;
	/** 斧空闲动作 */
	public static final int ACTION_AxeIdle = 14;
	/** 隐藏伤害动作 */
	public static final int ACTION_HideDamage = 13;
	/** 隐藏空闲动作 */
	public static final int ACTION_HideIdle = 14;
	/** 拾起动作 */
	public static final int ACTION_Pickup = 15;
	/** 抛掷动作 */
	public static final int ACTION_Throw = 16;
	/** 魔杖动作 */
	public static final int ACTION_Wand = 17;
	/** 攻击技能动作 */
	public static final int ACTION_SkillAttack = 18;
	/** Buff技能动作 */
	public static final int ACTION_SkillBuff = 19;
	/** 弓行走动作 */
	public static final int ACTION_BowWalk = 20;
	/** 弓攻击动作 */
	public static final int ACTION_BowAttack = 21;
	/** 弓伤害动作 */
	public static final int ACTION_BowDamage = 22;
	/** 弓空闲动作 */
	public static final int ACTION_BowIdle = 23;
	/** 矛行走动作 */
	public static final int ACTION_SpearWalk = 24;
	/** 矛攻击动作 */
	public static final int ACTION_SpearAttack = 25;
	/** 矛伤害动作 */
	public static final int ACTION_SpearDamage = 26;
	/** 矛空闲动作 */
	public static final int ACTION_SpearIdle = 27;
	/** 开动作 */
	public static final int ACTION_On = 28;
	/** 关动作 */
	public static final int ACTION_Off = 29;
	/** 开门动作 */
	public static final int ACTION_Open = 28;
	/** 关门动作 */
	public static final int ACTION_Close = 29;
	/** 方向(南)动作 */
	public static final int ACTION_South = 28;
	/** 方向(西)动作 */
	public static final int ACTION_West = 29;
	/** Alt键攻击动作 */
	public static final int ACTION_AltAttack = 30;
	/** 额外法术方向动作 */
	public static final int ACTION_SpellDirectionExtra = 31;
	/** 塔裂痕动作1 */
	public static final int ACTION_TowerCrack1 = 32;
	/** 塔裂痕动作 2*/
	public static final int ACTION_TowerCrack2 = 33;
	/** 塔裂痕动作 3*/
	public static final int ACTION_TowerCrack3 = 34;
	/** 塔死亡动作 */
	public static final int ACTION_TowerDie = 35;
	/** 门动作1 */
	public static final int ACTION_DoorAction1 = 32;
	/** 门动作 2*/
	public static final int ACTION_DoorAction2 = 33;
	/** 门动作 3*/
	public static final int ACTION_DoorAction3 = 34;
	/** 门动作 4*/
	public static final int ACTION_DoorAction4 = 35;
	/** 门动作 5*/
	public static final int ACTION_DoorAction5 = 36;
	/** 门死亡动作 */
	public static final int ACTION_DoorDie = 37;
	/** 角色行走动作 */
	public static final int ACTION_StaffWalk = 40;
	/** 角色攻击动作 */
	public static final int ACTION_StaffAttack = 41;
	/** 角色伤害动作 */
	public static final int ACTION_StaffDamage = 42;
	/** 角色空闲动作 */
	public static final int ACTION_StaffIdle = 43;
	/** 上移动作 */
	public static final int ACTION_Moveup = 44;
	/** 下移动作 */
	public static final int ACTION_Movedown = 45;
	/** 匕首行走动作 */
	public static final int ACTION_DaggerWalk = 46;
	/** 匕首攻击动作 */
	public static final int ACTION_DaggerAttack = 47;
	/** 匕首伤害动作 */
	public static final int ACTION_DaggerDamage = 48;
	/** 匕首空闲动作 */
	public static final int ACTION_DaggerIdle = 49;
	/** 双手剑行走动作 */
	public static final int ACTION_TwoHandSwordWalk = 50;
	/** 双手剑攻击动作 */
	public static final int ACTION_TwoHandSwordAttack = 51;
	/** 双手剑伤害动作 */
	public static final int ACTION_TwoHandSwordDamage = 52;
	/** 双手剑空闲动作 */
	public static final int ACTION_TwoHandSwordIdle = 53;
	/** 双刀行走动作 */
	public static final int ACTION_EdoryuWalk = 54;
	/** 双刀攻击动作 */
	public static final int ACTION_EdoryuAttack = 55;
	/** 双刀伤害动作 */
	public static final int ACTION_EdoryuDamage = 56;
	/** 双刀空闲动作 */
	public static final int ACTION_EdoryuIdle = 57;
	/** 双爪行走动作 */
	public static final int ACTION_ClawWalk = 58;
	/** 双爪攻击动作 */
	public static final int ACTION_ClawAttack = 59;
	/** 双爪空闲动作 */
	public static final int ACTION_ClawIdle = 61;
	/** 双爪伤害动作 */
	public static final int ACTION_ClawDamage = 60;
	/** 投掷飞刀动作 */
	public static final int ACTION_ThrowingKnifeWalk = 62;
	/** 飞刀攻击动作 */
	public static final int ACTION_ThrowingKnifeAttack = 63;
	/** 飞刀伤害动作 */
	public static final int ACTION_ThrowingKnifeDamage = 64;
	/** 投掷飞刀空闲动作 */
	public static final int ACTION_ThrowingKnifeIdle = 65;
	/** 思考动作 Alt+4 */
	public static final int ACTION_Think = 66; // Alt+4
	/** 挑衅动作 Alt+3 */
	public static final int ACTION_Aggress = 67; // Alt+3
	/** 迎接动作 Alt+1 */
	public static final int ACTION_Salute = 68; // Alt+1
	/** 欢呼动作 Alt+2 */
	public static final int ACTION_Cheer = 69; // Alt+2
	/** 商店动作 */
	public static final int ACTION_Shop = 70;
	/** 钓鱼动作 */
	public static final int ACTION_Fishing = 71;

}
