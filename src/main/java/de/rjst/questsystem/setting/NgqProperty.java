package de.rjst.questsystem.setting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NgqProperty {

    CURRENCY_SINGULAR("currency.singular", "Coin"),
    CURRENCY_PLURAL("currency.plural", "Coins"),
    DB_DRIVER_CLASS("spring.datasource.driver-class-name","org.mariadb.jdbc.Driver"),
    DB_URL("spring.datasource.url","jdbc:mariadb://vpn.rjst.de:3306/quests"),
    DB_USER("spring.datasource.username","root"),
    DB_PASSWORD("spring.datasource.password","t1rZuZikbnyM7CXThK6cyiFsbSxCJW"),
    DB_DDL_AUTO("spring.jpa.hibernate.ddl-auto","update"),
    DB_SHOW_SQL("spring.jpa.show-sql","true"),
    DB_HIKARI_AUTO_COMMIT("spring.datasource.hikari.autoCommit","true"),
    DB_HIKARI_CONNECTION_TIMEOUT("spring.datasource.hikari.connectionTimeout","30000"),
    DB_HIKARI_IDLE_TIMEOUT("spring.datasource.hikari.idleTimeout","600000"),
    DB_HIKARI_KEEPALIVE_TIME("spring.datasource.hikari.keepAliveTime","0"),
    DB_HIKARI_MAX_LIFETIME("spring.datasource.hikari.maxLifetime","1800000"),
    DB_HIKARI_MINIMUM_IDLE("spring.datasource.hikari.minimumIdle","10"),
    DB_HIKARI_MAXIMUM_POOL_SIZE("spring.datasource.hikari.maximumPoolSize","10"),
    QUESTS_DAILY_AMOUNT("quests.daily.amount", "5"),
    QUESTS_WEEKLY_AMOUNT("quests.weekly.amount", "10"),
    QUESTS_MONTHLY_AMOUNT("quests.monthly.amount", "10");

    private final String path;
    private final Object defaultValue;


}
