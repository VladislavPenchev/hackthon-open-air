package com.mentormate.hackathon.validator;

import com.mentormate.hackathon.validate.NotSamePassword;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link PasswordValidator} helps to validate passwords when user register
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
public class PasswordValidator implements ConstraintValidator<NotSamePassword, Object> {

    private String password;
    private String confirmPassword;

    public void initialize(NotSamePassword constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    /**
     * {@inheritDoc}
     *
     * @return true if password and confirm password are same
     */
    @Override
    public boolean isValid(Object user, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(user).getPropertyValue(password);
        Object fieldMatchValue = new BeanWrapperImpl(user).getPropertyValue(confirmPassword);

        String currentPassword = null;
        String currentConfirmPassword = null;
        if (fieldValue instanceof String) {
            currentPassword = (String) fieldValue;
        }
        if (fieldMatchValue instanceof String) {
            currentConfirmPassword = String.valueOf(fieldMatchValue);
        }

        if (StringUtils.hasText(currentPassword)) {
            return fieldValue.equals(currentConfirmPassword);
        }
        return true;
    }
}

