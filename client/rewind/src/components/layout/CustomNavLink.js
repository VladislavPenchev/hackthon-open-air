import React from "react";
import { Route} from "react-router-dom";
import NavLink from "react-bootstrap/NavLink";

const CustomNavLink = ({
                           to,
                           exact,
                           strict,
                           location,
                           activeClassName,
                           className,
                           activeStyle,
                           inactiveClassName,
                           style,
                           isActive: getIsActive,
                           children,
                           ...rest
                       }) => (
    <Route
        path={typeof to === "object" ? to.pathname : to}
        exact={exact}
        strict={strict}
        location={location}
    >
        {({ location, match }) => {
            const isActive = !!(getIsActive ? getIsActive(match, location) : match);
            const moddedClassName = `${className || ""} ${isActive
                ? activeClassName
                : inactiveClassName}`;
            return (
                <NavLink
                    to={to}
                    className={moddedClassName}
                    style={isActive ? {
                            backgroundColor: "transparent",
                            borderTop: "none",
                            borderLeft: "none",
                            borderRight: "none" } :
                        { backgroundColor: "transparent",
                            borderTop: "none",
                            borderLeft: "none",
                            borderRight: "none",
                            borderBottom: "none"
                        }}
                    {...rest}
                >
                    {typeof children === "function" ? children({ isActive }) : children}
                </NavLink>
            );
        }}
    </Route>
);

CustomNavLink.defaultProps = {
    activeClassName: "active",
};

export default CustomNavLink;