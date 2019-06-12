package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the title database table.
 * 
 */
@Entity

public class Title implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TITLE_ID")
	private Integer titleId;

	@Column(name = "TITLE_NAME")
	private String titleName;

	// bi-directional many-to-one association to Achievement
	@OneToMany(mappedBy = "title")
	private List<Achievement> achievements;

	// bi-directional many-to-one association to Character
	@OneToMany(mappedBy = "title")
	private List<Character> characters;

	public Title() {
	}

	public Integer getTitleId() {
		return this.titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<Achievement> getAchievements() {
		return this.achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public Achievement addAchievement(Achievement achievement) {
		getAchievements().add(achievement);
		achievement.setTitle(this);

		return achievement;
	}

	public Achievement removeAchievement(Achievement achievement) {
		getAchievements().remove(achievement);
		achievement.setTitle(null);

		return achievement;
	}

	public List<Character> getCharacters() {
		return this.characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public Character addCharacter(Character character) {
		getCharacters().add(character);
		character.setTitle(this);

		return character;
	}

	public Character removeCharacter(Character character) {
		getCharacters().remove(character);
		character.setTitle(null);

		return character;
	}

	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && titleId != null)
				? titleId.equals(((Title) other).titleId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return (titleId != null) ? (getClass().hashCode() + titleId.hashCode()) : super.hashCode();
	}

}