import { combineReducers } from 'redux';
import { authReducer } from './slices/auth';
import { projectsReducer } from './slices/projects';
import { timesheetReducer } from './slices/timesheet';
import { timesheetsReducer } from './slices/timesheets';
const rootReducer = combineReducers({
    auth: authReducer,
    timesheet: timesheetReducer,
    timesheets: timesheetsReducer,
    projects: projectsReducer,
});
export { rootReducer };
