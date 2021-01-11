import * as yup from "yup";

const RegisterValidationSchema = yup.object({
    email: yup
        .string()
        .email("You must enter a valid Email")
        .min(5, "Email must be at least 5 characters!")
        .max(30, "Email must not be longer than 30 characters!")
        .required("Email is required!"),
    password: yup
        .string()
        .min(6, "Password must be at least 6 characters!")
        .max(20, "Password must not be longer than 20 characters!")
        .required("Password is required!"),
    confirmPassword: yup
        .string()
        .required("Please confirm your password")
        .when("password", {
            is: password => (!!(password && password.length > 0)),
            then: yup.string().oneOf([yup.ref("password")], "Password doesn't match")
        })
});

export { RegisterValidationSchema };
