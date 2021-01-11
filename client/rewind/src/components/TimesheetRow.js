import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css'
import Tippy from '@tippyjs/react';
import 'tippy.js/dist/tippy.css'; // optional
import styled from 'styled-components';
import { useFormik } from 'formik';
import { useSelector, useDispatch } from 'react-redux';
import { TimesheetRowValidationSchema } from "../validations/schemas/TimesheetRowValidationSchema";
import Select from 'react-select';
import {
    addActivity,
    deleteActivity,
    saveDayInStore,
    saveProjectInStore,
    saveTaskInStore, setErrors
} from "../store/slices/timesheet";
import { setDay } from "../store/slices/timesheet";
import {fetchAllProjects} from "../store/slices/projects";
import moment from "moment";

const Input = styled.input`
text-align: center;
background-image: none !important;
padding: 0px !important;
display: inline-block;
`;

const Icon = styled.i`
display: inline-block;
color: #2e2e2e;
transition: transform 0.2s;


&:hover{
    transform: scale(1.2);
    cursor: pointer;
    color: red;
}
`;

const sum = arr => {
    let sum = 0;
    arr.forEach(element => {
        sum += element;
    });

    return sum;
}

export const TimesheetRow = ({ hours ,submitted, activity, index}) => {
    const projects = useSelector(state => state.projects.projects);
    const timesheet = useSelector(state => state.timesheet.timesheet);
    const dispatch = useDispatch();
    const id = "row";
    const [selectedTaskOption, setSelectedTaskOption] = React.useState(activity.task.name?{label:activity?.task?.name, value: activity?.task?.id} : null);
    const [selectedProjectOption, setSelectedProjectOption] = React.useState(activity.project.name? {label:activity.project.name, value: activity.project.id}: null);

    const deleteActivityOfSheet = ({timesheetId, activityId}) => {
        dispatch(deleteActivity({timesheetId, activityId}));
    }

    React.useEffect(() => {
        dispatch(fetchAllProjects());

    }, [dispatch])

    const isSubmitted = submitted === "SUBMITTED";

    const projectOptions = projects.filter(project => project.name !== '').map((project) => project = { value: project.id, label: project.name });
    let taskOptions = [];

    if (!projects.isLoading) {
        taskOptions = selectedProjectOption ? projects.filter(project => project.id === selectedProjectOption.value)[0]?.tasks.filter(task => task?.name !== '').map((task) => task = { value: task?.id, label: task?.name }) : [];
    }

    const addOnChangeProject = ({project, id, index}) => {
        dispatch(saveProjectInStore({project, id, index}))
    }

    const addOnChangeTask = ({task, id, index}) => {
        dispatch(saveTaskInStore({task, id, index}));
    }

    const addOnChangeDay = ({dayName, value, index}) => {
        let day = 0;
        switch (dayName) {
            case "monday":
                day = 0;
                formik.setFieldValue('monday', value);
                break;
            case "tuesday":
                day = 1;
                formik.setFieldValue('tuesday', value);
                break;
            case "wednesday":
                day = 2;
                formik.setFieldValue('wednesday', value);
                break;
            case "thursday":
                day = 3;
                formik.setFieldValue('thursday', value);
                break;
            case "friday":
                day = 4;
                formik.setFieldValue('friday', value);
                break;
            case "saturday":
                day = 5;
                formik.setFieldValue('saturday', value);
                break;
            case "sunday":
                day = 6;
                formik.setFieldValue('sunday', value);
                break;
            default:
                break;
        }

    

        let date = moment(activity.timesheetDays[day].date).format("YYYY-MM-DD");
       

        value = parseFloat(value);

        dispatch(saveDayInStore({day, value, date, index}));

    }

    const formik = useFormik({
        initialValues: {
            project: '',
            task: '',
            monday: timesheet.activities[index].timesheetDays[0].hours? timesheet.activities[index].timesheetDays[0].hours :'',
            tuesday: timesheet.activities[index].timesheetDays[1].hours? timesheet.activities[index].timesheetDays[1].hours :'',
            wednesday: timesheet.activities[index].timesheetDays[2].hours? timesheet.activities[index].timesheetDays[2].hours :'',
            thursday: timesheet.activities[index].timesheetDays[3].hours? timesheet.activities[index].timesheetDays[3].hours :'',
            friday: timesheet.activities[index].timesheetDays[4].hours? timesheet.activities[index].timesheetDays[4].hours :'',
            saturday: timesheet.activities[index].timesheetDays[5].hours? timesheet.activities[index].timesheetDays[5].hours :'',
            sunday: timesheet.activities[index].timesheetDays[6].hours? timesheet.activities[index].timesheetDays[6].hours :'',
            total: 0,
        },

        onSubmit: (values) => {
            values.total = (formik.values.monday !== '' ? parseFloat(formik.values.monday) : 0) +
                (formik.values.tuesday !== '' ? parseFloat(formik.values.tuesday) : 0) +
                (formik.values.wednesday !== '' ? parseFloat(formik.values.wednesday) : 0) +
                (formik.values.thursday !== '' ? parseFloat(formik.values.thursday) : 0) +
                (formik.values.friday !== '' ? parseFloat(formik.values.friday) : 0) +
                (formik.values.saturday !== '' ? parseFloat(formik.values.saturday) : 0) +
                (formik.values.sunday !== '' ? parseFloat(formik.values.sunday) : 0);
        },
        validationSchema: TimesheetRowValidationSchema,
    });

    const taskDefault = { value: '', label: "Choose Task..." };
    const projectDefault = { value: '', label: "Choose Project..." };

    const activities = timesheet.activities;

    const handleProjectChange = (e) => {

        if (activities.length === index + 1) {
            dispatch(addActivity());
        }

        setSelectedProjectOption(e);
        formik.setFieldValue('project', e);
        setSelectedTaskOption(null);
        formik.setFieldValue('task', null);

        // taskOptions = projects[e.value - 1].tasks;

       

        addOnChangeProject({project: e.label, id: e.value, index: index});
    }

    const handleTaskChange = (e) => {
        setSelectedTaskOption(e);
        formik.setFieldValue('task', e.label);
        dispatch(setErrors({activityId:activity.id, errors:formik.errors}));
        addOnChangeTask({task: e.label, id: e.value, index: index});
    }

    

    
    dispatch(setErrors({activityId:activity.id, errors:formik.errors}));
    return (
        <>
            <tr>
                <th>
                    <div className="mt-2">
                        <span className="d-inline-block ml-1">{index+1}</span>
                        {(index !== timesheet.activities.length-1 && !isSubmitted) && 
                        <Icon onClick={() => deleteActivityOfSheet({timesheetId: timesheet.id, activityId: activity.id})} className="d-inline-block fa fa-trash pl-2"></Icon>
                        }
                    </div>
                </th>
                <td>
                    <Select
                        form={id}
                        theme="danger"
                        name="project"
                        defaultValue={projectDefault}
                        onChange={handleProjectChange}
                        options={projectOptions}
                        value={selectedProjectOption}
                        className="react-select"
                        isDisabled={isSubmitted}
                    />
                     <Tippy content={"This inpuy is for selecting a project you worked on!"} arrow={true} placement='bottom' theme={"dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color:"#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>
                    <Select
                        name="task"
                        form={id}
                        defaultValue={taskDefault}
                        onChange={handleTaskChange}
                        value={selectedTaskOption || ''}
                        options={taskOptions}
                        className={formik.errors.task? "react-invalid": "react-select"}                        
                        isDisabled={isSubmitted}
                    />
                    <Tippy content={formik.errors.task ? "Please select a task!" : "This input is for selecting a task you worked on!"} arrow={true} placement='bottom' theme={formik.errors.task ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.task ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>
                    <div>
                        <Input value={formik.values.monday} disabled={isSubmitted} name="monday" maxLength={4} className={`form-control ${formik.errors.monday ? "is-invalid" : ""}`} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})}></Input>
                        <Tippy content={formik.errors.monday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.monday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                            <i class="fas fa-info-circle" style={{ color: formik.errors.monday ? "red" : "#2e2e2e" }}></i>
                        </Tippy>
                    </div>

                </td>
                <td>
                    <Input value={formik.values.tuesday} disabled={isSubmitted} name="tuesday" maxLength={4} className={`form-control ${formik.errors.tuesday ? "is-invalid" : ""}`} form={id} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})} />
                    <Tippy content={formik.errors.tuesday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.tuesday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.tuesday ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>
                    <Input value={formik.values.wednesday} disabled={isSubmitted} name="wednesday" maxLength={4} className={`form-control ${formik.errors.wednesday ? "is-invalid" : ""}`} form={id} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})} />
                    <Tippy content={formik.errors.wednesday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.wednesday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.wednesday ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td >
                <td>
                    <Input value={formik.values.thursday} disabled={isSubmitted} name="thursday" maxLength={4} className={`form-control ${formik.errors.thursday ? "is-invalid" : ""}`} form={id} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})}  />
                    <Tippy content={formik.errors.thursday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.thursday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.thursday ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>
                    <Input value={formik.values.friday} disabled={isSubmitted} name="friday" maxLength={4} className={`form-control ${formik.errors.friday ? "is-invalid" : ""}`} form={id} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})} />
                    <Tippy content={formik.errors.friday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.friday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.friday ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>
                    <Input value={formik.values.saturday}disabled={isSubmitted} name="saturday" maxLength={4} className={`form-control ${formik.errors.saturday ? "is-invalid" : ""}`} form={id} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})} />
                    <Tippy content={formik.errors.saturday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.saturday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.saturday ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>
                    <Input value={formik.values.sunday} disabled={isSubmitted} name="sunday" maxLength={4} className={`form-control ${formik.errors.sunday ? "is-invalid" : ""}`} form={id} onBlur={formik.handleBlur} onChange={(event) => addOnChangeDay({dayName: event.target.name, value: event.target.value, index: index})} />
                    <Tippy content={formik.errors.sunday ? "Only positive numbers allowed 0-24!" : "This input is for the work hours \n on a certain task!"} arrow={true} placement='bottom' theme={formik.errors.sunday ? "danger" : "dark"} style={{ display: "inline-block" }}>
                        <i class="fas fa-info-circle" style={{ color: formik.errors.sunday ? "red" : "#2e2e2e" }}></i>
                    </Tippy>
                </td>
                <td>{
                    sum([formik.values.monday,
                    formik.values.tuesday,
                    formik.values.wednesday,
                    formik.values.thursday,
                    formik.values.friday,
                    formik.values.saturday,
                    formik.values.sunday].filter(hour => !isNaN(hour)).map(Number).filter(hour => hour>=0))
                }
                </td>
            </tr>
        </>
    )
};

export default TimesheetRow;
