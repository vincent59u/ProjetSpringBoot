package fr.miage.matthieu.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ConsistentDateValidator.class })
@Documented
public @interface ConsistentDate {

    String message() default "La date_echeance doit être après la date_debut." ;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
