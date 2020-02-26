package com.magenta.samara.eco.web.screens.point;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Point;

@UiController("eco_Point.edit")
@UiDescriptor("point-edit.xml")
@EditedEntityContainer("pointDc")
@LoadDataBeforeShow
public class PointEdit extends StandardEditor<Point> {
}