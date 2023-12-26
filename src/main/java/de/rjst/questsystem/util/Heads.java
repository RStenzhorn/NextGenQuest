package de.rjst.questsystem.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.UUID;

@UtilityClass
public class Heads {

    private final String BASE_URL = "https://textures.minecraft.net/texture/";

    public final ItemStack INFO = createHead("c2ff2e4fd414caba59c246b9766b0407c9deed5797f464a59147b88df96fc6");
    public final ItemStack REWARD = createHead("a03bd00421729cd635cd3b48243430ad47cf707018a5916ff59549d5ecd6f879");
    public final ItemStack QUEST = createHead("20eabae76294dab536627db2197ea4c925ac6bf7ea03d95d2f14e172551ecb4b");
    public final ItemStack MONTHLY_QUEST = createHead("b1ed20567068cb0cc072d732f5322d734bf849e688c7fe011a302e1b9473d20c");
    public final ItemStack WEEKLY_QUEST = createHead("acae092f6a23c5c78ece66b1b18ec873bc7ba1de7db5d0e5ec19c32636923c4");
    public final ItemStack DAILY_QUEST = createHead("fa85bd4cc474ad1907785c2bee5d359292829f53fafba1760f0dbe0d8764971");

    public final ItemStack YES = createHead("a79a5c95ee17abfef45c8dc224189964944d560f19a44f19f8a46aef3fee4756");
    public final ItemStack NO = createHead("27548362a24c0fa8453e4d93e68c5969ddbde57bf6666c0319c1ed1e84d89065");
    public final ItemStack NEXT = createHead("682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e");
    public final ItemStack PREVIOUS = createHead("37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645");

    public final ItemStack PAGE_1 = createHead("a39c846f65d5f272a839fd9c2aeb11bdc8e3f8229fbe3583486e78f4c23c8b5b");
    public final ItemStack PAGE_2 = createHead("5415c4d0c7b8141501949f73ce0c78b2b1e990255371a7fc7199960c9b037d51");
    public final ItemStack PAGE_3 = createHead("5f8d3c8cb0983a4f56cc26a71ffcedbd7becc521291c78361ff1e99df4144cbc");
    public final ItemStack PAGE_4 = createHead("6127812166e14186decf17519603b355699499a545397f8931794fad6e9efd92");
    public final ItemStack PAGE_5 = createHead("fe1008592e3ad24d65dfa4ff5a3c800d78a3db134cbd8e9ec3cbac1ea8391b9d");
    public final ItemStack PAGE_6 = createHead("93098f3a994c1cd68e0e862a00688866f2e673481cd3447c85d9801bc0317b5f");
    public final ItemStack PAGE_7 = createHead("8102a8eb0ffdfe5982073dbc41b75bc22e577e3b1ad00bb14868cee384bec7b");
    public final ItemStack PAGE_8 = createHead("83a6d9eca68628518e6b99054bc0a1f7fcc79e2c969f3eb8f9ef034165e03bb5");
    public final ItemStack PAGE_9 = createHead("ff32222def1c7b3bd04513b1a40493407c4287b6ec3943f7333f715773d4cb61");
    public final ItemStack PAGE_10 = createHead("ff32222def1c7b3bd04513b1a40493407c4287b6ec3943f7333f715773d4cb61");
    public final ItemStack PAGE_11 = createHead("c035db8ac89f9c05fda044c621936fc9fd587a66ee2b0b9c0876e6b5988ce83f");
    public final ItemStack PAGE_12 = createHead("12c6e9a1fedb260d7c66be70dd36f48866d11b6d206267c28ecbf4493c3ab9bb");
    public final ItemStack PAGE_13 = createHead("2ed1f2925c1e4b928f4745f69d2282e85d066be28248d70f0b76698d36ce4ad4");
    public final ItemStack PAGE_14 = createHead("cadc2df2d030e4d800ce1d6f734c5474277b69f2cc9e75268347c377bee2479f");
    public final ItemStack PAGE_15 = createHead("87b8145be4ef516faa9e8094a9eecc8fd344e2ba195464fc3966d71cd973ec7c");

    @SneakyThrows
    private @NotNull ItemStack createHead(final String url) {
        final UUID uuid = UUID.randomUUID();

        final PlayerProfile profile = Bukkit.createProfile(uuid);
        final PlayerTextures textures = profile.getTextures();
        textures.setSkin(new URL(BASE_URL + url));
        profile.setTextures(textures);

        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setPlayerProfile(profile);
        head.setItemMeta(skullMeta);
        FrontendUtil.setDisplayName(head, Component.text(""));
        return head;
    }
}
