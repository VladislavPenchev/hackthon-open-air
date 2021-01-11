import * as Yup from "yup";

const TimesheetRowValidationSchema = Yup.object({
    project: Yup.string(),
    task: Yup.string().when("project", {
        is: project => project === null,
        then: Yup.string(),
        otherwise: Yup.string().required()
    }),
    monday: Yup.number().positive().min(0).max(24),
    tuesday: Yup.number().positive().min(0).max(24),
    wednesday: Yup.number().positive().min(0).max(24),
    thursday: Yup.number().positive().min(0).max(24),
    friday: Yup.number().positive().min(0).max(24),
    saturday: Yup.number().positive().min(0).max(24),
    sunday: Yup.number().positive().min(0).max(24),
});





const schema = Yup.object().shape({
    timesheet:
        Yup.object().shape({
            activities: Yup.array().of(
                Yup.object().shape({
                    project: Yup.object().shape({
                        name: Yup.string().required("Project must be selected!"),
                    }),
                    task: Yup.object().shape({
                        name: Yup.string().required("Task must be selected!"),
                    }),
                    timesheetDays: Yup.array().of(
                        Yup.object().shape({
                            hours: Yup.number().positive().min(0).max(24),
                        })
                    )
                })
            )
        })
});




// const schema = Yup.object().shape({
//     timesheet:
//         Yup.object().shape({
//             activities: Yup.array().of(
//                 Yup.object().shape({
//                     project: Yup.object().shape({
//                         name: Yup.string().required("Project must be selected!"),
//                     }),
//                     task: Yup.object().shape({
//                         name: Yup.string().required("Task must be selected!"),
//                     }),
//                     timesheetDays: Yup.array().of(
//                         Yup.object().shape({
//                             hours: Yup.number().positive().min(0).max(24)
//                         }
//                         ))
//                 }))
//         })
// });

export { TimesheetRowValidationSchema, schema };
