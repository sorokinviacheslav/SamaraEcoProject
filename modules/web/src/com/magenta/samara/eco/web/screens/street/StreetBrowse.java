package com.magenta.samara.eco.web.screens.street;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.backgroundwork.BackgroundWorkProgressWindow;
import com.haulmont.cuba.gui.backgroundwork.BackgroundWorkWindow;
import com.haulmont.cuba.gui.backgroundwork.LocalizedTaskWrapper;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;
import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Street;
import com.magenta.samara.eco.service.AddressManipulationService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@UiController("eco_Street.browse")
@UiDescriptor("street-browse.xml")
@LookupComponent("streetsTable")
@LoadDataBeforeShow
public class StreetBrowse extends StandardLookup<Street> {

    @Inject
    private GroupTable<Street> streetsTable;
    @Inject
    private AddressManipulationService addressManipulationService;

    private class CoordinatesDownloadTask extends BackgroundTask<Integer, Void> {
        private Street street;

        public CoordinatesDownloadTask(Street street) {
            super(10, TimeUnit.MINUTES, StreetBrowse.this);
            this.street = street;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            for (int strNum=1;strNum<500;strNum++) {
                if (taskLifeCycle.isCancelled()) {
                    break;
                }
                AddressManipulationService.AddressInfo address = new AddressManipulationService.AddressInfo(street.getName(),street.getStreetType(),strNum,"");
                addressManipulationService.addNewAddress(address);
                taskLifeCycle.publish(strNum);

            }
            return null;
        }
    }

    @Subscribe("downloadCoordinates")
    public void onDownloadCoordinatesClick(Button.ClickEvent event) {
        Street str = streetsTable.getSingleSelected();
        if(str==null) return;
        BackgroundTask<Integer, Void> task = new CoordinatesDownloadTask(str);
        BackgroundWorkProgressWindow.show(task,"Загрузка адресов...","Прогресс загрузки:",498,true,true);
    }

}