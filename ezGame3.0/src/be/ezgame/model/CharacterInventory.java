package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the character_inventory database table.
 * 
 */
@Entity
@Table(name="character_inventory")
public class CharacterInventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHARACTER_INVENTORY_ID")
	private Integer characterInventoryId;

	@Column(name="ITEM_QUANTITY")
	private int itemQuantity;

	@Column(name="ITEM_USE")
	private boolean itemUse;

	//bi-directional many-to-one association to Character
	@ManyToOne
	private Character character;

	//bi-directional many-to-one association to Item
	@ManyToOne
	private Item item;

	public CharacterInventory() {
	}

	public Integer getCharacterInventoryId() {
		return this.characterInventoryId;
	}

	public void setCharacterInventoryId(Integer characterInventoryId) {
		this.characterInventoryId = characterInventoryId;
	}

	public int getItemQuantity() {
		return this.itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public boolean isItemUse() {
		return itemUse;
	}

	public void setItemUse(boolean itemUse) {
		this.itemUse = itemUse;
	}

	public Character getCharacter() {
		return this.character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}