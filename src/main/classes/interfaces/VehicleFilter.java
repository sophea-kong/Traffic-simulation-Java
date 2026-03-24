package interfaces;

import parents.Vehicle;

@FunctionalInterface
public interface VehicleFilter {
    boolean filter(Vehicle v);
}
