package monke.models.entities;

import monke.models.base.GameEntity;

public class Item extends GameEntity {
        private enum ItemType {
            COIN, KEY, POTION, WEAPON, ARMOR
        };

        private ItemType type;

        public Item(int x, int y, ItemType type) {
            super(x, y, 0.0f);
            this.type = type;
        }

        public ItemType getType() {
            return type;
        }
}
