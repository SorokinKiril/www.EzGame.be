package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ITEM_ID")
	private Integer itemId;

	@Column(name="ITEM_DESCRIPTION")
	private String itemDescription;

	@Column(name="ITEM_IMAGE_LINK")
	private String itemImageLink;

	@Column(name="ITEM_NAME")
	private String itemName;

	@Column(name="ITEM_PRICE")
	private int itemPrice;

	//bi-directional many-to-one association to CharacterInventory
	@OneToMany(mappedBy="item")
	private List<CharacterInventory> characterInventories;

	//bi-directional many-to-one association to ItemType
	@ManyToOne
	@JoinColumn(name="item_type_ITEM_TYPE_ID")
	private ItemType itemType;

	public Item() {
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemImageLink() {
		return this.itemImageLink;
	}

	public void setItemImageLink(String itemImageLink) {
		this.itemImageLink = itemImageLink;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public List<CharacterInventory> getCharacterInventories() {
		return this.characterInventories;
	}

	public void setCharacterInventories(List<CharacterInventory> characterInventories) {
		this.characterInventories = characterInventories;
	}

	public CharacterInventory addCharacterInventory(CharacterInventory characterInventory) {
		getCharacterInventories().add(characterInventory);
		characterInventory.setItem(this);

		return characterInventory;
	}

	public CharacterInventory removeCharacterInventory(CharacterInventory characterInventory) {
		getCharacterInventories().remove(characterInventory);
		characterInventory.setItem(null);

		return characterInventory;
	}

	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

}