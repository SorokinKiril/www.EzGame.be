package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the character database table.
 * 
 */
@Entity(name="Characters")
public class Character implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHARACTER_ID")
	private Integer characterId;

	@Column(name="CHARACTER_EXPERIENCE")
	private int characterExperience;

	@Column(name="CHARACTER_GENDER")
	private boolean characterGender;

	@Column(name="CHARACTER_GOLD")
	private int characterGold=100;

	@Column(name="CHARACTER_HIT_POINT")
	private int characterHitPoint;

	@Column(name="CHARACTER_LEVEL")
	private int characterLevel = 0;

	@Column(name="CHARACTER_NAME")
	private String characterName;

	@Column(name="CHARACTER_STATUS")
	private boolean characterStatus =true;
	
	@Column(name="CHARACTER_IMAGE_LINK")
	private String characterImageLink;

	//bi-directional many-to-one association to Classe
	@ManyToOne
	private Classe classe;

	//bi-directional many-to-one association to Race
	@ManyToOne
	private Race race;

	//bi-directional many-to-one association to Title
	@ManyToOne
	private Title title;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to CharacterAchievement
	@OneToMany(mappedBy="character")
	private List<CharacterAchievement> characterAchievements;

	//bi-directional many-to-one association to CharacterInventory
	@OneToMany(mappedBy="character")
	private List<CharacterInventory> characterInventories;

	public Character() {
	}

	public Integer getCharacterId() {
		return this.characterId;
	}

	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}

	public int getCharacterExperience() {
		return this.characterExperience;
	}

	public void setCharacterExperience(int characterExperience) {
		this.characterExperience = characterExperience;
	}

	public boolean isCharacterGender() {
		return characterGender;
	}

	public void setCharacterGender(boolean characterGender) {
		this.characterGender = characterGender;
	}

	public int getCharacterGold() {
		return this.characterGold;
	}

	public void setCharacterGold(int characterGold) {
		this.characterGold = characterGold;
	}

	public int getCharacterHitPoint() {
		return this.characterHitPoint;
	}

	public void setCharacterHitPoint(int characterHitPoint) {
		this.characterHitPoint = characterHitPoint;
	}

	public int getCharacterLevel() {
		return this.characterLevel;
	}

	public void setCharacterLevel(int characterLevel) {
		this.characterLevel = characterLevel;
	}

	public String getCharacterName() {
		return this.characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public boolean getCharacterStatus() {
		return this.characterStatus;
	}

	public void setCharacterStatus(boolean characterStatus) {
		this.characterStatus = characterStatus;
	}

	public Classe getClasse() {
		return this.classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Race getRace() {
		return this.race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCharacterImageLink() {
		return characterImageLink;
	}

	public void setCharacterImageLink(String characterImageLink) {
		this.characterImageLink = characterImageLink;
	}

	public List<CharacterAchievement> getCharacterAchievements() {
		return this.characterAchievements;
	}

	public void setCharacterAchievements(List<CharacterAchievement> characterAchievements) {
		this.characterAchievements = characterAchievements;
	}

	public CharacterAchievement addCharacterAchievement(CharacterAchievement characterAchievement) {
		getCharacterAchievements().add(characterAchievement);
		characterAchievement.setCharacter(this);

		return characterAchievement;
	}

	public CharacterAchievement removeCharacterAchievement(CharacterAchievement characterAchievement) {
		getCharacterAchievements().remove(characterAchievement);
		characterAchievement.setCharacter(null);

		return characterAchievement;
	}

	public List<CharacterInventory> getCharacterInventories() {
		return this.characterInventories;
	}

	public void setCharacterInventories(List<CharacterInventory> characterInventories) {
		this.characterInventories = characterInventories;
	}

	public CharacterInventory addCharacterInventory(CharacterInventory characterInventory) {
		getCharacterInventories().add(characterInventory);
		characterInventory.setCharacter(this);

		return characterInventory;
	}

	public CharacterInventory removeCharacterInventory(CharacterInventory characterInventory) {
		getCharacterInventories().remove(characterInventory);
		characterInventory.setCharacter(null);

		return characterInventory;
	}

}