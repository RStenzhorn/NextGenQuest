package de.rjst.questsystem.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reward_shop_item")
public class RewardShopItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price", nullable = false)
    private BigInteger price;

    @Column(name = "name")
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "itemStack", nullable = false, columnDefinition = "TEXT")
    private String itemStack;

    @Column(name = "command", length = 512)
    private String command;

}
