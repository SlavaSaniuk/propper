package me.saniukvyacheslav.gui.controllers.menu;

import me.saniukvyacheslav.Logger;
import me.saniukvyacheslav.core.logging.PropperLoggingConfiguration;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.menu.PropertiesMenuEvents;
import me.saniukvyacheslav.gui.models.topmenu.PropertiesMenuModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * "Properties" top menu controller.
 */
public class PropertiesMenuController implements Observer, Observable {

    private static final Logger LOGGER = PropperLoggingConfiguration.getLogger(PropertiesMenuController.class); // Logger;
    private final PropertiesMenuModel menuModel; // Menu model;
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // Map of observers;

    /**
     * Construct new instance of this controller.
     * @param aPropertiesMenuModel - menu model.
     */
    public PropertiesMenuController(PropertiesMenuModel aPropertiesMenuModel) {
        // Check parameters:
        LOGGER.debug("Construct new instance of [PropertiesMenuController] class:");
        Objects.requireNonNull(aPropertiesMenuModel, "PropertiesMenuModel [aPropertiesMenuModel] must be not null.");

        // Map:
        LOGGER.debug(String.format("[PropertiesMenuModel] model: [%s];", aPropertiesMenuModel));
        this.menuModel = aPropertiesMenuModel;

        LOGGER.debug("Construct new instance of [PropertiesMenuController] class: SUCCESS;");
    }

    /**
     * Do something on event.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        LOGGER.debug(String.format("Handle [%d] event:", event.getCode()));

        // Choose event:
        switch (event.getCode()) {
            case 550: // REPOSITORY_OPENED event:
                LOGGER.debug(String.format("Handle [%d: %s] event:", RepositoryEvents.REPOSITORY_OPENED.getCode(), RepositoryEvents.REPOSITORY_OPENED.name()));
                this.onRepositoryOpenedEvent();
                break;
            case 552: // REPOSITORY_CLOSED event:
                LOGGER.debug(String.format("Handle [%d: %s] event:", RepositoryEvents.REPOSITORY_CLOSED.getCode(), RepositoryEvents.REPOSITORY_CLOSED.name()));
                this.onRepositoryClosedEvent();
                break;
            default:
                LOGGER.debug(String.format("Handing [%d] event by this [PropertiesMenuController] controller is not supported.", event.getCode()));
        }
    }

    /**
     * Enable whole "Properties" menu.
     */
    private void onRepositoryOpenedEvent() {
        // Enable whole menu:
        LOGGER.debug("Enable whole [Properties] menu:");
        this.menuModel.enableMenu();
    }

    /**
     * Disable whole "Properties" menu.
     */
    private void onRepositoryClosedEvent() {
        // Disable whole menu:
        LOGGER.debug("Disable whole [Properties] menu:");
        this.menuModel.disableMenu();
    }

    public void onPropertyInsertEvent() {
        LOGGER.trace("#onPropertyInsertEvent = [701, PROPERTY_INSERT_EVENT] event:");
        this.notify(PropertiesMenuEvents.PROPERTY_INSERT_EVENT);
    }

    @Override
    public void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents) {
        LOGGER.trace(String.format("Subscribe [%s] observer on [%s] FileMenu events.", anObserver, Arrays.toString(anApplicationEvents)));
        this.subscribers.put(anObserver, anApplicationEvents);
    }

    @Override
    public void unsubscribe(Observer anObserver) {
        LOGGER.tracef("Unsubscribe [%s] observer;", anObserver.getClass().getSimpleName());
        this.subscribers.remove(anObserver);
    }

    @Override
    public void notify(PropperApplicationEvent anApplicationEvent, Object... anArguments) {
        LOGGER.trace(String.format("Notify observers about [%d] event:", anApplicationEvent.getCode()));
        // Iterate through subscribers:
        this.subscribers.forEach((subscriber, actionEvents) -> {

            // Iterate through supported event array of current subscriber:
            for (PropperApplicationEvent event: actionEvents) {
                if (event == anApplicationEvent) {
                    LOGGER.trace(String.format("Notify observer [%s] about [%d] file menu event:", subscriber.getClass().getCanonicalName(), event.getCode()));
                    subscriber.update(anApplicationEvent, anArguments);
                }
            }
        });
    }
}
