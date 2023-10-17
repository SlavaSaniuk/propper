package me.saniukvyacheslav.gui.events.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

@AllArgsConstructor
public enum PropertiesMenuEvents implements PropperApplicationEvent {

    PROPERTY_INSERT_EVENT(701);

    @Getter private int code;

 }
