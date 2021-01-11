import { createSlice } from '@reduxjs/toolkit';
import * as api from '../../api/AuthQueries';

const initialState = {
    timesheets: [],
    error: null,
    isCreating: false,
    isDeleting: false,
    limit: 5,
};

const { reducer: timesheetsReducer, actions } = createSlice({
    name: 'timesheets',
    initialState,
    reducers: {
        fetchTimesheetsStart: (state) => {
            state.isLoading = true;
        },
        fetchTimesheetsSuccess: (state, action) => {
            state.isLoading = false;
            state.timesheets = action.payload.timesheets;
            state.error = null;
        },
        fetchTimesheetsFailure: (state, action) => {
            state.isLoading = false;
            state.timesheets = [];
            state.error = action.payload;
        },
        deleteTimesheetStart: (state) => {
            state.isDeleting = true;
        },
        deleteTimesheetSuccess: (state, action) => {
            state.isDeleting = false;
            state.timesheets = state.timesheets.content.filter((timesheet) => timesheet.id !== action.payload.id);
        },
        deleteTimesheetFailure: (state, action) => {
            state.isDeleting = false;
            state.error = action.payload;
        },
        reset: () => {
            return initialState;
        },
    },
});

//Actions
export const fetchUserTimesheets = ({ cursor }) => {
    return async (dispatch, getState) => {
        const { limit } = getState().timesheets;
        try {
            dispatch(actions.fetchTimesheetsStart());
            const timesheets = await api.getTimesheetsForUser({ cursor, limit });
            dispatch(actions.fetchTimesheetsSuccess({ timesheets, cursor }));
        } catch (err) {
            dispatch(actions.fetchTimesheetsFailure(err?.response?.data?.message));
        }
    }
};

export const deleteTimesheet = ({ id }) => {
    return async (dispatch) => {
        dispatch(actions.deleteTimesheetStart());
        try {
            await api.deleteTimesheet({ id });
            dispatch(actions.deleteTimesheetSuccess({ id }));
            dispatch(fetchUserTimesheets({ cursor: 0 }));
        } catch (err) {
            dispatch(actions.deleteTimesheetFailure());
        }
    }
};

export const resetTimesheets = actions.reset;

export { timesheetsReducer };

