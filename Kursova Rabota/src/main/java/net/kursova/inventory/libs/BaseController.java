package net.kursova.inventory.libs;

import net.kursova.inventory.App;
import net.kursova.inventory.services.InventoryService;
import net.kursova.inventory.services.InventoryServiceHibernate;

public abstract class BaseController {
    public InventoryService inventoryService = new InventoryServiceHibernate(App.getInstance().getSessionFactory());
}