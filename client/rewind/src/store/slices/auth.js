import { createSlice } from '@reduxjs/toolkit';
import * as api from '../../api/AuthQueries';

const initialState = {
    user: localStorage.getItem('user'),
    isLoading: false,
    error: null,
    isSessionChecked: false,
};

const { reducer: authReducer, actions } = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        authStart: (state) => {
            state.isLoading = true;
        },
        authSuccess: (state, action) => {
            state.isLoading = false;
            state.user = action.payload;
            state.error = null;
        },
        authFailure: (state, action) => {
            state.isLoading = false;
            state.user = null;
            state.error = action.payload;
        },
        markSessionChecked: state => {
            state.isSessionChecked = true;
        },
        registerStart: (state) => {
            state.isLoading = true;
            state.error = null;
        },
        registerSuccess: (state, action) => {
            state.isLoading = false;
            state.error = null;
        },
        registerFailure: (state, action) => {
            state.isLoading = false;
            state.error = action.payload;
        },
        clearErrors: (state) => {
            state.error = null;
        },
        logoutStart: (state) => {
            state.isLoading = true;
            state.error = null;
        },
        logoutSuccess: (state, action) => {
            state.isLoading = false;
            state.user = null;
            state.error = null;
        },
        logoutFailure: (state, action) => {
            state.isLoading = false;
            state.user = null;
            state.error = action.payload;
        },
    },
});

//Actions

export const register = ({ email, password }) => {
    return async (dispatch, getState) => {
        const isLoading = getState().auth.isLoading;
        if (isLoading) {
            return
        }

        try {
            dispatch(actions.registerStart());
            const user = await api.register({ email, password });
            dispatch(actions.registerSuccess(user));
        } catch (err) {
            dispatch(actions.registerFailure(err?.response?.data?.message));
        }
    }

};

export const login = ({ email, password }) => {
    return async (dispatch, getState) => {
        const isLoading = getState().auth.isLoading;
        if (isLoading) {
            return
        }

        try {
            dispatch(actions.authStart());
            await api.login({ email, password });
            const user = await api.getMe();

            dispatch(actions.authSuccess(user));
        } catch (err) {
            dispatch(actions.authFailure(err?.response?.data?.message));
        }
    }

};

export const logout = () => {
    return async (dispatch, getState) => {
        const isLoading = getState().auth.isLoading;
        if (isLoading) {
            return
        }

        try {
            dispatch(actions.logoutStart());
            await api.logout();
            dispatch(actions.logoutSuccess());
        } catch (err) {
            dispatch(actions.logoutFailure(err?.response?.data?.message));
        }
    }

};

export const checkSession = () => {
    return async (dispatch) => {
        try {
            const user = await api.getMe();
            dispatch(actions.authSuccess(user));
        } catch (err) { }
        dispatch(actions.markSessionChecked())
    }
}


export const clearErrors = () => {
    return async (dispatch) => {
        dispatch(actions.clearErrors());
    }
}

export { authReducer };
