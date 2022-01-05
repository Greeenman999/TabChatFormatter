package de.greenman999.lpt.listeners;

import de.greenman999.lpt.LPF;
import de.greenman999.lpt.tab.Tab;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

public class LuckpermsListener {

    private final LPF lpf;
    private final Tab tab;
    private final EventBus eventBus;

    public LuckpermsListener(LPF lpf, Tab tab) {
        this.lpf = lpf;
        this.tab = tab;

        eventBus = lpf.getAPI().getEventBus();
        eventBus.subscribe(lpf, UserDataRecalculateEvent.class, this::onDataRecalculate);
        eventBus.subscribe(lpf, NodeMutateEvent.class, this::onNodeMutate);
    }

    private void onDataRecalculate(UserDataRecalculateEvent event) {
        lpf.log("data event");
        tab.updateTablist();
    }

    private void onNodeMutate(NodeMutateEvent event) {
        lpf.log("node event");
        tab.updateTablist();
    }
}
