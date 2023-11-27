package com.norbert.frontend.service;

import com.norbert.frontend.Application;
import javafx.scene.image.Image;

import java.util.Objects;

public class IconUtil {
    public final static Image MAIN_ICON = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icon.png")));
}
