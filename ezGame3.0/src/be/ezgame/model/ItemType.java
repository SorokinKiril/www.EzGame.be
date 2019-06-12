package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the item_type database table.
 * 
 */
@Entity
@Table(name="item_type")
public class ItemType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ITEM_TYPE_ID")
	private Integer itemTypeId;

	@Column(name="ITEM_TYPE_NAME")
	private String itemTypeName;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="itemType")
	private List<Item> items;

	public ItemType() {
	}

	public int getItemTypeId() {
		return this.itemTypeId;
	}

	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemTypeName() {
		return this.itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setItemType(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setItemType(null);

		return item;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && itemTypeId != null)
				? itemTypeId.equals(((ItemType) other).itemTypeId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return (itemTypeId != null) ? (getClass().hashCode() + itemTypeId.hashCode()) : super.hashCode();
	}

}