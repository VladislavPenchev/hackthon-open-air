import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8000/api/v1',
    withCredentials: false,
});

export const register = async ({ email, password }) => {
    const res = await instance.post('/auth/register', { email, password, confirmPassword: password });
    return res.data;
};

export const login = async ({ email, password }) => {
    const res = await instance.post('/auth/login', { email, password });
    const { token, user } = res.data;
    instance.defaults.headers['Authorization'] = `Bearer ${token}`;
    localStorage.setItem('token', token);
    localStorage.setItem('user', user);
    return res.data;
};

export const logout = async () => {
    const res = await instance.post('/auth/logout');
    localStorage.clear();
    return res.data;
};

export const getTimesheetsForUser = async ({ cursor, limit }) => {
    const res = await instance.get(`/timesheets`, { params: { page: cursor, size: limit } });
    return res.data;
};

export const fetchProjects = async () => {
    const res = await instance.get('/projects');
    return res.data;
};

export const getMe = async () => {
    const res = await instance.get('/auth/me');
    return res.data;
}

export const createTimesheet = async ({ fromDate }) => {
    const res = await instance.post('/timesheets', { fromDate });
    return res.data;
}

export const deleteTimesheet = async ({ id }) => {
    const res = await instance.delete(`/timesheets/${id}`);
    return res.data;
}

export const submitTimesheet = async ({ id }) => {
    const res = await instance.put(`/timesheets/${id}/submit`);
    return res.data;
}

export const saveTimesheet = async ({ id, activities, statusType, total }) => {
    const res = await instance.put(`/timesheets/${id}`, {activities, statusType, total});
    return res.data;
}

export const fetchTimesheetById = async ({ id }) => {
    const res = await instance.get(`/timesheets/${id}`);
    return res.data;
}

export const addActivityToTimesheet = async ({ id }) => {
    const res = await instance.post(`/timesheets/${id}/activities`);
    return res.data;
}

export const deleteActivityOfTimesheet = async ({ timesheetId, activityId }) => {
    const res = await instance.delete(`/timesheets/${timesheetId}/activities/${activityId}`);
    return res.data;
}

const token = localStorage.getItem('token');
if (token) {
    instance.defaults.headers['Authorization'] = `Bearer ${token}`;
}
