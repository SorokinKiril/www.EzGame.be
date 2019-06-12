package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the character_achievement database table.
 * 
 */
@Entity
@Table(name="character_achievement")
public class CharacterAchievement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CHARACTER_ACHIEVEMENT_ID")
	private Integer characterAchievementId;

	@Column(name="ACHIEVEMENT_STATUS")
	private boolean achievementStatus;

	//bi-directional many-to-one association to Achievement
	@ManyToOne
	private Achievement achievement;

	//bi-directional many-to-one association to Character
	@ManyToOne
	private Character character;

	public CharacterAchievement() {
	}

	public Integer getCharacterAchievementId() {
		return this.characterAchievementId;
	}

	public void setCharacterAchievementId(Integer characterAchievementId) {
		this.characterAchievementId = characterAchievementId;
	}

	public boolean isAchievementStatus() {
		return achievementStatus;
	}

	public void setAchievementStatus(boolean achievementStatus) {
		this.achievementStatus = achievementStatus;
	}

	public Achievement getAchievement() {
		return this.achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}

	public Character getCharacter() {
		return this.character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

}