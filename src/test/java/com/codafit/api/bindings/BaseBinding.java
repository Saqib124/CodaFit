package com.codafit.api.bindings;

import com.codafit.api.steps.HealthCheckSteps;
import net.thucydides.core.annotations.Steps;

abstract class BaseBinding {

    @Steps
    HealthCheckSteps healthCheckSteps;
}
