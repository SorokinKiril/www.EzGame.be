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

import java.util.List;
import java.util.stream.Collectors;

import be.ezgame.emf.EMF;

import be.ezgame.model.Item;
import be.ezgame.service.CharacterInventoryService;
import be.ezgame.service.CharacterService;
import be.ezgame.service.ItemService;
import be.ezgame.model.Character;
import be.ezgame.model.CharacterInventory;

@Named
@ViewScoped
public class ItemBean implements Serializable {

	private static final long serialVersionUID = 7634032501090805690L;

	private EntityManager em;
	private List<Item> listItem;
	private Item itemEdit;
	private int quantity;
	private int maxQuantityBuyable;
	private int totalPrice;
	private Character charSession;
	private List<CharacterInventory> characterInventories;
	private List<CharacterInventory> characterNotEmptyInventories;
	private CharacterInventory inventory;
	private CharacterInventory equipedItemEdit;
	private int totalCharGoldAfterSell;
	private Integer userId;

	public ItemBean() {
	}

	@PostConstruct
	public void init() {
		em = EMF.getEM();
		itemEdit = new Item();
		equipedItemEdit = new CharacterInventory();
		Subject subject = SecurityUtils.getSubject();
		userId = (Integer) subject.getPrincipal();
		ItemService iService = new ItemService(em);
		CharacterService cservice = new CharacterService(em);
		CharacterInventoryService ciService = new CharacterInventoryService(em);
		if (subject.getPrincipal() != null) {
			charSession = cservice.findCharacterAliveByUser(userId);
		}
		listItem = iService.findAll();
		characterInventories = ciService.findCharacterInventoryByCharacter(charSession);
		characterNotEmptyInventories = characterInventories.stream().filter(i -> i.getItemQuantity() != 0)
				.collect(Collectors.toList());
		em.close();
	}

	// Méthode de calcul de la quantité maximale d'objets qu'un personnage peut
	// acheter.
	public void calculateMaxQuantityBuyable() {
		maxQuantityBuyable = charSession.getCharacterGold() / itemEdit.getItemPrice();
	}

	// Méthode de calcul du prix total du panier d'un personnage.
	public void calculatetotalPrice() {
		totalPrice = quantity * itemEdit.getItemPrice();
	}

	// Méthode de calcul de l'or d'un personnage aprés la vente d'objets.
	public void calculateGoldCharacter() {
		totalCharGoldAfterSell = equipedItemEdit.getCharacter().getCharacterGold()
				+ (quantity * equipedItemEdit.getItem().getItemPrice());
	}

	// Méthode d'ajout et de modification d'un objet.
	public void submitItem() {

		em = EMF.getEM();
		ItemService iService = new ItemService(em);

		try {
			if (itemEdit.getItemId() != null) {

				em.getTransaction().begin();
				iService.update(itemEdit);
				em.getTransaction().commit();

			} else {

				em.getTransaction().begin();
				iService.create(itemEdit);
				em.getTransaction().commit();

			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La sauvegarde de vos modifications a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La sauvegarde de vos modifications a échoué.", ""));

		}
		itemEdit = new Item();
		listItem = iService.findAll();
		em.close();
	}

	// Méthode d'achat d'objet.
	public void buyItem() {

		em = EMF.getEM();
		CharacterInventoryService ciService = new CharacterInventoryService(em);
		CharacterService cService = new CharacterService(em);

		List<CharacterInventory> existingInventory = characterInventories.stream()
				.filter(i -> i.getItem().getItemId().equals(itemEdit.getItemId())).collect(Collectors.toList());
		charSession.setCharacterGold(charSession.getCharacterGold() - (quantity * itemEdit.getItemPrice()));

		inventory = new CharacterInventory();
		inventory.setCharacter(charSession);
		inventory.setItem(itemEdit);
		inventory.setItemQuantity(quantity);
		try {
			if (quantity > 0) {

				em.getTransaction().begin();
				cService.update(charSession);
				em.getTransaction().commit();

				if (!existingInventory.isEmpty()) {

					existingInventory.get(0).setItemQuantity(existingInventory.get(0).getItemQuantity() + quantity);
					em.getTransaction().begin();
					ciService.update(existingInventory.get(0));
					em.getTransaction().commit();

				} else {

					em.getTransaction().begin();
					ciService.create(inventory);
					em.getTransaction().commit();
				}

			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre achat a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre achat n'a pas été effectué.", ""));

		}
		itemEdit = new Item();
		inventory = new CharacterInventory();
		existingInventory.clear();
		characterInventories = ciService.findCharacterInventoryByCharacter(charSession);
		quantity = 0;
		totalPrice = 0;

		CharacterService cservice = new CharacterService(em);
		charSession = cservice.findCharacterAliveByUser(userId);

		em.close();
	}

	// Méthode de mise à jour d'une entrée de l'inventaire d'un personnage.
	public void updateCharacterInventory() {

		em = EMF.getEM();
		equipedItemEdit.setItemQuantity(equipedItemEdit.getItemQuantity() - quantity);
		charSession.setCharacterGold(totalCharGoldAfterSell);

		CharacterInventoryService iService = new CharacterInventoryService(em);
		CharacterInventory oldEquipedItem = iService.findCharacterInventoryByEquipedItem(equipedItemEdit);

		try {
			if (oldEquipedItem != null) {

				oldEquipedItem.setItemUse(false);
				em.getTransaction().begin();
				iService.update(oldEquipedItem);
				em.getTransaction().commit();
			}
			if (equipedItemEdit.getItemQuantity() == 0) {
				equipedItemEdit.setItemUse(false);
			}
			em.getTransaction().begin();
			iService.update(equipedItemEdit);
			em.getTransaction().commit();

			CharacterService cService = new CharacterService(em);

			em.getTransaction().begin();
			cService.update(charSession);
			em.getTransaction().commit();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La modification a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La modification n'a pas été effectué.", ""));

		}

		characterInventories = iService.findCharacterInventoryByCharacter(charSession);
		characterNotEmptyInventories = characterInventories.stream().filter(i -> i.getItemQuantity() != 0)
				.collect(Collectors.toList());
		equipedItemEdit = new CharacterInventory();
		quantity = 0;
		em.close();
	}

	// Méthode de fermeture de la boite de dialogue d'édition.
	public void submitCancel() {
		itemEdit = new Item();
		quantity = 0;
	}
	
	//Getters & Setters

	public List<Item> getListItem() {
		return listItem;
	}

	public void setListItem(List<Item> listItem) {
		this.listItem = listItem;
	}

	public Item getItemEdit() {
		return itemEdit;
	}

	public void setItemEdit(Item itemEdit) {
		this.itemEdit = itemEdit;
	}

	public Character getCharSession() {
		return charSession;
	}

	public void setCharSession(Character charSession) {
		this.charSession = charSession;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getMaxQuantityBuyable() {
		return maxQuantityBuyable;
	}

	public List<CharacterInventory> getCharacterInventories() {
		return characterInventories;
	}

	public void setCharacterInventories(List<CharacterInventory> characterInventories) {
		this.characterInventories = characterInventories;
	}

	public void setMaxQuantityBuyable(int maxQuantityBuyable) {
		this.maxQuantityBuyable = maxQuantityBuyable;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public CharacterInventory getEquipedItemEdit() {
		return equipedItemEdit;
	}

	public void setEquipedItemEdit(CharacterInventory equipedItemEdit) {
		this.equipedItemEdit = equipedItemEdit;
	}

	public int getTotalCharGoldAfterSell() {
		return totalCharGoldAfterSell;
	}

	public void setTotalCharGoldAfterSell(int totalCharGoldAfterSell) {
		this.totalCharGoldAfterSell = totalCharGoldAfterSell;
	}

	public List<CharacterInventory> getCharacterNotEmptyInventories() {
		return characterNotEmptyInventories;
	}

	public void setCharacterNotEmptyInventories(List<CharacterInventory> characterNotEmptyInventories) {
		this.characterNotEmptyInventories = characterNotEmptyInventories;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}