package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the race database table.
 * 
 */
@Entity
public class Race implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RACE_ID")
	private Integer raceId;

	@Column(name="RACE_NAME")
	private String raceName;

	//bi-directional many-to-one association to Character
	@OneToMany(mappedBy="race")
	private List<Character> characters;

	public Race() {
	}

	public Integer getRaceId() {
		return this.raceId;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	public String getRaceName() {
		return this.raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public List<Character> getCharacters() {
		return this.characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public Character addCharacter(Character character) {
		getCharacters().add(character);
		character.setRace(this);

		return character;
	}

	public Character removeCharacter(Character character) {
		getCharacters().remove(character);
		character.setRace(null);

		return character;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && raceId != null)
				? raceId.equals(((Race) other).raceId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return (raceId != null) ? (getClass().hashCode() + raceId.hashCode()) : super.hashCode();
	}

}