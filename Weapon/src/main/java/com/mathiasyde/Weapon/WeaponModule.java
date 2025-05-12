package com.mathiasyde.Weapon;

import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.GameEngine.GameEngine;

public class WeaponModule extends GameModule {
    @Override
    public void register() {
        GameEngine.components.register("weapon", Weapon.class);
    }
}
