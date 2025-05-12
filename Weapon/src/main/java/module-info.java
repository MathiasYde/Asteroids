module Weapon {
    exports com.mathiasyde.Weapon;
    requires GameEngine;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Weapon.WeaponModule;
}