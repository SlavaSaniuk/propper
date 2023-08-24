package me.saniukvyacheslav.core.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ExceptionDialog {

    @Getter @Setter private String title;
    @Getter @Setter private String headerText;
    @Getter @Setter private String contentText;
    @Getter @Setter private Exception originalException;

}
