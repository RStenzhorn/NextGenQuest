package de.rjst.questsystem.setting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
public enum NgqMessageType {

    QUEST_MSG_SUCCESS(
            "messages.quests.success",
            "&4Du hast eine Quest erfolgreich abgeschlossen. Dafür hast du %REWARD% Coni bekommen.",
            "&4You have successfully completed a quest. You have received %REWARD% Coni."
    ),
    INVENTORY_MAIN(
            "messages.inventory.main",
            "&2Quests - Hauptmenü",
            "&2Quests - Main Menu"
    ),

    INVENTORY_MAIN_REWARDS(
            "messages.inventory.main.reward",
            "&2Belohnungen",
            "&2Rewards"
    ),
    INVENTORY_MAIN_INFO(
            "messages.inventory.main.info",
            "&2Quest-Belohnung: %CURRENCY%",
            "&2Quest Reward: %CURRENCY%"
    ),
    INVENTORY_MAIN_QUESTS(
            "messages.inventory.main.quests",
            "&2Deine Quests",
            "&2Your Quests"
    ),
    INVENTORY_QUESTS_SELECT(
            "messages.inventory.quests.select",
            "&2Deine Quests",
            "&2Your Quests"
    ),
    INVENTORY_QUESTS_SELECT_DAILY(
            "messages.inventory.quests.select.daily",
            "&2Tägliche Quests",
            "&2Daily Quests"
    ),
    INVENTORY_QUESTS_SELECT_WEEKLY(
            "messages.inventory.quests.select.weekly",
            "&2Wöchentliche Quests",
            "&2Weekly Quests"
    ),
    INVENTORY_QUESTS_SELECT_MONTHLY("messages.inventory.quests.select.monthly",
            "&2Monatliche Quests",
            "&2Monthly Quests"),
    INVENTORY_REWARD("messages.inventory.reward",
            "&2Belohnungen",
            "&2Rewards"),
    QUEST_NAME(
            "messages.item.quest.name",
            "&2Quest: %NAME%",
            "&2Quest: %NAME%"
    ),
    INVENTORY_ITEM_REWARD(
            "messages.inventory.reward.itemName",
            "&2Belohnung: %NAME%",
            "&2Reward: %NAME%"
    ),

    INVENTORY_DAILY_QUESTS(
            "messages.inventory.quests.daily",
            "&2Tägliche Quests",
            "&2Daily quests"
    ),

    INVENTORY_WEEKLY_QUESTS(
            "messages.inventory.quests.weekly",
            "&2Wöchentliche Quests",
            "&2Weekly quests"
    ),

    INVENTORY_MONTHLY_QUESTS(
            "messages.inventory.quests.monthly",
            "&2Monatliche Quests",
            "&2Monthly quests"
    ),

    REWARD_NOT_ENOUGH(
            "messages.rewards.notEnough",
            "&4Du hast nicht genug %CURRENCY%!",
            "&4Not enough %CURRENCY%!"
    ),
    REWARD_SUCCESS(
            "messages.rewards.success",
            "&2Du hast deine Belohnung erfolgreich erhalten!",
            "&2You have successfully received your reward!"
    ),

    INVENTORY_REWARD_BUY_REQUEST(
            "messages.inventory.reward.buyRequest",
            "&2Kauf-Anfrage",
            "&2Purchase Request"
    ),
    INVENTORY_YES_BUTTON(
            "messages.inventory.accept",
            "&2Akzeptieren",
            "&2Accept"
    ),
    INVENTORY_NO_BUTTON(
            "messages.inventory.decline",
            "&4Ablehnen",
            "&4Decline"
    ),
    INVENTORY_REWARD_DESCRIPTION(
            "messages.inventory.reward.item.description",
            "&2Preis: %PRICE%",
            "&2Price: %PRICE%"
    ),
    INVENTORY_CONDITION_TYPE_BLOCK_BREAK(
            "messages.inventory.condition.blockBreak",
            "&5Baue %VALUE% / %GOAL% Blöcke vom Typ %TYPE% ab",
            "&5Break %VALUE% / %GOAL% blocks of type %TYPE%"
    ),
    INVENTORY_CONDITION_TYPE_BLOCK_PLACE(
            "messages.inventory.condition.blockPlace",
            "&5Platziere %VALUE% / %GOAL% Blöcke vom Typ %TYPE%",
            "&5Place %VALUE% / %GOAL% blocks of type %TYPE%"
    ),
    INVENTORY_CONDITION_TYPE_MOVE(
            "messages.inventory.condition.move",
            "&5Laufe eine Entfernung von %VALUE% / %GOAL% Blöcken.",
            "&5Walk a distance of %VALUE% / %GOAL% blocks."
    ),
    INVENTORY_CONDITION_TYPE_SWIM(
            "messages.inventory.condition.swim",
            "&5Schwimme eine Entfernung von %VALUE% / %GOAL% Blöcken.",
            "&5Swim a distance of %VALUE% / %GOAL% blocks."
    ),
    INVENTORY_CONDITION_TYPE_BREAK_ITEM(
            "messages.inventory.condition.breakItem",
            "&5Zerstöre %VALUE% / %GOAL% %TYPE% durch Abnutzung.",
            "&5Break %VALUE% / %GOAL% %TYPE% by wear and tear."
    ),
    INVENTORY_CONDITION_TYPE_FLY(
            "messages.inventory.condition.fly",
            "&5Lege mit einer Elytra eine Entfernung von %VALUE% / %GOAL% Blöcken zurück.",
            "&5Fly a distance of %VALUE% / %GOAL% blocks with an Elytra."
    ),
    INVENTORY_CONDITION_TYPE_CHAT(
            "messages.inventory.condition.chat",
            "&5Nutze den Chat %VALUE% / %GOAL%-mal.",
            "&5Use the chat %VALUE% / %GOAL% times."
    ),
    INVENTORY_CONDITION_TYPE_FISHING(
            "messages.inventory.condition.fishing",
            "&5Wirf die Angel aus %VALUE% / %GOAL%-mal.",
            "&5Cast your fishing rod %VALUE% / %GOAL% times."
    ),
    INVENTORY_CONDITION_TYPE_CONSUME(
            "messages.inventory.condition.consume",
            "&5Verbrauche %VALUE% / %GOAL% %TYPE%.",
            "&5Consume %VALUE% / %GOAL% %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_CONSUME_POTION(
            "messages.inventory.condition.consumePotion",
            "&5Verbrauche %VALUE% / %GOAL% Tränke vom Typ %TYPE%.",
            "&5Consume %VALUE% / %GOAL% potions of type %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_VEHICLE_LORE(
            "messages.inventory.condition.vehicleLore",
            "&5Lege mit einem Minecart eine Entfernung von %VALUE% / %GOAL% %TYPE% zurück.",
            "&5Travel a distance of %VALUE% / %GOAL% %TYPE% with a minecart."
    ),
    INVENTORY_CONDITION_TYPE_SPY_GLASS(
            "messages.inventory.condition.spyGlass",
            "&5Finde ein Fernrohr %VALUE% / %GOAL% ein %TYPE%.",
            "&5Use a spyglass %VALUE% / %GOAL% to find a %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_CRAFTING(
            "messages.inventory.condition.crafting",
            "&5Stelle %VALUE% / %GOAL% %TYPE% her.",
            "&5Craft %VALUE% / %GOAL% %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_ENCHANTMENT(
            "messages.inventory.condition.enchantment",
            "&5Verzaubere %VALUE% / %GOAL% %TYPE%.",
            "&5Enchant %VALUE% / %GOAL% %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_KILL_ALL_MOBS(
            "messages.inventory.condition.killAllMobs",
            "&5Töte %VALUE% / %GOAL% Mobs.",
            "&5Kill %VALUE% / %GOAL% mobs."
    ),
    INVENTORY_CONDITION_TYPE_KILL_MOB(
            "messages.inventory.condition.killMob",
            "&5Töte %VALUE% / %GOAL% %TYPE%.",
            "&5Kill %VALUE% / %GOAL% %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_KILL_PLAYER(
            "messages.inventory.condition.killPlayer",
            "&5Töte %VALUE% / %GOAL% Spieler.",
            "&5Kill %VALUE% / %GOAL% players."
    ),
    INVENTORY_CONDITION_TYPE_SHEEP_SHEAR(
            "messages.inventory.condition.sheepShear",
            "&5Schere %VALUE% / %GOAL% ein Schaf.",
            "&5Shear %VALUE% / %GOAL% a sheep."
    ),
    INVENTORY_CONDITION_TYPE_COW_MILKING(
            "messages.inventory.condition.cowMilking",
            "&5Melke %VALUE% / %GOAL% eine Kuh.",
            "&5Milk %VALUE% / %GOAL% a cow."
    ),
    INVENTORY_CONDITION_TYPE_SLEEP(
            "messages.inventory.condition.sleep",
            "&5Schlafe %VALUE% / %GOAL%.",
            "&5Sleep %VALUE% / %GOAL% times."
    ),
    INVENTORY_CONDITION_TYPE_PURCHASE_PLAYER_SHOP(
            "messages.inventory.condition.purchasePlayerShop",
            "&5Kaufe %VALUE% / %GOAL% bei einem Spieler-Shop.",
            "&5Purchase %VALUE% / %GOAL% from a player shop."
    ),
    INVENTORY_CONDITION_TYPE_SNOWBALL(
            "messages.inventory.condition.snowball",
            "&5Wirf %VALUE% / %GOAL% einen Schneeball.",
            "&5Throw %VALUE% / %GOAL% snowballs."
    ),
    INVENTORY_CONDITION_TYPE_TRADE_VILLAGER(
            "messages.inventory.condition.tradeVillager",
            "&5Handele %VALUE% / %GOAL% mit einem Dorfbewohner.",
            "&5Trade %VALUE% / %GOAL% with a villager."
    ),
    INVENTORY_CONDITION_TYPE_TRADE_MERCHANT(
            "messages.inventory.condition.tradeMerchant",
            "&5Handele %VALUE% / %GOAL% mit einem reisenden Händler.",
            "&5Trade %VALUE% / %GOAL% with a wandering merchant."
    ),
    INVENTORY_CONDITION_TYPE_HARVEST(
            "messages.inventory.condition.harvest",
            "&5Ernte %VALUE% / %GOAL% Felder.",
            "&5Harvest %VALUE% / %GOAL% fields."
    ),
    INVENTORY_CONDITION_TYPE_TOTEM_OF_UNDYING(
            "messages.inventory.condition.totemOfUndying",
            "&5Nutze ein Totem of Undying %VALUE% / %GOAL%-mal.",
            "&5Use a Totem of Undying %VALUE% / %GOAL% times."
    ),
    INVENTORY_CONDITION_TYPE_BREW(
            "messages.inventory.condition.brew",
            "&5Braue %VALUE% / %GOAL% Tränke mit dem Effekt %TYPE%.",
            "&5Brew %VALUE% / %GOAL% potions with the effect %TYPE%."
    ),
    INVENTORY_CONDITION_TYPE_SUMMON_ENDER_DRAGON(
            "messages.inventory.condition.summonEnderDragon",
            "&5Beschwöre %VALUE% / %GOAL% Enderdrachen.",
            "&5Summon %VALUE% / %GOAL% Ender Dragons."
    ),
    INVENTORY_CONDITION_TYPE_TRIGGER_RAID(
            "messages.inventory.condition.triggerRaid",
            "&5Triggere %VALUE% / %GOAL% Raids.",
            "&5Trigger %VALUE% / %GOAL% raids."
    ),
    INVENTORY_CONDITION_TYPE_DROP_ITEM(
            "messages.inventory.condition.dropItem",
            "&5Droppe %VALUE% / %GOAL% %TYPE% durch das Abbauen von Blöcken oder das Töten von Mobs.",
            "&5Drop %VALUE% / %GOAL% %TYPE% by breaking blocks or killing mobs."
    ),
    INVENTORY_CONDITION_TYPE_FOUND_STRUCT(
            "messages.inventory.condition.foundStruct",
            "&5Finde die Struktur %TYPE% (%VALUE% / %GOAL%).",
            "&5Find the structure %TYPE% (%VALUE% / %GOAL%)."
    ),
    INVENTORY_CONDITION_TYPE_VOTE(
            "messages.inventory.condition.vote",
            "&5Vote für den Server %VALUE% / %GOAL%.",
            "&5Vote for the server %VALUE% / %GOAL%."
    ),
    INVENTORY_SUB_CONDITION_TYPE_BIOME(
            "messages.inventory.subCondition.biome",
            "&6Im Biome: %TYPE%",
            "&6In the biome: %TYPE%"
    ),
    INVENTORY_SUB_CONDITION_TYPE_WORLD(
            "messages.inventory.subCondition.world",
            "&6In der Welt: %TYPE%",
            "&6In the world: %TYPE%"
    ),
    INVENTORY_SUB_CONDITION_TYPE_ITEM_IN_HAND(
            "messages.inventory.subCondition.itemInHand",
            "&6Mit dem Item %TYPE% in der Hand",
            "&6With the item %TYPE% in hand"
    );

    private final String path;

    @Getter
    private final String german;
    @Getter
    private final String english;


    public String getPath(final @NotNull Locale locale) {
        return String.format("%s.%s", path, locale.getLanguage());
    }
}
