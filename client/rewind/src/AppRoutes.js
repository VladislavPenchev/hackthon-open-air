import React from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import LoginPage from "./components/pages/auth/LoginPage";
import { RegisterPage } from "./components/pages/auth/RegisterPage";
import { CreateTimesheet } from "./components/pages/timesheets/CreateTimesheet";
import { ViewTimesheet } from "./components/pages/timesheets/ViewTimesheet";
import Background from "./components/Background";
import { useSelector } from "react-redux";
import { TimesheetsPage } from "./components/pages/timesheets/TimesheetsPage";
import { NavBar } from "./components/layout/NavBar";

export const AppRoutes = () => {
    const user = useSelector(state => state.auth.user);

    if (!user) {
        return (
            <>
                <Background />
                <Switch>
                    <Route path="/login" exact component={LoginPage} />
                    <Route path="/register" exact component={RegisterPage} />
                    <Redirect to="/login" />
                </Switch>
            </>
        )
    } else {
        return (
            <>
                <Background />
                <NavBar />
                <Switch>
                    <Route path="/timesheet/home" exact component={TimesheetsPage} />
                    <Route path="/timesheet/create" exact component={CreateTimesheet} />
                    <Route path="/timesheet/view/:id" exact component={ViewTimesheet} />
                    <Route path="/timesheet/edit/:id" exact component={ViewTimesheet} />
                    <Redirect to="/timesheet/home" />
                </Switch>
            </>
        )
    }
}