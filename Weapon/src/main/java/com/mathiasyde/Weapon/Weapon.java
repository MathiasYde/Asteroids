package com.mathiasyde.Weapon;

import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Components.Spawner;

public class Weapon extends Component {
    private Transform transform;
    private Spawner spawner;

    @Override
    public void awake() {
        transform = require(Transform.class);
        spawner = entity.provider("bullet");
    }    
    
    public void shoot() {
        Vector2f forward = transform.forward();
        Vector2f position = transform.position();

        Entity bullet = spawner.spawn();
        bullet.get(Transform.class).inherit(transform);
        
    }
}
