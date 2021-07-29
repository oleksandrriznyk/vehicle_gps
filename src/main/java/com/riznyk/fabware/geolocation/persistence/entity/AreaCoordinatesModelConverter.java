package com.riznyk.fabware.geolocation.persistence.entity;

import com.riznyk.fabware.geolocation.web.controller.model.AreaCoordinatesModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.awt.geom.Rectangle2D;

@Component
public class AreaCoordinatesModelConverter implements Converter<AreaCoordinatesModel, Rectangle2D.Double> {

  @Override
  public Rectangle2D.Double convert(final AreaCoordinatesModel areaCoordinatesModel) {
    return new Rectangle2D.Double(areaCoordinatesModel.getUpperLeftX(),
                                  areaCoordinatesModel.getUpperLeftY(),
                                  areaCoordinatesModel.getWidth(),
                                  areaCoordinatesModel.getHeight());
  }

}
