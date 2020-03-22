/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

/**
 * Defined service types
 * 
 * @author michaelmartak
 *
 */
public enum ServiceTypeEnum {
    /**
     * A service providing sales, purchases, or trades. Generally requires
     * authentication when performing transactions.
     */
    MARKET(MarketService.class),
    /**
     * A service that provides the user with an inventory. Usually requires
     * authentication, but may not always (e.g., a local database would not require
     * any authentication).
     */
    INVENTORY(InventoryService.class),
    /**
     * A service that provides a stream of news articles, videos, etc. Can be an RSS
     * stream. Authentication is typically not required, unless you are the New York
     * Times.
     */
    NEWS(NewsService.class),
    /**
     * A service that provides lookup-based information (e.g., search, or
     * suggestions). Authentication is forbidden, since performance is critical.
     * Caching and updating is an important aspect of the service.
     */
    LOOKUP(LookupService.class),
    /**
     * A service that lets you build and create. Usually requires authentication.
     */
    BUILDER(BuilderService.class),
    /**
     * A service that provides meaningful or catered advice.
     */
    INTELLIGENCE(IntelligenceService.class);

    private final Class<? extends ServiceType> interfaceType;

    ServiceTypeEnum(Class<? extends ServiceType> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Class<? extends ServiceType> interfaceType() {
        return interfaceType;
    }

}
