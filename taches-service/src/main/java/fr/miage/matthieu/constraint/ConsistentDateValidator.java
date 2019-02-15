package fr.miage.matthieu.constraint;

import fr.miage.matthieu.entity.Tache;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentDateValidator implements ConstraintValidator<ConsistentDate, Tache> {

    @Override
    public void initialize(ConsistentDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Tache tache, ConstraintValidatorContext context) {
        if (tache == null) {
            return true;
        }

        //Check if date_debut < date_echeance
        return tache.getDate_debut().before(tache.getDate_echeance());
    }
}