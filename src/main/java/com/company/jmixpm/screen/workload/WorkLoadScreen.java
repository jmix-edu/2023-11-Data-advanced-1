package com.company.jmixpm.screen.workload;

import com.company.jmixpm.screen.workloadinfo.WorkLoadInfoScreen;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("WorkLoadScreen")
@UiDescriptor("work-load-screen.xml")
public class WorkLoadScreen extends Screen {
    @Autowired
    private Table<KeyValueEntity> workloadTable;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("workloadTable.workloadInfo")
    public void onWorkloadTableWorkloadInfo(final Action.ActionPerformedEvent event) {

        KeyValueEntity selected = workloadTable.getSingleSelected();
        if (selected == null) {
            return;
        }

        screenBuilders.screen(this)
                .withScreenClass(WorkLoadInfoScreen.class)
                .build()
                .withItem(selected)
                .show();
        
    }
    
    
}