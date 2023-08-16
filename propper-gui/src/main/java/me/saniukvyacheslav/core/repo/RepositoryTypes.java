package me.saniukvyacheslav.core.repo;

import lombok.Getter;

/**
 * Supported repository types.
 */
public enum RepositoryTypes {

    /**
     * File repository. Properties store in properties file.
     */
    FileRepository(1);

    @Getter private final int type;

    /**
     * Construct new repository type constant.
     * @param aType - type code.
     */
    RepositoryTypes(int aType) {
        this.type = aType;
    }
}
