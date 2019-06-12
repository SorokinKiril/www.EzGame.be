package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the achievement database table.
 * 
 */
@Entity
public class Achievement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACHIEVEMENT_ID")
	private Integer achievementId;

	@Column(name="ACHIEVEMENT_DESCRIPTION")
	private String achievementDescription;

	@Column(name="ACHIEVEMENT_NAME")
	private String achievementName;

	//bi-directional many-to-one association to Title
	@ManyToOne
	private Title title;

	//bi-directional many-to-one association to CharacterAchievement
	@OneToMany(mappedBy="achievement")
	private List<CharacterAchievement> characterAchievements;

	public Achievement() {
	}

	public Integer getAchievementId() {
		return this.achievementId;
	}

	public void setAchievementId(Integer achievementId) {
		this.achievementId = achievementId;
	}

	public String getAchievementDescription() {
		return this.achievementDescription;
	}

	public void setAchievementDescription(String achievementDescription) {
		this.achievementDescription = achievementDescription;
	}

	public String getAchievementName() {
		return this.achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public List<CharacterAchievement> getCharacterAchievements() {
		return this.characterAchievements;
	}

	public void setCharacterAchievements(List<CharacterAchievement> characterAchievements) {
		this.characterAchievements = characterAchievements;
	}

	public CharacterAchievement addCharacterAchievement(CharacterAchievement characterAchievement) {
		getCharacterAchievements().add(characterAchievement);
		characterAchievement.setAchievement(this);

		return characterAchievement;
	}

	public CharacterAchievement removeCharacterAchievement(CharacterAchievement characterAchievement) {
		getCharacterAchievements().remove(characterAchievement);
		characterAchievement.setAchievement(null);

		return characterAchievement;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && achievementId != null)
				? achievementId.equals(((Achievement) other).achievementId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return (achievementId != null) ? (getClass().hashCode() + achievementId.hashCode()) : super.hashCode();
	}

}