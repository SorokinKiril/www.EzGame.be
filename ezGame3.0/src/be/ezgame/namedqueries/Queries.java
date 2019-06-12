package be.ezgame.namedqueries;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity // Ne pas tenir compte du warning
@NamedQueries({
		// Title queries
		@NamedQuery(name = "Title.findAll", query = "SELECT t FROM Title t"),
		@NamedQuery(name = "Title.findTitleByName", query = "SELECT t FROM Title t WHERE t.titleName = :name"),
		@NamedQuery(name = "Title.findTitleById", query = "SELECT t FROM Title t WHERE t.titleId = :titleId"),
		// SecretQuestion queries
		@NamedQuery(name = "SecretQuestion.findAll", query = "SELECT q FROM SecretQuestion q"),
		@NamedQuery(name = "SecretQuestion.findQuestionByName", query = "SELECT q FROM SecretQuestion q WHERE q.question = :name"),
		@NamedQuery(name = "SecretQuestion.findQuestionById", query = "SELECT q FROM SecretQuestion q WHERE q.secretQuestionId = :secretQuestionId"),
		// Race queries
		@NamedQuery(name = "Race.findAll", query = "SELECT r FROM Race r"),
		@NamedQuery(name = "Race.findRaceByName", query = "SELECT r FROM Race r WHERE r.raceName = :raceName"),
		@NamedQuery(name = "Race.findRaceById", query = "SELECT r FROM Race r WHERE r.raceId = :raceId"),
		// Classe queries
		@NamedQuery(name = "Classe.findAll", query = "SELECT c FROM Classe c"),
		@NamedQuery(name = "Classe.findClasseByName", query = "SELECT c FROM Classe c WHERE c.classeName = :classeName"),
		@NamedQuery(name = "Classe.findClasseById", query = "SELECT c FROM Classe c WHERE c.classeId = :classeId"),
		// Achievement queries
		@NamedQuery(name = "Achievement.findAll", query = "SELECT a FROM Achievement a"),
		@NamedQuery(name = "Achievement.findAchievementByName", query = "SELECT a FROM Achievement a WHERE a.achievementName = :achievementName"),
		@NamedQuery(name = "Achievement.findAchievementById", query = "SELECT a FROM Achievement a WHERE a.achievementId = :achievementId"),
		// Type item queries
		@NamedQuery(name = "ItemType.findAll", query = "SELECT i FROM ItemType i"),
		@NamedQuery(name = "ItemType.findItemTypeById", query = "SELECT i FROM ItemType i WHERE i.itemTypeId = :itemTypeId"),
		// Item queries
		@NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
		@NamedQuery(name = "Item.findItemByName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName"),
		@NamedQuery(name = "Item.findItemById", query = "SELECT i FROM Item i WHERE i.itemId = :itemId"),
		// Character queries
		@NamedQuery(name = "Character.findAll", query = "SELECT c FROM Characters c"),
		@NamedQuery(name = "Character.findAllAlive", query = "SELECT c FROM Characters c WHERE c.characterStatus = true"),
		@NamedQuery(name = "Character.findCharacterByName", query = "SELECT c FROM Characters c WHERE c.characterName = :characterName"),
		@NamedQuery(name = "Character.findCharacterAliveByUser", query = "SELECT c FROM Characters c WHERE c.user.userId = :userId AND c.characterStatus = true"),
		@NamedQuery(name = "Character.findCharacterById", query = "SELECT c FROM Characters c WHERE c.characterId = :characterId"),
		@NamedQuery(name = "Character.findLeaderboard", query = "SELECT c FROM Characters c ORDER BY c.characterLevel DESC"),
		// CharacterInventory queries
		@NamedQuery(name = "CharacterInventory.findCharacterInventoryByCharacter", query = "SELECT c FROM CharacterInventory c WHERE c.character = :character ORDER BY c.item.itemType"),
		@NamedQuery(name = "CharacterInventory.findCharacterInventoryByEquipedItem", query = "SELECT c FROM CharacterInventory c WHERE c.itemUse = :itemUse AND c.item.itemType = :itemType"),
		// CharacterAchievement queries
		@NamedQuery(name = "CharacterAchievement.findAll", query = "SELECT c FROM CharacterAchievement c"),
		@NamedQuery(name = "CharacterAchievement.findAchievementByCharacter", query = "SELECT c FROM CharacterAchievement c WHERE c.character = :character"),
		@NamedQuery(name = "CharacterAchievement.findByAchievementAndCharacter", query = "SELECT c FROM CharacterAchievement c WHERE c.character = :character AND c.achievement = :achievement"),
		// User queries
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findUserByLogin", query="SELECT u FROM User u WHERE u.userName = :login AND u.userPassword = :password AND u.userStatus = true"),
		@NamedQuery(name = "User.findUserByName", query="SELECT u FROM User u WHERE u.userName = :userName"),
		@NamedQuery(name = "User.findUserById", query="SELECT u FROM User u WHERE u.userId = :userId"),
		@NamedQuery(name = "User.findUserByMail", query="SELECT u FROM User u WHERE u.userMail = :userMail"),
		// Role queries
		@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
		@NamedQuery(name = "Role.findRoleById", query = "SELECT r FROM Role r WHERE r.roleId = :roleId"),
		
})
public class Queries implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
