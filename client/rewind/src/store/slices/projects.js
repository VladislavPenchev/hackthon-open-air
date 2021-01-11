import { createSlice } from "@reduxjs/toolkit";
import { fetchProjects } from "../../api/AuthQueries";

const initialState = {
    projects: [],
    isLoading: false,
    errors: null,
};

const { reducer: projectsReducer, actions } = createSlice({
    name: "projects",
    initialState,
    reducers: {
        fetchProjectsStart: (state) => {
            state.isLoading = true;
        },
        fetchProjectsSuccess: (state, action) => {
            state.isLoading = false;
            state.projects = action.payload;
            state.error = null;
        },
        fetchProjectsFailure: (state, action) => {
            state.isLoading = false;
            state.error = action.payload;
        },
    }
});

export const fetchAllProjects = () => {
    return async (dispatch) => {

        try {
            dispatch(actions.fetchProjectsStart());

            const results = await fetchProjects();


            dispatch(actions.fetchProjectsSuccess(results));
        } catch (error) {
            dispatch(actions.fetchProjectsFailure(error?.response?.data?.message));
        }
    };
}

export { projectsReducer };
