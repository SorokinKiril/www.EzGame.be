package be.ezgame.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;

import java.util.ArrayList;
import java.util.List;

import be.ezgame.emf.EMF;
import be.ezgame.model.Achievement;
import be.ezgame.model.Character;
import be.ezgame.model.CharacterAchievement;
import be.ezgame.model.Title;
import be.ezgame.model.User;
import be.ezgame.service.AchievementService;
import be.ezgame.service.CharacterAchievementService;
import be.ezgame.service.CharacterService;

@Named
@ViewScoped
public class CharacterBean implements Serializable {

	private static final long serialVersionUID = 8310888927201817145L;
	private EntityManager em;
	private List<Character> listCharacter;
	private List<Character> selectedCharacter;
	private Character characterNew;
	private Character characterSession;
	private List<CharacterAchievement> characterSessionAchievements;
	private List<Character> leaderboard;
	private List<Achievement> characterAchievement;
	private List<CharacterAchievement> characterAchievements;
	private List<CharacterAchievement> oldCharacterAchievements;
	private List<Achievement> undoneCharacterAchievements;
	private List<Title> titleUnlocked;
	private List<Integer> maxHitPointOfSelectedCharacter;
	private int characterSessionMagic;
	private int characterSessionIntelligence;
	private int characterSessionArmor;
	private int characterSessionStrength;
	private int characterSessionMaxHitPoint;
	private CharacterAchievement charAchiev;
	private User userSession;

	public CharacterBean() {
	}

	@PostConstruct
	public void init() {

		characterNew = new Character();
		charAchiev = new CharacterAchievement();
		characterSession = new Character();
		characterAchievements = new ArrayList<CharacterAchievement>();
		characterAchievement = new ArrayList<Achievement>();
		titleUnlocked = new ArrayList<Title>();
		characterAchievement.add(null);
		characterAchievement.add(null);
		characterAchievement.add(null);
		maxHitPointOfSelectedCharacter = new ArrayList<Integer>();
		maxHitPointOfSelectedCharacter.add(null);
		maxHitPointOfSelectedCharacter.add(null);
		maxHitPointOfSelectedCharacter.add(null);

		em = EMF.getEM();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getPrincipal();
		CharacterService cService = new CharacterService(em);
		if (subject.getPrincipal() != null) {
			
			characterSession = cService.findCharacterAliveByUser(userId);
			if (characterSession != null) {
				AchievementService achievService = new AchievementService(em);
				CharacterAchievementService aService = new CharacterAchievementService(em);
				characterSessionAchievements = aService.findAchievementByCharacter(characterSession);
				undoneCharacterAchievements = achievService.findAll();

				calculateCharacterSessionStats();
				calculateCharacterMaxHitPoint();

				for (int i = 0; i < characterSessionAchievements.size(); i++) {
					undoneCharacterAchievements.remove(characterSessionAchievements.get(i).getAchievement());					
					if (!titleUnlocked.contains(characterSessionAchievements.get(i).getAchievement().getTitle())) {
						titleUnlocked.add(characterSessionAchievements.get(i).getAchievement().getTitle());
					}
				}
			}
		}
		listCharacter = cService.findAllAlive();
		leaderboard = cService.findLeaderboard();
		em.close();
	}

	// Méthode de calcul des statistiques d'un personnage.
	public void calculateCharacterSessionStats() {
		characterSessionMagic = (characterSession.getCharacterLevel() * 2)
				+ characterSession.getClasse().getClasseMagic();
		characterSessionIntelligence = (characterSession.getCharacterLevel() * 2)
				+ characterSession.getClasse().getClasseIntelligence();
		characterSessionArmor = (characterSession.getCharacterLevel() * 2)
				+ characterSession.getClasse().getClasseArmor();
		characterSessionStrength = (characterSession.getCharacterLevel() * 2)
				+ characterSession.getClasse().getClasseStrength();
	}

	// Méthode de calcul de la quantité maximale de points de vie d'un personnage.
	public void calculateCharacterMaxHitPoint() {
		characterSessionMaxHitPoint = (characterSession.getCharacterLevel() * 2)
				+ characterSession.getClasse().getClasseHitPoint();
	}

	// Méthode de création d'un nouveau personnage pendant l'inscription.
	public void submitNewCharacter(User userCreated) {
		em = EMF.getEM();
		CharacterService cService = new CharacterService(em);
		characterNew.setCharacterHitPoint(characterNew.getClasse().getClasseHitPoint());
		try {
			System.out.println("UserCreated " + userCreated);
			characterNew.setUser(userCreated);

			em.getTransaction().begin();
			cService.create(characterNew);
			em.getTransaction().commit();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La création de votre personnage a reussi.", ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La création de votre personnage a échoué.", ""));
		}
		characterNew = new Character();
		em.close();
	}

	// Méthode de création d'un nouveau personnage aprés inscription.
	public String createFreshCharacter() {
		em = EMF.getEM();
		CharacterService cService = new CharacterService(em);

		characterNew.setCharacterHitPoint(characterNew.getClasse().getClasseHitPoint());
		characterNew.setUser(userSession);
		try {
			if (characterSession != null) {
				characterSession.setCharacterHitPoint(0);
				characterSession.setCharacterStatus(false);
				em.getTransaction().begin();
				cService.update(characterSession);
				em.getTransaction().commit();
			}

			em.getTransaction().begin();
			cService.create(characterNew);
			em.getTransaction().commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La création de votre nouveau parsonnage a reussi.", ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La création de votre nouveau parsonnage a échoué.", ""));
		}

		characterNew = new Character();
		em.close();
		return "/character_user.xhtml?faces-redirect=true";
	}

	// Vérifie si un/des personnage(s) ont été selectionés.
	public void checkRowSelect() {

		try {
			if (selectedCharacter.size() < 1 || selectedCharacter.size() > 3) {
				selectedCharacter.clear();
				throw new Exception();
			} else {
				for (int i = 0; selectedCharacter.size() > i; i++) {
					// charAchiev.setCharacter(selectedCharacter.get(i));
					characterSessionMaxHitPoint = (selectedCharacter.get(i).getCharacterLevel() * 2)
							+ selectedCharacter.get(i).getClasse().getClasseHitPoint();
					maxHitPointOfSelectedCharacter.set(i, characterSessionMaxHitPoint);

					// characterAchievements.add(charAchiev);
				}
				RequestContext.getCurrentInstance().addCallbackParam("showDialog", true);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().addCallbackParam("showDialog", false);
		}
	}

	// Méthode de mise à jour d'un/des personnages et leur succés.
	public void submitCharacterUpdate() {
		em = EMF.getEM();
		CharacterService cService = new CharacterService(em);
		CharacterAchievementService aService = new CharacterAchievementService(em);
		try {

			for (int i = 0; selectedCharacter.size() > i; i++) {

				// Calcul du niveau du personnage sélectionné.
				int level = (int) (0.04 * Math.sqrt(selectedCharacter.get(i).getCharacterExperience()));
				selectedCharacter.get(i).setCharacterLevel(level);

				// Calcul du status (mort ou vif) en fonction des points de vie.
				if (selectedCharacter.get(i).getCharacterHitPoint() == 0) {
					selectedCharacter.get(i).setCharacterStatus(false);
				}

				// Vérification de l'éventuel accomplissement d'un succès par un personnage.
				if (characterAchievement.get(i) != null) {
					CharacterAchievement charAchievSelectedChar = new CharacterAchievement();
					charAchievSelectedChar.setAchievement(characterAchievement.get(i));
					charAchievSelectedChar.setCharacter(selectedCharacter.get(i));
					charAchievSelectedChar.setAchievementStatus(true);
					characterAchievements.add(charAchievSelectedChar);

				} else {
					characterAchievements.add(null);
				}

				// Persistance du personnage.
				em.getTransaction().begin();
				cService.update(selectedCharacter.get(i));
				em.getTransaction().commit();

				// Vérification de l'existence éventuelle d'un characterachievement déjà
				// existant en base de données.
				if (characterAchievements.get(i) != null) {
					oldCharacterAchievements = aService.findByAchievementAndCharacter(
							characterAchievements.get(i).getAchievement(), characterAchievements.get(i).getCharacter());

					// Persistance du characterAchievements.
					if (oldCharacterAchievements.isEmpty()) {
						em.getTransaction().begin();
						aService.create(characterAchievements.get(i));
						em.getTransaction().commit();
					}
				}
			}

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La mise à jour des personnages a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La mise à jour des personnages a échoué.", ""));
		}
		characterAchievements.clear();
		oldCharacterAchievements = null;
		selectedCharacter.clear();
		characterAchievement.clear();
		characterAchievement.add(null);
		characterAchievement.add(null);
		characterAchievement.add(null);
		characterNew = new Character();
		listCharacter = cService.findAllAlive();
		em.close();
	}

	// Méthode de mise à jour du titre d'un personnage.
	public void updateCharacterTitle() {
		em = EMF.getEM();

		CharacterService cService = new CharacterService(em);

		try {
			em.getTransaction().begin();
			cService.update(characterSession);
			em.getTransaction().commit();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Le titre de votre personnage a été mis à jour.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Le titre de votre personnage n'a pas été mis à jour.", ""));
		}
	}

	// Méthode de fermeture de la boite de dialogue d'édition.
	public void submitCancel() {
		characterNew = new Character();
	}

	// Getters & Setters

	public List<Character> getListCharacter() {
		return listCharacter;
	}

	public void setListCharacter(List<Character> listCharacter) {
		this.listCharacter = listCharacter;
	}

	public Character getCharacterNew() {
		return characterNew;
	}

	public void setCharacterNew(Character characterNew) {
		this.characterNew = characterNew;
	}

	public List<Character> getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(List<Character> selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}

	public List<Character> getLeaderboard() {
		return leaderboard;
	}

	public void setLeaderboard(List<Character> leaderboard) {
		this.leaderboard = leaderboard;
	}

	public List<Achievement> getCharacterAchievement() {
		return characterAchievement;
	}

	public void setCharacterAchievement(List<Achievement> characterAchievement) {
		this.characterAchievement = characterAchievement;
	}

	public List<CharacterAchievement> getCharacterAchievements() {
		return characterAchievements;
	}

	public void setCharacterAchievements(List<CharacterAchievement> characterAchievements) {
		this.characterAchievements = characterAchievements;
	}

	public CharacterAchievement getCharAchiev() {
		return charAchiev;
	}

	public void setCharAchiev(CharacterAchievement charAchiev) {
		this.charAchiev = charAchiev;
	}

	public List<CharacterAchievement> getOldCharacterAchievements() {
		return oldCharacterAchievements;
	}

	public void setOldCharacterAchievements(List<CharacterAchievement> oldCharacterAchievements) {
		this.oldCharacterAchievements = oldCharacterAchievements;
	}

	public Character getCharacterSession() {
		return characterSession;
	}

	public void setCharacterSession(Character characterSession) {
		this.characterSession = characterSession;
	}

	public List<CharacterAchievement> getCharacterSessionAchievements() {
		return characterSessionAchievements;
	}

	public void setCharacterSessionAchievements(List<CharacterAchievement> characterSessionAchievements) {
		this.characterSessionAchievements = characterSessionAchievements;
	}

	public List<Title> getTitleUnlocked() {
		return titleUnlocked;
	}

	public void setTitleUnlocked(List<Title> titleUnlocked) {
		this.titleUnlocked = titleUnlocked;
	}

	public int getCharacterSessionMagic() {
		return characterSessionMagic;
	}

	public void setCharacterSessionMagic(int characterSessionMagic) {
		this.characterSessionMagic = characterSessionMagic;
	}

	public int getCharacterSessionIntelligence() {
		return characterSessionIntelligence;
	}

	public void setCharacterSessionIntelligence(int characterSessionIntelligence) {
		this.characterSessionIntelligence = characterSessionIntelligence;
	}

	public int getCharacterSessionArmor() {
		return characterSessionArmor;
	}

	public void setCharacterSessionArmor(int characterSessionArmor) {
		this.characterSessionArmor = characterSessionArmor;
	}

	public int getCharacterSessionStrength() {
		return characterSessionStrength;
	}

	public void setCharacterSessionStrength(int characterSessionStrength) {
		this.characterSessionStrength = characterSessionStrength;
	}

	public int getCharacterSessionMaxHitPoint() {
		return characterSessionMaxHitPoint;
	}

	public void setCharacterSessionMaxHitPoint(int characterSessionMaxHitPoint) {
		this.characterSessionMaxHitPoint = characterSessionMaxHitPoint;
	}

	public List<Integer> getMaxHitPointOfSelectedCharacter() {
		return maxHitPointOfSelectedCharacter;
	}

	public void setMaxHitPointOfSelectedCharacter(List<Integer> maxHitPointOfSelectedCharacter) {
		this.maxHitPointOfSelectedCharacter = maxHitPointOfSelectedCharacter;
	}

	public List<Achievement> getUndoneCharacterAchievements() {
		return undoneCharacterAchievements;
	}

	public void setUndoneCharacterAchievements(List<Achievement> undoneCharacterAchievements) {
		this.undoneCharacterAchievements = undoneCharacterAchievements;
	}

}