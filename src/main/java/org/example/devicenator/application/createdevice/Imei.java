package org.example.devicenator.application.createdevice;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ImeiValidator.class })
public @interface Imei {

  String message() default "Imei is not valid";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
