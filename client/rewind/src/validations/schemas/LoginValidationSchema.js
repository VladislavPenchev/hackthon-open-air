import * as Yup from "yup";

const LoginValidationSchema = Yup.object({
    email: Yup.string().email("You must enter a valid Email").required('Email is required'),
    password: Yup.string().required('Password is required')
});

export { LoginValidationSchema };
