package com.magenta.samara.eco.web.screens.point;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Point;

@UiController("eco_Point.browse")
@UiDescriptor("point-browse.xml")
@LookupComponent("pointsTable")
@LoadDataBeforeShow
public class PointBrowse extends StandardLookup<Point> {
}