package de.rjst.questsystem.logic.frontend.shop;

import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.database.repository.RewardShopItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service("rewardShopItemsSupplier")
public class RewardShopItemsSupplier implements Supplier<List<RewardShopItemEntity>> {


    private final RewardShopItemEntityRepository rewardShopItemEntityRepository;

    @Override
    public List<RewardShopItemEntity> get() {
        return rewardShopItemEntityRepository.findAll();
    }
}
