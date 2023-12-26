package de.rjst.questsystem.util;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class ItemStackParser {

    public String convertBase64(final ItemStack itemStack) {
        try {
            final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(itemStack);
            return Base64Coder.encodeLines(arrayOutputStream.toByteArray());
        } catch (final Exception exception) {
            throw new RuntimeException("Error turning ItemStack into base64", exception);
        }
    }


    public ItemStack convertItemStack(final String base64) {
        try {
            final ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(arrayInputStream);
            return (ItemStack) objectInputStream.readObject();
        } catch (final Exception exception) {
            throw new RuntimeException("Error turning base64 into ItemStack", exception);
        }
    }

}
