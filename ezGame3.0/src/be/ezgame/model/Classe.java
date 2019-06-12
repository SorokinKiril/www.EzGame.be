package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the classe database table.
 * 
 */
@Entity
public class Classe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CLASSE_ID")
	private Integer classeId;

	@Column(name="CLASSE_ARMOR")
	private int classeArmor;

	@Column(name="CLASSE_HIT_POINT")
	private int classeHitPoint;

	@Column(name="CLASSE_IMAGE_LINK")
	private String classeImageLink;

	@Column(name="CLASSE_INTELLIGENCE")
	private int classeIntelligence;

	@Column(name="CLASSE_MAGIC")
	private int classeMagic;

	@Column(name="CLASSE_NAME")
	private String classeName;

	@Column(name="CLASSE_STRENGTH")
	private int classeStrength;

	//bi-directional many-to-one association to Character
	@OneToMany(mappedBy="classe")
	private List<Character> characters;

	public Classe() {
	}

	public Integer getClasseId() {
		return this.classeId;
	}

	public void setClasseId(Integer classeId) {
		this.classeId = classeId;
	}

	public int getClasseArmor() {
		return this.classeArmor;
	}

	public void setClasseArmor(int classeArmor) {
		this.classeArmor = classeArmor;
	}

	public int getClasseHitPoint() {
		return this.classeHitPoint;
	}

	public void setClasseHitPoint(int classeHitPoint) {
		this.classeHitPoint = classeHitPoint;
	}

	public String getClasseImageLink() {
		return this.classeImageLink;
	}

	public void setClasseImageLink(String classeImageLink) {
		this.classeImageLink = classeImageLink;
	}

	public int getClasseIntelligence() {
		return this.classeIntelligence;
	}

	public void setClasseIntelligence(int classeIntelligence) {
		this.classeIntelligence = classeIntelligence;
	}

	public int getClasseMagic() {
		return this.classeMagic;
	}

	public void setClasseMagic(int classeMagic) {
		this.classeMagic = classeMagic;
	}

	public String getClasseName() {
		return this.classeName;
	}

	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}

	public int getClasseStrength() {
		return this.classeStrength;
	}

	public void setClasseStrength(int classeStrength) {
		this.classeStrength = classeStrength;
	}

	public List<Character> getCharacters() {
		return this.characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public Character addCharacter(Character character) {
		getCharacters().add(character);
		character.setClasse(this);

		return character;
	}

	public Character removeCharacter(Character character) {
		getCharacters().remove(character);
		character.setClasse(null);

		return character;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && classeId != null)
				? classeId.equals(((Classe) other).classeId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return (classeId != null) ? (getClass().hashCode() + classeId.hashCode()) : super.hashCode();
	}

}