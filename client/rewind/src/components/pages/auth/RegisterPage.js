import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import styled from 'styled-components';
import { FormGroup, Container, Col, Alert, Spinner } from 'react-bootstrap';
import { useFormik } from 'formik';
import "../../../css/Animation.css";
import classNames from 'classnames';
import { RegisterValidationSchema } from "../../../validations/schemas/RegisterValidationSchema";
import "../../../css/forms.scss";
import { useDispatch, useSelector } from 'react-redux';
import { register } from '../../../store/slices/auth';
import { Link, useHistory } from "react-router-dom";

const Title = styled.h1`
  font-family: 'Lobster', cursive;
  letter-spacing: 0.2rem;
  font-size: 4rem;
  transition: transform 0.2s;

&:hover{
    transform: scale(1.2);
    cursor: pointer;
}
`

const Label = styled.label`
font-family: 'Roboto', sans-serif;
font-weight: 400;
`;

const Form = styled.form`
 position: relative;
    display: -ms-flexbox;
    display: flex;
    padding: 1rem;
    -ms-flex-direction: column;
    flex-direction: column;
    width: 100%;
    pointer-events: auto;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: 1.1rem;
    outline: 0;
    max-width: 500px;
    font-family: 'Roboto', sans-serif;
 
    input::placeholder{
       font-style:italic;
       font-family: 'Roboto', sans-serif;
       opacity: 0.4;
     }
`;

const Header = styled.h2`
font-family: 'Roboto', sans-serif;
font-weight: 300;
font-size: 2rem;
letter-spacing: 0rem;
`;

export const RegisterPage = () => {
    const error = useSelector(state => state.auth.error);
    const isLoading = useSelector(state => state.auth.isLoading);
    const dispatch = useDispatch();
    const submit = React.useRef();
    const history = useHistory();

    const handleSubmit = () => {
        submit.current.click();
    }

    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
            confirmPassword: '',
        },

        onSubmit: (values) => {
            dispatch(register(values));
        },
        validationSchema: RegisterValidationSchema,
    });

    return (
        <Container className="mt-5">
            <Col className="d-flex justify-content-center">
                <Form id="myform" className="myform shadow" onSubmit={formik.handleSubmit}>
                    <div className="logo mb-3">
                        <div className="col-md-12 text-center">
                            <Title>Rewind</Title>
                            <Header>REGISTER</Header>
                        </div>
                    </div>
                    {error &&
                        <Alert variant="danger">{error}</Alert>
                    }

                    <FormGroup className="form-group">
                        {formik.touched.email && formik.errors.email &&
                            <Alert variant="danger">{formik.errors.email}</Alert>}
                        <Label className="form-label">Email</Label>
                        <input type="text"
                            name="email"
                            className={"form-control " + classNames(formik.touched.email
                                && !formik.errors.email
                                && "is-valid",
                                formik.touched.email
                                && formik.errors.email
                                && "is-invalid")}
                            onBlur={formik.handleBlur}
                            onChange={formik.handleChange}
                            id="email"
                            placeholder="Enter Email"
                        />
                    </FormGroup>
                    <FormGroup className="form-group">
                        {formik.touched.password && formik.errors.password &&
                            <Alert variant="danger">{formik.errors.password}</Alert>}
                        <Label className="form-label">Password</Label>
                        <input type="password"
                            name="password"
                            id="password"
                            className={"form-control " + classNames(formik.touched.password
                                && !formik.errors.password
                                && "is-valid",
                                formik.touched.password
                                && formik.errors.password
                                && "is-invalid")}
                            onBlur={formik.handleBlur}
                            onChange={formik.handleChange}
                            placeholder="Enter Password" />
                    </FormGroup>
                    <FormGroup className="form-group">
                        {formik.touched.confirmPassword && formik.errors.confirmPassword &&
                            <Alert variant="danger">{formik.errors.confirmPassword}</Alert>}
                        <Label className="form-label">Confirm password</Label>
                        <input type="password"
                            name="confirmPassword"
                            id="confirmPassword"
                            className={"form-control " + classNames(formik.touched.confirmPassword
                                && !formik.errors.confirmPassword
                                && "is-valid",
                                formik.touched.confirmPassword
                                && formik.errors.confirmPassword
                                && "is-invalid")}
                            onBlur={formik.handleBlur}
                            onChange={formik.handleChange}
                            placeholder="Confirm Password" />
                    </FormGroup>
                    {/*<FormGroup className="form-group">*/}
                        {/*<p className="text-center">By signing up you accept our <a href="#TermsOfUse">Terms Of Use</a></p>*/}
                    {/*</FormGroup>*/}
                    <FormGroup className="form-group">
                        <div className="col-md-12 text-center ">
                            <a href="# " onClick={handleSubmit} className="btn-flip mb-3" data-back="Register" data-front="Register" style={{ textDecoration: 'none', display: isLoading ? "none" : "" }}> </a>
                            <button ref={submit} form="myform" type="submit" style={{ display: 'none' }} onClick={() => {
                                let path = `/login`;
                                history.push(path);
                            }}></button>
                            {isLoading && <Spinner animation="border" />}
                        </div>
                    </FormGroup>
                    <FormGroup className="form-group">
                        <p className="text-center">Already have account? <Link to="/login">Sign in here</Link></p>
                    </FormGroup>
                </Form>
            </Col>
        </Container>
    )
}
